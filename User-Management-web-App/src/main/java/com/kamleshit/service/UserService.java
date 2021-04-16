package com.kamleshit.service;

import java.util.Map;

import com.kamleshit.entity.UserAccountEntity;

public interface UserService 
{	
	public String loginCheck(String email, String pwd);

    public Map<Integer, String> getCountries();

    public Map<Integer, String> getStates(Integer countryId);

    public Map<Integer, String> getCities(Integer stateId);

    public UserAccountEntity getUserByEmail(String emailId);

    public Boolean saveUser(UserAccountEntity userAcc);

    //public String unlockAccount(UnlockAccount acc);

    public Boolean forgotPassword(String emailId);

}
