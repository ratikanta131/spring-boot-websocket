package in.co.sdrc.newsapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.co.sdrc.newsapp.domain.Item;
import in.co.sdrc.newsapp.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/item")
@Api(description="Contains endpoints to deal with items")
public class ItemController {

	@Autowired
	private ItemService service;

	@PostMapping("create")
	@PreAuthorize("hasAuthority('ITEM_ALL_OPERATION')")
	@ApiOperation(value = "Create a new item")
	public Item create(@RequestBody Item item) {
		return service.save(item);
	}

	@PostMapping("update")
	@PreAuthorize("hasAuthority('ITEM_ALL_OPERATION')")
	@ApiOperation(value = "Update an existing item")
	public Item update(@RequestBody Item item) {
		return service.update(item);
	}

	@DeleteMapping("delete/{id}")
	@PreAuthorize("hasAuthority('ITEM_ALL_OPERATION')")
	@ApiOperation(value = "Delete an item")
	public void delete(@PathVariable long id) {
		service.delete(id);
	}

	@GetMapping("allItems")
	@PreAuthorize("hasAnyAuthority('ITEM_VIEW_OPERATION', 'ITEM_ALL_OPERATION')")
	@ApiOperation(value = "Get all items")
	public List<Item> item(@RequestParam String lastSyncDate){
		return service.updatedItems(lastSyncDate);
	}
	
}
