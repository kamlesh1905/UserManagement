package com.kamleshit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="STATE_MASTER")
public class StateMasterEntity 
{

		@Id
		@GeneratedValue
		@Column(name="STATE_ID")
		private Integer stateId;
		
		@Column(name="STATE_NAME")
		private String stateName;
				
		@Column(name="COUNTRY_ID")
		private Integer countryId;

		public Integer getStateId() {
			return stateId;
		}

		public void setStateId(Integer stateId) {
			this.stateId = stateId;
		}

		public String getStateName() {
			return stateName;
		}

		public void setStateName(String stateName) {
			this.stateName = stateName;
		}

		public Integer getCountry() {
			return getCountry();
		}

		public void setCountry(Integer country) {
			this.countryId = country;
		}
		
		
		
}
