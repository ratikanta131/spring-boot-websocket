package in.co.sdrc.newsapp.repository;

import java.util.Date;
import java.util.List;

import org.sdrc.usermgmt.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.co.sdrc.newsapp.domain.UserDetails;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long>{

	
	List<UserDetails> findAllByCreatedDateGreaterThanOrUpdatedDateGreaterThan(Date lastSyncDate, Date lastSyncDate1);

	UserDetails findByAccount(Account account);

	@Query("SELECT u FROM UserDetails u WHERE u.account.enabled = true and u.account.expired = false and locked = false")
	List<UserDetails> findAllEnabledUser();

}
