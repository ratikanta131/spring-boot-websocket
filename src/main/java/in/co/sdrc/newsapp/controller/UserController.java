package in.co.sdrc.newsapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.co.sdrc.newsapp.model.UserModel;
import in.co.sdrc.newsapp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/user")
@Api(description="Contains endpoints to deal with users")
public class UserController {
	
	@Autowired
	private UserService service;

	@GetMapping("allUsers")
	@PreAuthorize("hasAnyAuthority('ITEM_VIEW_OPERATION', 'ITEM_ALL_OPERATION')")
	@ApiOperation(value = "Get all users")
	public List<UserModel> user(@RequestParam String lastSyncDate){
		return service.updatedUsers(lastSyncDate);
	}

}
