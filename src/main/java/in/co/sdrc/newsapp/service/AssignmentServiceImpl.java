package in.co.sdrc.newsapp.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.sdrc.usermgmt.domain.Account;
import org.sdrc.usermgmt.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

import in.co.sdrc.newsapp.config.RabbitMQConfig;
import in.co.sdrc.newsapp.domain.Assignment;
import in.co.sdrc.newsapp.domain.Item;
import in.co.sdrc.newsapp.exception.AssignmentNotFoundException;
import in.co.sdrc.newsapp.exception.DBOperationFailedException;
import in.co.sdrc.newsapp.exception.InvalidDateFormatException;
import in.co.sdrc.newsapp.exception.ItemAlreadyAssignedException;
import in.co.sdrc.newsapp.exception.ItemNotFoundException;
import in.co.sdrc.newsapp.model.AssignModelForInfo;
import in.co.sdrc.newsapp.model.AssignmentModel;
import in.co.sdrc.newsapp.model.AssignmentRabbitMQModel;
import in.co.sdrc.newsapp.model.Mail;
import in.co.sdrc.newsapp.repository.AssignmentRepository;
import in.co.sdrc.newsapp.repository.ItemRepository;

@Service
public class AssignmentServiceImpl implements AssignmentService{

    @Autowired
	private AssignmentRepository repository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private RabbitMQConfig rabbitMQConfig;

	@Autowired
	private EmailService mailService;

	@Autowired
	private CommonService commonService;

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    
    private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
    public Assignment create(final AssignmentModel itemTransationModel) throws ParseException {

		log.info("Assingment creation request has come from " + commonService.getNameFromUsername(commonService.getUsername()) + " details = " + itemTransationModel);
		
		final Item item = itemRepository.findByIdAndIsLiveTrue (itemTransationModel.getItemId());

		if(item == null){
			throw new ItemNotFoundException("Item not found");
		}

		//checking whether the item is already assigned to someone or not
		Assignment assignment = repository.findByItemAndReturnDateIsNull(item);
		if(assignment != null){
			throw new ItemAlreadyAssignedException("This item is already assigned to " + commonService.getNameFromUsername(assignment.getAssignedTo().getUserName()));
		}
		assignment = new Assignment();

		//Getting value from model
		final Account assignedTo = accountRepository.findById (itemTransationModel.getAssignedToUserId());
		final Date issueDate = sdf.parse(itemTransationModel.getIssueDate());
		final Account issuedBy = accountRepository.findById(itemTransationModel.getIssuedBy());
		

		//Setting in the domain class
		assignment.setAssignedTo(assignedTo);
		assignment.setIssueDate(issueDate);
		assignment.setIssuedBy(issuedBy);
		assignment.setItem(item);
		
		//Saving in database
		assignment =  repository.save(assignment);

		if(assignment != null){
			log.info("Assingment created successfully by " + commonService.getNameFromUsername(commonService.getUsername()) + " details = " + itemTransationModel);
			final AssignmentRabbitMQModel assignmentRabbitMQModel = new AssignmentRabbitMQModel();
			assignmentRabbitMQModel.setItemName(item.getName());
			assignmentRabbitMQModel.setAssignedBy(commonService.getNameFromUsername(issuedBy.getUserName()));
			assignmentRabbitMQModel.setEmail(assignedTo.getEmail());
			assignmentRabbitMQModel.setAssignedTo(commonService.getNameFromUsername(assignedTo.getUserName()));
			
			final boolean flag = rabbitMQConfig.assignmentCreationChannel().send(MessageBuilder.withPayload(assignmentRabbitMQModel).build());
			if(!flag){
				log.error("Could not send message to RabbitMQ, while creating assignment. Item name: " + item.getName());
			}
		}else{
			throw new DBOperationFailedException("Assignment creation operation failed, reason unknown, please contact developer");
		}

		return assignment;
	}

	@Override
	public List<AssignModelForInfo> getByPerson(final Integer id) {

		final Account account = accountRepository.findById(id);

		final List<Assignment> assignments = repository.findByAssignedToAndReturnDateIsNull(account);
		if(assignments.size() == 0){
			throw new AssignmentNotFoundException("No assigmnet found.");
		}
		final List<AssignModelForInfo> list = new ArrayList<>();
		assignments.stream().forEach(assignment -> {
			final AssignModelForInfo assignModelForInfo = new AssignModelForInfo();
			assignModelForInfo.setAssignedItemId(assignment.getItem().getId());
			assignModelForInfo
					.setAssignedTo(commonService.getNameFromUsername(assignment.getAssignedTo().getUserName()));
			assignModelForInfo.setIssueDate(sdf.format(assignment.getIssueDate()));
			assignModelForInfo.setIssuedBy(commonService.getNameFromUsername(assignment.getIssuedBy().getUserName()));
			assignModelForInfo.setItemId(assignment.getItem().getItemId());
			assignModelForInfo.setItemName(assignment.getItem().getName());
			list.add(assignModelForInfo);
		});

		return list;
	}

	@Override
	public AssignModelForInfo getByItem(final Long id) {
		final Item item = itemRepository.findByIdAndIsLiveTrue(id);
		if (item == null) {
			throw new ItemNotFoundException("Item not found");
		}

		final Assignment assignment = repository.findByItemAndReturnDateIsNull(item);
		if (assignment == null) {
			throw new AssignmentNotFoundException("No assigmnet found.");
		}
		final AssignModelForInfo assignModelForInfo = new AssignModelForInfo();
		assignModelForInfo.setAssignedItemId(assignment.getItem().getId());
		assignModelForInfo.setAssignedTo(commonService.getNameFromUsername(assignment.getAssignedTo().getUserName()));
		assignModelForInfo.setIssueDate(sdf.format(assignment.getIssueDate()));
		assignModelForInfo.setIssuedBy(commonService.getNameFromUsername(assignment.getIssuedBy().getUserName()));
		assignModelForInfo.setItemId(assignment.getItem().getItemId());
		assignModelForInfo.setItemName(assignment.getItem().getName());
		return assignModelForInfo;
	}

	@Override
	public Assignment submit(final Long itemId, final String returnDate) {

		log.info("Item submit request has come from " + commonService.getNameFromUsername(commonService.getUsername())
				+ " ItemId = " + itemId + ", return date = " + returnDate);

		final Item item = itemRepository.findByIdAndIsLiveTrue(itemId);
		if (item == null) {
			throw new ItemNotFoundException("Item not found");
		}
		Assignment assignment = repository.findByItemAndReturnDateIsNull(item);
		if (assignment == null) {
			throw new AssignmentNotFoundException("Assignment not found");
		}

		final Account receivedBy = accountRepository.findByUserName(commonService.getUsername());
		assignment.setReceivedBy(receivedBy);

		try {
			assignment.setReturnDate(sdf.parse(returnDate));
		} catch (final ParseException e) {
			throw new InvalidDateFormatException("Invalid return date format, the correct format is dd-MM-yyyy");
		}

		assignment.setUpdatedDate(null);
		assignment = repository.save(assignment);
		if (assignment != null) {
			log.info("Item submitted succesfully, request has come from "
					+ commonService.getNameFromUsername(commonService.getUsername()) + " ItemId = " + itemId
					+ ", return date = " + returnDate);

			final AssignmentRabbitMQModel assignmentRabbitMQModel = new AssignmentRabbitMQModel();
			assignmentRabbitMQModel.setItemName(item.getName());
			assignmentRabbitMQModel.setReceivedBy(commonService.getNameFromUsername(receivedBy.getUserName()));
			assignmentRabbitMQModel.setEmail(assignment.getAssignedTo().getEmail());
			assignmentRabbitMQModel
					.setAssignedTo(commonService.getNameFromUsername(assignment.getAssignedTo().getUserName()));

			final boolean flag = rabbitMQConfig.submitOutChannel()
					.send(MessageBuilder.withPayload(assignmentRabbitMQModel).build());
			if (!flag) {
				log.error("Could not send message to RabbitMQ, while submitting assignment. Item name: "
						+ item.getName());
			}
		} else {
			log.error("Error in item submission, request has come from "
					+ commonService.getNameFromUsername(commonService.getUsername()) + " ItemId = " + itemId
					+ ", return date = " + returnDate);
		}
		return assignment;
	}

	@StreamListener(value = RabbitMQConfig.SDRC_APP_EMAIL_CHANNEL_IN)
	public void sendAssignmentEmail(final AssignmentRabbitMQModel assignmentRabbitMQModel) {

		log.info("Received RabbitMQ message to send item assigngment email. Details " + assignmentRabbitMQModel);

		final Mail mail = new Mail();
		mail.setFromUserName("SDRC App Admin");
		mail.setMessage(assignmentRabbitMQModel.getItemName() + " is assigned to you by "
				+ assignmentRabbitMQModel.getAssignedBy() + ". Please contact "
				+ assignmentRabbitMQModel.getAssignedBy() + " if it this assignment is not correct.");

		mail.setSubject(assignmentRabbitMQModel.getItemName() + " is assigned to you by "
				+ assignmentRabbitMQModel.getAssignedBy());
		mail.setToEmailIds(Arrays.asList(assignmentRabbitMQModel.getEmail()));
		mail.setToUserName(assignmentRabbitMQModel.getAssignedTo());
		mailService.sendMail(mail);

	}

	@StreamListener(value = RabbitMQConfig.SDRC_APP_SUBMIT_EMAIL_CHANNEL_IN)
	public void sendAssignmentSubmitEmail(final AssignmentRabbitMQModel assignmentRabbitMQModel) {

		log.info("Received RabbitMQ message to send item submission email. Details " + assignmentRabbitMQModel);

		final Mail mail = new Mail();
		mail.setFromUserName("SDRC App Admin");
		mail.setMessage(assignmentRabbitMQModel.getItemName() + " is submitted. Received by "
				+ assignmentRabbitMQModel.getReceivedBy() + ". Please contact "
				+ assignmentRabbitMQModel.getReceivedBy() + " if it this submission is not correct.");

		mail.setSubject(assignmentRabbitMQModel.getItemName() + " is submitted");
		mail.setToEmailIds(Arrays.asList(assignmentRabbitMQModel.getEmail()));
		mail.setToUserName(assignmentRabbitMQModel.getAssignedTo());
		mailService.sendMail(mail);

	}

	@Override
	public List<AssignModelForInfo> historyByPerson(final Integer id) {

		final Account account = accountRepository.findById(id);
		final List<Assignment> assignments = repository.findByAssignedTo(account);

		if (assignments.size() == 0) {
			throw new AssignmentNotFoundException("No assigmnet found.");
		}
		final List<AssignModelForInfo> list = new ArrayList<>();
		assignments.stream().forEach(assignment -> {
			final AssignModelForInfo assignModelForInfo = new AssignModelForInfo();
			assignModelForInfo.setAssignedItemId(assignment.getItem().getId());
			assignModelForInfo
					.setAssignedTo(commonService.getNameFromUsername(assignment.getAssignedTo().getUserName()));
			assignModelForInfo.setIssueDate(sdf.format(assignment.getIssueDate()));
			assignModelForInfo.setIssuedBy(commonService.getNameFromUsername(assignment.getIssuedBy().getUserName()));
			assignModelForInfo.setItemId(assignment.getItem().getItemId());
			assignModelForInfo.setItemName(assignment.getItem().getName());

			if(assignment.getReturnDate() != null){
				assignModelForInfo.setReturnDate(sdf.format(assignment.getReturnDate()));
				assignModelForInfo.setReceivedBy(commonService.getNameFromUsername(assignment.getReceivedBy().getUserName()));
			}
			list.add(assignModelForInfo);
		});

		return list;
	}

	@Override
	public List<AssignModelForInfo> historyByItem(final Long id) {

		final Item item = itemRepository.findByIdAndIsLiveTrue(id);
		final List<Assignment> assignments = repository.findByItem(item);

		if (assignments.size() == 0) {
			throw new AssignmentNotFoundException("No assigmnet found.");
		}
		final List<AssignModelForInfo> list = new ArrayList<>();
		assignments.stream().forEach(assignment -> {
			final AssignModelForInfo assignModelForInfo = new AssignModelForInfo();
			assignModelForInfo.setAssignedItemId(assignment.getItem().getId());
			assignModelForInfo.setAssignedTo(commonService.getNameFromUsername(assignment.getAssignedTo().getUserName()));
			assignModelForInfo.setIssueDate(sdf.format(assignment.getIssueDate()));
			assignModelForInfo.setIssuedBy(commonService.getNameFromUsername(assignment.getIssuedBy().getUserName()));
			assignModelForInfo.setItemId(assignment.getItem().getItemId());
			assignModelForInfo.setItemName(assignment.getItem().getName());
			if(assignment.getReturnDate() != null){
				assignModelForInfo.setReturnDate(sdf.format(assignment.getReturnDate()));
				assignModelForInfo.setReceivedBy(commonService.getNameFromUsername(assignment.getReceivedBy().getUserName()));
			}
			
			list.add(assignModelForInfo);
		});

		return list;
	}

	@Override
	public void logLatLong(String time, String latLong) {
		log.info("time " + time + " Latlong " + latLong);
	}
}