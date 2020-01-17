package in.co.sdrc.newsapp.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.sdrc.usermgmt.core.util.IUserManagementHandler;
import org.sdrc.usermgmt.domain.Account;
import org.sdrc.usermgmt.domain.AccountDesignationMapping;
import org.sdrc.usermgmt.domain.Designation;
import org.sdrc.usermgmt.repository.AccountRepository;
import org.sdrc.usermgmt.repository.DesignationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import in.co.sdrc.newsapp.domain.UserDetails;
import in.co.sdrc.newsapp.repository.CustomAccountDesignationMappingRepository;
import in.co.sdrc.newsapp.repository.UserDetailsRepository;

@Component
public class IUserManagementHandlerImpl implements IUserManagementHandler {

	@Autowired
	private UserDetailsRepository userDetailsRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private DesignationRepository designationRepository;

	@Autowired
	@Qualifier("customAccountDesignationMappingRepository")
	private CustomAccountDesignationMappingRepository customAccountDesignationMappingRepository;

	private Logger logger = LoggerFactory.getLogger(this.getClass());


	@Override
	public List < ? > getAllAuthorities() {
		return null;
	}

	@Override
	public boolean saveAccountDetails(Map < String, Object > map, Object account) {



		if (map.get("name") == null || map.get("name").toString().isEmpty())
			throw new RuntimeException("key : name not found in map");
		if (map.get("employeeId") == null || map.get("employeeId").toString().isEmpty())
			throw new RuntimeException("key : employeeId not found in map");
		if (map.get("address") == null || map.get("address").toString().isEmpty())
			throw new RuntimeException("key : address not found in map");
		if (map.get("bloodgroup") == null || map.get("bloodgroup").toString().isEmpty())
			throw new RuntimeException("key : bloodgroup not found in map");
		if (map.get("mobileNumber") == null || map.get("mobileNumber").toString().isEmpty())
			throw new RuntimeException("key : mobileNumber not found in map");






		try {

			Account acc = (Account) account;

			UserDetails user = new UserDetails();
			user.setAccount(acc);
			user.setAddress(map.get("address").toString());
			user.setAlternateMobileNumber(map.get("alternateMobileNumber").toString());
			user.setBloodgroup(map.get("bloodgroup").toString());
			user.setEmployeeId(Integer.parseInt(map.get("employeeId").toString()));
			user.setMobileNumber(map.get("mobileNumber").toString());
			user.setName(map.get("name").toString());

			Account reportingAuthority = null;
			String reportingAuthorityIdString = map.get("reportingAuthorityAccountId") != null ? map.get("reportingAuthorityAccountId").toString() : null;
			if (reportingAuthorityIdString != null) {
				reportingAuthority = accountRepository.findOne(Integer.parseInt(reportingAuthorityIdString));
			}

			if (reportingAuthority != null) {
				user.setReportingAuthority(reportingAuthority);
			}


			logger.info("Account create, Username = " + acc.getUserName());
			userDetailsRepository.save(user);

			return true;
		} catch (Exception e) {
			logger.error("Action: while creating user with payload {} " + map, e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map < String, Object > sessionMap(Object arg0) {
		return null;
	}

	@Override
	public boolean updateAccountDetails(Map < String, Object > map, Object account, Principal arg2) {







		try {

			Account acc = (Account) account;

			UserDetails user = userDetailsRepository.findByAccount(acc);
			List < Designation > designations = designationRepository.findAll();


			if (!(map.get("name") == null || map.get("name").toString().isEmpty())) {
				user.setName(map.get("name").toString());
			}

			if (!(map.get("employeeId") == null || map.get("employeeId").toString().isEmpty())) {
				user.setEmployeeId(Integer.parseInt(map.get("employeeId").toString()));
			}

			if (!(map.get("address") == null || map.get("address").toString().isEmpty())) {
				user.setAddress(map.get("address").toString());
			}

			if (!(map.get("bloodgroup") == null || map.get("bloodgroup").toString().isEmpty())) {
				user.setBloodgroup(map.get("bloodgroup").toString());
			}

			if (!(map.get("mobileNumber") == null || map.get("mobileNumber").toString().isEmpty())) {
				user.setMobileNumber(map.get("mobileNumber").toString());
			}

			if (!(map.get("alternateMobileNumber") == null || map.get("alternateMobileNumber").toString().isEmpty())) {
				user.setAlternateMobileNumber(map.get("alternateMobileNumber").toString());
			}

			if (!(map.get("reportingAuthorityAccountId") == null || map.get("reportingAuthorityAccountId").toString().isEmpty())) {
				
				Account reportingAuthority = null;
				String reportingAuthorityIdString = map.get("reportingAuthorityAccountId") != null ? map.get("reportingAuthorityAccountId").toString() : null;
				if (reportingAuthorityIdString != null) {
					reportingAuthority = accountRepository.findOne(Integer.parseInt(reportingAuthorityIdString));
				}

				if (reportingAuthority != null) {
					user.setReportingAuthority(reportingAuthority);
				}


			}





			acc = accountRepository.save(acc);
			user.setAccount(acc);

			if (!(map.get("designationIds") == null || map.get("designationIds").toString().isEmpty())) {
				List < Integer > designationIds = new ArrayList < > ();

				try {
					designationIds = (List < Integer > ) map.get("designationIds");
				} catch (Exception e) {
					throw new RuntimeException("key : designationIds, please give valid input");
				}


				List < AccountDesignationMapping > accountDesignationMappings = customAccountDesignationMappingRepository.findByAccountAndEnableTrue((Account) account);

				//check to be deleted
				for (int i = 0; i < accountDesignationMappings.size(); i++) {

					boolean flag = false;

					for (int j = 0; j < designationIds.size(); j++) {
						if (accountDesignationMappings.get(i).getDesignation().getId() == designationIds.get(j).intValue()) {
							flag = true;
						}
					}

					if (!flag) {
						accountDesignationMappings.get(i).setEnable(false);
					}

				}

				//check to add
				for (int i = 0; i < designationIds.size(); i++) {

					boolean flag = false;
					for (int j = 0; j < accountDesignationMappings.size(); j++) {
						if (designationIds.get(i).intValue() == accountDesignationMappings.get(j).getDesignation().getId()) {
							flag = true;
						}
					}
					if (!flag) {

						AccountDesignationMapping accountDesignationMapping = new AccountDesignationMapping();
						accountDesignationMapping.setAccount((Account) account);
						accountDesignationMapping.setDesignation(getDesignation(designations, designationIds.get(i).intValue()));
						accountDesignationMapping.setEnable(true);
						accountDesignationMappings.add(accountDesignationMapping);

					}

				}

				customAccountDesignationMappingRepository.save(accountDesignationMappings);
			}


			userDetailsRepository.save(user);
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Designation getDesignation(List < Designation > designations, int id) {

		for (int i = 0; i < designations.size(); i++) {
			if (designations.get(i).getId() == id) {
				return designations.get(i);
			}
		}

		return null;
	}

}