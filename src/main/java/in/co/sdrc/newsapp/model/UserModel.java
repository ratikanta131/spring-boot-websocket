package in.co.sdrc.newsapp.model;

import java.util.List;

public class UserModel {
	
	private Integer id;
	private String userName;
	private String name;
	private List<Integer> designationIds;
	private String email;
	private Integer employeeId;
	private String address;
	private String bloodgroup;
	private String mobileNumber;
	private String alternateMobileNumber;
	private Integer reportingAuthorityAccountId;
	private List<String> designationNames;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String username) {
		this.userName = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Integer> getDesignationIds() {
		return designationIds;
	}

	public void setDesignationIds(List<Integer> designationIds) {
		this.designationIds = designationIds;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBloodgroup() {
		return bloodgroup;
	}

	public void setBloodgroup(String bloodgroup) {
		this.bloodgroup = bloodgroup;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAlternateMobileNumber() {
		return alternateMobileNumber;
	}

	public void setAlternateMobileNumber(String alternateMobileNumber) {
		this.alternateMobileNumber = alternateMobileNumber;
	}

	public Integer getReportingAuthorityAccountId() {
		return reportingAuthorityAccountId;
	}

	public void setReportingAuthorityAccountId(Integer reportingAuthorityAccountId) {
		this.reportingAuthorityAccountId = reportingAuthorityAccountId;
	}

	public List<String> getDesignationNames() {
		return designationNames;
	}

	public void setDesignationNames(List<String> designationNames) {
		this.designationNames = designationNames;
	}
	

}
