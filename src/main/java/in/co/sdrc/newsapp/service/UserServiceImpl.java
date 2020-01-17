package in.co.sdrc.newsapp.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.sdrc.usermgmt.domain.AccountDesignationMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.co.sdrc.newsapp.domain.UserDetails;
import in.co.sdrc.newsapp.exception.InvalidDateFormatException;
import in.co.sdrc.newsapp.model.UserModel;
import in.co.sdrc.newsapp.repository.UserDetailsRepository;


@Service
public class UserServiceImpl implements UserService{

	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");

	@Autowired
	private UserDetailsRepository repository;


	@Override
	public List<UserModel> updatedUsers(String lastSyncDateString) {
		List<UserModel> userList = new ArrayList<>();
		if(lastSyncDateString != null && !lastSyncDateString.isEmpty() && !lastSyncDateString.equals("null")){
			Date lastSyncDate = null;
			try{
				lastSyncDate = sdf.parse(lastSyncDateString);
			}catch(ParseException e){
				throw new InvalidDateFormatException("Invalid last sync date, the correct format is dd-MM-yyyy HH:mm:ss.SSS");
			}
			List<UserDetails> userDetailsList = repository.findAllByCreatedDateGreaterThanOrUpdatedDateGreaterThan(lastSyncDate, lastSyncDate);
			
			userDetailsList.stream().forEach(userDetails->{
				UserModel model = new UserModel();

				model.setId(userDetails.getAccount().getId());
				model.setName(userDetails.getName());
				model.setName(userDetails.getAccount().getUserName());
				model.setAddress(userDetails.getAddress());
				model.setAlternateMobileNumber(userDetails.getAlternateMobileNumber());
				model.setBloodgroup(userDetails.getBloodgroup());

				List<AccountDesignationMapping> mapping = userDetails.getAccount().getAccountDesignationMapping();
				List<Integer> designationIds = new ArrayList<>();
				List<String> designationNames = new ArrayList<>();
				mapping.stream().forEach(map -> {
					designationIds.add(map.getDesignation().getId());
					designationNames.add(map.getDesignation().getName());
				});

				model.setDesignationIds(designationIds);
				model.setDesignationNames(designationNames);
				model.setEmail(userDetails.getAccount().getEmail());
				model.setEmployeeId(userDetails.getEmployeeId());
				model.setMobileNumber(userDetails.getMobileNumber());
				if (userDetails.getReportingAuthority() != null) {
					model.setReportingAuthorityAccountId(userDetails.getReportingAuthority().getId());
				}
				userList.add(model);
			});

		} else {

			List<UserDetails> userDetailsList = repository.findAllEnabledUser();

			userDetailsList.stream().forEach(userDetails -> {
				UserModel model = new UserModel();

				model.setId(userDetails.getAccount().getId());
				model.setName(userDetails.getName());
				model.setName(userDetails.getAccount().getUserName());
				model.setAddress(userDetails.getAddress());
				model.setAlternateMobileNumber(userDetails.getAlternateMobileNumber());
				model.setBloodgroup(userDetails.getBloodgroup());


				List<AccountDesignationMapping> mapping = userDetails.getAccount().getAccountDesignationMapping();
				List<Integer> designationIds = new ArrayList<>();
				List<String> designationNames = new ArrayList<>();
				mapping.stream().forEach(map->{
					designationIds.add(map.getDesignation().getId());
					designationNames.add(map.getDesignation().getName());
				});

				model.setDesignationIds(designationIds);
				model.setDesignationNames(designationNames);
				model.setEmail(userDetails.getAccount().getEmail());
				model.setEmployeeId(userDetails.getEmployeeId());
				model.setMobileNumber(userDetails.getMobileNumber());
				if(userDetails.getReportingAuthority() != null){
					model.setReportingAuthorityAccountId(userDetails.getReportingAuthority().getId());
				}
				
				userList.add(model);
			});
		}
		return userList; 
	}

}
