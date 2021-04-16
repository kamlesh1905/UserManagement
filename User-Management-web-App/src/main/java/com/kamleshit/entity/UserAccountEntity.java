package com.kamleshit.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="USER_ACCOUNT")
public class UserAccountEntity 
{
	@Id
	@GeneratedValue
	@Column(name="USER_ID")
	private Integer userId;
	
	@Column(name="FIRST_NAME")
	private String firstName;

	@Column(name="LAST_NAME")
	private String userLastName;
	
	@Column(name="USER_EMAIL", unique=true)
	private String userEmail;
	
	@Column(name="USER_PWD")
	private String userPazzword;
	
	@Column(name="USER_MOBILE")
	private Integer userPhonenumber;

	@Column(name="DOB")
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	
	@Column(name="ACCT_STATUS")
	private String AcctStatus;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPazzword() {
		return userPazzword;
	}

	public void setUserPazzword(String userPazzword) {
		this.userPazzword = userPazzword;
	}

	public Integer getUserPhonenumber() {
		return userPhonenumber;
	}

	public void setUserPhonenumber(Integer userPhonenumber) {
		this.userPhonenumber = userPhonenumber;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAcctStatus() {
		return AcctStatus;
	}

	public void setAcctStatus(String acctStatus) {
		AcctStatus = acctStatus;
	}
	

	
	
	
	
	
	
	
	
	
	

}
