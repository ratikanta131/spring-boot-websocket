package in.co.sdrc.newsapp.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.sdrc.usermgmt.domain.Account;

@Entity
public class Assignment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	private Item item;
	
	@ManyToOne
	@JoinColumn(name="assigned_to_fk")
	@NotNull
	private Account assignedTo;
	
	@NotNull
	private Date issueDate;
	
	private Date returnDate;
	
	@ManyToOne
	@JoinColumn(name="issued_by_fk")
	@NotNull
	private Account issuedBy;

	@ManyToOne
	@JoinColumn(name="received_by_fk")
	private Account receivedBy;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Account getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(Account assignedTo) {
		this.assignedTo = assignedTo;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Account getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(Account issuedBy) {
		this.issuedBy = issuedBy;
	}

	public Account getReceivedBy() {
		return receivedBy;
	}

	public void setReceivedBy(Account receivedBy) {
		this.receivedBy = receivedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public String toString() {
		return "ItemTransation [assignedTo=" + assignedTo + ", createdDate=" + createdDate + ", id=" + id
				+ ", issueDate=" + issueDate + ", issuedBy=" + issuedBy + ", item=" + item + ", receivedBy="
				+ receivedBy + ", returnDate=" + returnDate + ", updatedDate=" + updatedDate + "]";
	}
	

	

}
