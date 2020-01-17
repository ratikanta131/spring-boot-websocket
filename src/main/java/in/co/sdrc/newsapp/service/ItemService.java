package in.co.sdrc.newsapp.service;

import java.util.List;

import in.co.sdrc.newsapp.domain.Item;

public interface ItemService {

	Item save(Item item);

	Item update(Item item);

	void delete(long id);

	List<Item> updatedItems(String lastSyncDateString);

}