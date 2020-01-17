package in.co.sdrc.newsapp.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(min = 2, max = 20)
	@Column(unique = true)
	@ApiModelProperty(required = true, value = "Item id given by SDRC", example = "SDRC/Mobile/Vivo/027",
	position = 1)
	private String itemId;
	
	@NotNull
	@Size(min = 2, max = 20)
	@ApiModelProperty(required = true, value = "Name of the item", example = "Vivo mobile",
	position = 2)
	private String name;
	
	@ApiModelProperty(value = "Accessories comes with the item", example = "Data cable, earphone",
	position = 3)
	private String accessories;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
	
	@NotNull
	private Boolean isLive;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccessories() {
		return accessories;
	}

	public void setAccessories(String accessories) {
		this.accessories = accessories;
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

	public Boolean getIsLive() {
		return isLive;
	}

	public void setIsLive(Boolean isLive) {
		this.isLive = isLive;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", itemId=" + itemId + ", name=" + name + ", accessories=" + accessories
				+ ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", isLive=" + isLive + "]";
	}
	
	

}
