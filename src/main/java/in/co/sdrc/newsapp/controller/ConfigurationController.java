package in.co.sdrc.newsapp.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.co.sdrc.newsapp.service.ConfigurationService;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class ConfigurationController {

	@Autowired
	private ConfigurationService configurationService;
	
	@PostMapping("createOneAutherity")
	@PreAuthorize("hasAuthority('USER_MGMT_ALL_API')")
	public String createOneAutherity(@RequestParam("name") String name, @RequestParam("description") String description){
		return configurationService.createOneAutherity(name, description);
	}
	
	@PostMapping("createOneDesignation")
	@PreAuthorize("hasAuthority('USER_MGMT_ALL_API')")
	public String createOneDesignation(@RequestParam("name") String name, @RequestParam("code") String code){
		return configurationService.createOneDesignation(name, code);
	}
	
	@PostMapping("createOneDesignationAutherityMapping")
	@PreAuthorize("hasAuthority('USER_MGMT_ALL_API')")
	public String createOneDesignationAutherityMapping(@RequestParam("designationCode") String designationCode, @RequestParam("autherityName") String autherityName){
		return configurationService.createOneDesignationAutherityMapping(designationCode, autherityName);
	}
	

}