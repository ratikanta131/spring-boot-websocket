package in.co.sdrc.newsapp.service;

import org.sdrc.usermgmt.domain.Authority;
import org.sdrc.usermgmt.domain.Designation;
import org.sdrc.usermgmt.domain.DesignationAuthorityMapping;
import org.sdrc.usermgmt.repository.AuthorityRepository;
import org.sdrc.usermgmt.repository.DesignationAuthorityMappingRepository;
import org.sdrc.usermgmt.repository.DesignationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

	@Autowired
	private AuthorityRepository authorityRepository;

	@Autowired
	private DesignationRepository designationRepository;

	@Autowired
	private DesignationAuthorityMappingRepository designationAuthorityMappingRepository;


	@Override
	public String createOneAutherity(String name, String description) {

		Authority authority = new Authority();
		authority.setAuthority(name);
		authority.setDescription(description);
		authorityRepository.save(authority);
		System.out.println("Autherity " + name + " created.");

		return "succes";
	}

	@Override
	public String createOneDesignation(String name, String code) {

		Designation desg = new Designation();
		desg.setCode(code);
		desg.setName(name);
		designationRepository.save(desg);
		return "success";
	}

	@Override
	public String createOneDesignationAutherityMapping(String designationCode, String autherityName) {

		DesignationAuthorityMapping dam = new DesignationAuthorityMapping();

		dam.setAuthority(authorityRepository.findByAuthority(autherityName));
		dam.setDesignation(designationRepository.findByCode(designationCode));

		designationAuthorityMappingRepository.save(dam);

		System.out.println("Autherity mapping" + designationCode + " " + autherityName + " created.");

		return "success";
	}

}
