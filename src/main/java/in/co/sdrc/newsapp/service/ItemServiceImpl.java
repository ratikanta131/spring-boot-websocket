package in.co.sdrc.newsapp.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import in.co.sdrc.newsapp.domain.Assignment;
import in.co.sdrc.newsapp.domain.Item;
import in.co.sdrc.newsapp.exception.InvalidDateFormatException;
import in.co.sdrc.newsapp.exception.InvalidInputException;
import in.co.sdrc.newsapp.exception.ItemAlreadyAssignedException;
import in.co.sdrc.newsapp.exception.ItemIdAlreadyExistsException;
import in.co.sdrc.newsapp.exception.ItemNotFoundException;
import in.co.sdrc.newsapp.repository.AssignmentRepository;
import in.co.sdrc.newsapp.repository.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService{


	@Autowired
	private ItemRepository repository;

	@Autowired
	private CommonService commonService;

	@Autowired
	private AssignmentRepository assignmentRepository;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public Item save(Item item) {

		log.info("Item creation request has come from " + commonService.getNameFromUsername(commonService.getUsername()) + " details = " + item);
		Item savedItem = null;
		try{
			savedItem = repository.save(item);
			if(savedItem != null){
				log.info("Item has created succesfully by " + commonService.getNameFromUsername(commonService.getUsername()) + " details = " + savedItem);
			}
		}catch(DataIntegrityViolationException e){
			throw new ItemIdAlreadyExistsException("Item Id " + item.getItemId() + " already exists, if you don't find the item, please contact application admin. The item might be disabled(isLive false).");
		}
		return savedItem;
	}

	@Override
	public Item update(Item item) {

		log.info("Item updation request has come from " + commonService.getNameFromUsername(commonService.getUsername()) + " details = " + item);
		if(item.getId() == null){
			log.error("id can not be blank");
			throw new InvalidInputException("id can not be blank");
		}
		Item existingItem = repository.findByIdAndIsLiveTrue(item.getId());
		if(existingItem == null){
			throw new ItemNotFoundException("Item not found");
		}
		if(item.getItemId() == null){
			throw new InvalidInputException("Item id can not be blank");
		}
		if(!(item.getItemId().equals(existingItem.getItemId()))){
			Item differentItem = repository.findByItemIdAndIsLiveTrue(item.getItemId());
			if(differentItem != null){
				throw new ItemIdAlreadyExistsException("Item Id " + item.getItemId() + " already exists, if you don't find the item, please contact application admin. The item might be disabled(isLive false).");
			}
		}
		existingItem.setAccessories(item.getAccessories());
		existingItem.setName(item.getName());
		existingItem.setUpdatedDate(null);
		existingItem.setItemId(item.getItemId());
		existingItem = repository.save(existingItem);
		
		if(existingItem != null){
			log.info("Item has updated succesfully by " + commonService.getNameFromUsername(commonService.getUsername()) + " details = " + existingItem);
		}
		return existingItem;

	}

	@Override
	public void delete(long id) {

		log.info("Item deletion request has come from " + commonService.getNameFromUsername(commonService.getUsername()) + " item id  = " + id);
		

		Item item = repository.findByIdAndIsLiveTrue(id);
		if(item == null){
			throw new ItemNotFoundException("Item not found");
		}

		//Have to check in the assignment transaction
		//If there is any pending assignments, we have to clear that first.
		Assignment assignment = assignmentRepository.findByItemAndReturnDateIsNull(item);

		//check it after giving return date
		if(assignment != null){
			throw new ItemAlreadyAssignedException("Item can't be deleted, has already been assigned to " + commonService.getNameFromUsername(assignment.getAssignedTo().getUserName()));
		}
		item.setIsLive(false);
		item = repository.save(item);
		if(item != null){
			log.info("Item deleted by " + commonService.getNameFromUsername(commonService.getUsername()) + " item id  = " + id);
		}else{
			log.info("Could not delete Item by " + commonService.getNameFromUsername(commonService.getUsername()) + " item id  = " + id);
		}

	}

	@Override
	public List<Item> updatedItems(String lastSyncDateString) {

		if(lastSyncDateString != null && !lastSyncDateString.isEmpty() && !lastSyncDateString.equals("null")){
			Date lastSyncDate = null;
			try{
				lastSyncDate = sdf.parse(lastSyncDateString);
			}catch(ParseException e){
				throw new InvalidDateFormatException("Invalid last sync date, the correct format is dd-MM-yyyy HH:mm:ss.SSS");
			}
			return repository.findAllByCreatedDateGreaterThanOrUpdatedDateGreaterThan(lastSyncDate, lastSyncDate);
		} else {
			return repository.findAllByIsLiveTrue();
		}
	}

}
