package in.co.sdrc.newsapp.model;
public class AssignmentModel {

    private Long itemId;
    private Integer assignedToUserId;
    private String issueDate;
    private String returnDate;
    private Integer issuedBy;
    private Integer receivedBy;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getAssignedToUserId() {
        return assignedToUserId;
    }

    public void setAssignedToUserId(Integer assignedToUserId) {
        this.assignedToUserId = assignedToUserId;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(Integer issuedBy) {
        this.issuedBy = issuedBy;
    }

    public Integer getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(Integer receivedBy) {
        this.receivedBy = receivedBy;
    }

    @Override
    public String toString() {
        return "ItemTransactionModel [assignedToUserId=" + assignedToUserId + ", issueDate=" + issueDate + ", issuedBy="
                + issuedBy + ", itemId=" + itemId + ", receivedBy=" + receivedBy + ", returnDate=" + returnDate + "]";
    }

    

}