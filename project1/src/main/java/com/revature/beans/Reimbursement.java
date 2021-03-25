package com.revature.beans;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Reimbursement {
	
	public static enum ReimbursementType {
		LODGING, TRAVEL, FOOD, OTHER
	}
	public static enum StatusType {
		PENDING, APPROVED, DENIED
	}
	
	private int id;
	private double amount;
	private LocalDateTime submitted;
	private String description;
	private int authorId;
	private int resolverId;
	private StatusType status;
	private ReimbursementType type;
	
	

	
	// might edit to emit the status and type id, don't really need but will keep for now
	@Override
	public String toString() {
		return "[" + id + ", " + amount + ", " + submitted + ", " + description + ", " + authorId + ", " + resolverId + ", " + status + "]";
	}
	
//	@Override
//	public String toString() {
//		return "Reimbursement [id=" + id + ", amount=" + amount + ", submitted=" + submitted + ", description="
//				+ description + ", author=" + authorId + ", resolver=" + resolverId
//				+ ", status=" + status + ", type=" + type + "]";
//	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public LocalDateTime getSubmitted() {
		return submitted;
	}
	public void setSubmitted(LocalDateTime submitted) {
		this.submitted = submitted;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + authorId;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + resolverId;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((submitted == null) ? 0 : submitted.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reimbursement other = (Reimbursement) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (authorId != other.authorId)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (resolverId != other.resolverId)
			return false;
		if (status != other.status)
			return false;
		if (submitted == null) {
			if (other.submitted != null)
				return false;
		} else if (!submitted.equals(other.submitted))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	public StatusType getStatus() {
		return status;
	}
	public void setStatus(StatusType status) {
		this.status = status;
	}
	public ReimbursementType getType() {
		return type;
	}
	public void setType(ReimbursementType type) {
		this.type = type;
	}
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	public int getResolverId() {
		return resolverId;
	}
	public void setResolverId(int resolverId) {
		this.resolverId = resolverId;
	}

}
