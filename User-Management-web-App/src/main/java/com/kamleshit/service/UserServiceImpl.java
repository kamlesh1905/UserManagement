package com.kamleshit.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import com.kamleshit.domain.UnlockAccount;
import com.kamleshit.entity.CitiesMasterEntity;
import com.kamleshit.entity.CountryMasterEntity;
import com.kamleshit.entity.StateMasterEntity;
import com.kamleshit.entity.UserAccountEntity;
import com.kamleshit.repository.CitiesMasterRepository;
import com.kamleshit.repository.CountryMasterRepository;
import com.kamleshit.repository.StatesMasterRepository;
import com.kamleshit.repository.UserAccountsRepository;
import com.kamleshit.utils.EmailUtils;

public class UserServiceImpl implements UserService
{

	@Autowired
	private UserAccountsRepository userAccRepo;
	
	@Autowired
	private CountryMasterRepository countrysRepo;
	
	@Autowired
	private CitiesMasterRepository citiesRepo;
	
	@Autowired
	private StatesMasterRepository statesRepo;
	
	@Autowired
	private EmailUtils emailUtils;
	
	
	@Override
	public String loginCheck(String email, String pwd) {
		UserAccountEntity entity = userAccRepo.findByUserEmailAndUserPazzword(email, pwd);
		
		if(entity!=null)
		{
			String acctStatus = entity.getAcctStatus();
			
			if(acctStatus.equals("UNLOCKED"))
			{
				return "Success";
			}
			else
			{
				return "Account is locked";
			}
		}
		else
		{
			return "Invalid Credential";
		}
		
	}

	@Override
	public Map<Integer, String> getCountries() {
		
		List<CountryMasterEntity> findAll = countrysRepo.findAll();
		
		Map<Integer,String> countries = new HashMap<>();
		
		findAll.forEach(entity ->{
			countries.put(entity.getCountryId(), entity.getCountryName());
		});
		
		return countries;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) 
	{
		List<StateMasterEntity> entities = statesRepo.findByCountryId(countryId);
		
		Map<Integer,String> states = new HashMap<>();
		
		entities.forEach(entity->{
			states.put(entity.getStateId(), entity.getStateName());
		});
		
		return states;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {
		
		List<CitiesMasterEntity> entities = citiesRepo.findByStateId(stateId);
		
		Map<Integer,String> cities = new HashMap<>();
		
		entities.forEach(entity->{
			cities.put(entity.getCityId(), entity.getCityName());
		});
		
		
		return cities;
	}

	@Override
	public UserAccountEntity getUserByEmail(String emailId) {
		UserAccountEntity entity = userAccRepo.findByUserEmail(emailId);
		return entity;
	}

	@Override
	public Boolean saveUser(UserAccountEntity userAcc) {
		
		String tempPwd = generateRandomPwd(5);
		userAcc.setUserPazzword(tempPwd);
		userAcc.setAcctStatus("LOCKED");
		
		UserAccountEntity save = userAccRepo.save(userAcc);
		
		if(save.getUserId()!=null)
		{
			Boolean  isSent = sendAcctRegEmail(userAcc);
			
			return isSent;
		}
		else
		{
			return false;
		}
		
	}
	
	
	@Override
	public String unlockAccount(UnlockAccount acc) 
	{
		UserAccountEntity user = userAccRepo.findByUserEmail(acc.getEmail());
		if(user!=null && user.getUserPazzword().equals(acc.getTempPwd()))
		{
			user.setUserPazzword(acc.getNewPwd());
			user.setAcctStatus("UNLOCKED");
			userAccRepo.save(user);
			return "Account Unlock Successfully";
		}
		return "Incorrect Temporary password";
	}
	

	@Override
	public Boolean forgotPassword(String emailId) 
	{
		UserAccountEntity user = userAccRepo.findByUserEmail(emailId);	
		if(user!=null && user.getAcctStatus().equals("UNLOCKED"))
		{
			return sendPwdToUserEmail(user);
		}
		return false;
	}
	
	
	
	private String generateRandomPwd(Integer length)
	{
		byte[] array = new byte[length];
		new Random().nextBytes(array);
		String generatedString = new String(array, Charset.forName("UTF-8"));
		return generatedString;
	}
	
	
	private Boolean sendAcctRegEmail(UserAccountEntity userAcc)
	{
		String body = getEmailBodyTxt(userAcc,"RECOVER_PASSWORD_EMAIL_BOBY_TEMPLATE.txt");
		emailUtils.sendEmail("Registration Successfull", "body", userAcc.getUserEmail());
		
		return true;
	}
	
	private Boolean sendPwdToUserEmail(UserAccountEntity userAcc)
	{
		String body = getEmailBodyTxt(userAcc,"UNLOCK-ACC-EMAIL-BODY-TEMPLATE.txt");
		emailUtils.sendEmail("Password Recovery", body, userAcc.getUserEmail());
		return true;
		
	}
	
	/*
	 * private String getPwdRecoverEmailBody(UserAccountEntity userAcc) { String
	 * mailBody = null;
	 * 
	 * StringBuilder sb = new StringBuilder();
	 * 
	 * try { FileReader fr = new FileReader("UNLOCK-ACC-EMAIL-BODY-TEMPLATE.txt");
	 * BufferedReader br = new BufferedReader(fr);
	 * 
	 * String line = br.readLine();// firstline read
	 * 
	 * while(line!=null) { sb.append(line); line=br.readLine(); // second line and
	 * so on } br.close();
	 * 
	 * mailBody = sb.toString(); //converting string builder to string
	 * 
	 * mailBody.replace("{FNAME}", userAcc.getFirstName());
	 * mailBody.replace("{LNAME}", userAcc.getUserLastName());
	 * mailBody.replace("{TEMP-PWD}", userAcc.getUserPazzword());
	 * mailBody.replace("{EMAIL}", userAcc.getUserEmail());
	 * 
	 * 
	 * } catch (Exception e) {
	 * 
	 * e.printStackTrace(); }
	 * 
	 * 
	 * return mailBody;
	 * 
	 * }
	 * 
	 * 
	 * private String getAccRegEmailBodyTxt(UserAccountEntity userAcc) { String
	 * mailBody = null;
	 * 
	 * StringBuilder sb = new StringBuilder();
	 * 
	 * try { FileReader fr = new
	 * FileReader("RECOVER_PASSWORD_EMAIL_BOBY_TEMPLATE.txt"); BufferedReader br =
	 * new BufferedReader(fr);
	 * 
	 * String line = br.readLine();// firstline read
	 * 
	 * while(line!=null) { sb.append(line); line=br.readLine(); // second line and
	 * so on } br.close();
	 * 
	 * mailBody = sb.toString(); //converting string builder to string
	 * 
	 * mailBody.replace("{FNAME}", userAcc.getFirstName());
	 * mailBody.replace("{LNAME}", userAcc.getUserLastName());
	 * mailBody.replace("{PWD}", userAcc.getUserPazzword());
	 * 
	 * } catch (Exception e) {
	 * 
	 * e.printStackTrace(); }
	 * 
	 * 
	 * return mailBody;
	 * 
	 * }
	 */
	
	private String getEmailBodyTxt(UserAccountEntity userAcc,String filename) 
	{
		String mailBody = null;
		
		StringBuilder sb = new StringBuilder();
		
		try 
		{
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			
			String line = br.readLine();// firstline read
			
			while(line!=null)
			{
				sb.append(line);
				line=br.readLine(); // second line and so on
			}
			br.close();
			
			mailBody = sb.toString(); //converting string builder to string
			
			mailBody.replace("{FNAME}", userAcc.getFirstName());
			mailBody.replace("{LNAME}", userAcc.getUserLastName());
			mailBody.replace("{TEMP-PWD}", userAcc.getUserPazzword());
			mailBody.replace("{EMAIL}", userAcc.getUserEmail());
			
			
		} 
		catch (Exception e) 
		{
			
			e.printStackTrace();
		}
		
			
		return mailBody;
		
	}
	
	
	

	

}
