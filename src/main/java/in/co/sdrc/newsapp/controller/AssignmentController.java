package in.co.sdrc.newsapp.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.co.sdrc.newsapp.domain.Assignment;
import in.co.sdrc.newsapp.model.AssignModelForInfo;
import in.co.sdrc.newsapp.model.AssignmentModel;
import in.co.sdrc.newsapp.service.AssignmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/assignment")
@Api(description="Contains endpoints to deal with assignments")
public class AssignmentController{

    @Autowired
    private AssignmentService service;


    @PostMapping("create")
	@PreAuthorize("hasAuthority('ITEM_ALL_OPERATION')")
	@ApiOperation(value = "Create a new assignment")
	public Assignment create(@RequestBody AssignmentModel itemTransationmModel) throws ParseException {
		return service.create(itemTransationmModel);
	}

	@GetMapping("getByPerson/{id}")
	@PreAuthorize("hasAnyAuthority('ITEM_VIEW_OPERATION', 'ITEM_ALL_OPERATION')")
	@ApiOperation(value = "Get list of open assignments of a person")
	public List<AssignModelForInfo> getByPerson(@PathVariable Integer id) {
		return service.getByPerson(id);
	}

	@GetMapping("getByItem/{id}")
	@PreAuthorize("hasAnyAuthority('ITEM_VIEW_OPERATION', 'ITEM_ALL_OPERATION')")
	@ApiOperation(value = "Get open assignment of an item")
	public AssignModelForInfo getByItem(@PathVariable Long id) {
		return service.getByItem(id);
	}

	@PostMapping("submit")
	@PreAuthorize("hasAuthority('ITEM_ALL_OPERATION')")
	@ApiOperation(value = "Submit/ return  an assigment, return date format dd-MM-yyyy")
	public Assignment submit(@RequestParam Long itemId, @RequestParam String returnDate) {
		return service.submit(itemId, returnDate);
	}

	@GetMapping("historyByPerson/{id}")
	@PreAuthorize("hasAnyAuthority('ITEM_VIEW_OPERATION', 'ITEM_ALL_OPERATION')")
	@ApiOperation(value = "Get assignment history of a person")
	public List<AssignModelForInfo> historyByPerson(@PathVariable Integer id) {
		return service.historyByPerson(id);
	}

	@GetMapping("historyByItem/{id}")
	@PreAuthorize("hasAuthority('ITEM_ALL_OPERATION')")
	@ApiOperation(value = "Get assignment history of an item")
	public List<AssignModelForInfo> historyByItem(@PathVariable Long id) {
		return service.historyByItem(id);
	}

	@GetMapping("testLat")
	public void historyByItem(@RequestParam String time, @RequestParam String latLong) {
		service.logLatLong(time, latLong);
	}


}