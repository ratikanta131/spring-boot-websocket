package in.co.sdrc.newsapp.repository;

import java.util.Date;
import java.util.List;

import org.sdrc.usermgmt.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import in.co.sdrc.newsapp.domain.Assignment;
import in.co.sdrc.newsapp.domain.Item;
public interface AssignmentRepository extends JpaRepository<Assignment, Long>{

	List<Assignment> findAllByCreatedDateGreaterThanOrUpdatedDateGreaterThan(Date lastSyncDate,
			Date lastSyncDate2);

	List<Assignment> findAllByAssignedTo(Integer id);

	Assignment findByItemAndReturnDateIsNull(Item item);

	List<Assignment> findByItem(Item item);

	List<Assignment> findByAssignedToAndReturnDateIsNull(Account account);

	List<Assignment> findByAssignedTo(Account account);

}