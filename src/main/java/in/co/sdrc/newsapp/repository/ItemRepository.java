package in.co.sdrc.newsapp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.co.sdrc.newsapp.domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

	List<Item> findAllByIsLiveTrue();
	
	Item findOneByIsLiveTrue(Long id);
	
	List<Item> findAllByCreatedDateGreaterThanOrUpdatedDateGreaterThan(Date lastSyncDate, Date lastSyncDate1);

	Item findByItemIdAndIsLiveTrue(String itemId);

	Item findByIdAndIsLiveTrue(long id);

}
