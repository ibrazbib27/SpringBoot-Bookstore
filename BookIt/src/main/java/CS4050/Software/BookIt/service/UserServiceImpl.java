package CS4050.Software.BookIt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.transaction.annotation.Transactional;

import CS4050.Software.BookIt.model.User;

import CS4050.Software.BookIt.repository.UserRepository;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {

	
	 @Autowired
	 private EntityManager em;
	 
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    
    @Autowired
    private JavaMailSender emailSender;
    
    @Override
    public void sendSimpleMessage(String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Account Activation");
        message.setText("Congratulations on registering an account with Bookit! Please go to the following url in order to activate your account: http://localhost:8080/accountactivation?token="+bCryptPasswordEncoder.encode(email.trim()));
        message.setTo(email);
        message.setFrom("BookIt!");

        emailSender.send(message);
    }

	@Override
	public String findUserByEmail(String email) {
		String emailVal = "";
		String findEmail = "SELECT email FROM user_info WHERE email = '"+email+"'";
		Query query = em.createNativeQuery(findEmail);
		List results = query.getResultList();
		emailVal += results.toString();
		return emailVal;
				//userRepository.findByEmail(email);
	}
	
	@Override
	public String getFirstName(String email) {
		String nameVal = "";
		String nameSearch = "SELECT first_name FROM user_info WHERE email = '"+email+"'";
		Query query = em.createNativeQuery(nameSearch);
		List results = query.getResultList();
		nameVal += results.toString();
		nameVal = nameVal.replace('[', ' ');
		nameVal = nameVal.replace(']', ' ');
		nameVal = nameVal.trim();
		return nameVal;
				//userRepository.findByEmail(email);
	}
	
	@Override
	public String verifyToken (String email) {
		String tokenVal = "";
		String findtoken = "SELECT token FROM user_info WHERE email = '"+email+"'";
		Query query = em.createNativeQuery(findtoken);
		List results = query.getResultList();
		tokenVal += results.toString();
		return tokenVal;
				//userRepository.findByEmail(email);
	}

	@Override
	@Transactional
	public void saveInfo(User user) {
			 em.joinTransaction();
			 String tables = "(first_name, last_name, email, password";
			 String values = "(?,?,?,?";
			 int count = 0;
			 if(user.getAddress().equals(null)) {
					user.setAddress(" ");
				 }
				 if(user.getAddressBill().equals(null)) {
						user.setAddressBill(" ");
					 }
				 if(user.getCity().equals(null)) {
						user.setCity(" ");
					 }
				 if(user.getCityBill().equals(null)) {
						user.setCityBill(" ");
					 }
				 if(user.getState().equals(null)) {
						user.setState(" ");
					 }
				 if(user.getStateBill().equals(null)) {
						user.setStateBill(" ");
					 }
				 if(user.getZip().equals(null)) {
						user.setZip(" ");
					 }
				 if(user.getZipBill().equals(null)) {
						user.setZipBill(" ");
					 }
				 if(user.getCardName().equals(null)) {
						user.setCardName(" ");
					 }
				 if(user.getCardNumber().equals(null)) {
						user.setCardNumber(" ");
					 }
				 else {
					 String encodedString = Base64.getEncoder().encodeToString((user.getCardNumber()).getBytes());
					 user.setCardNumber(encodedString);
				 }
				 if(user.getCvv().equals(null)) {
						user.setCvv(" ");
					 }
				 else {
					 String encodedString = Base64.getEncoder().encodeToString((user.getCvv()).getBytes());
					 user.setCvv(encodedString);
				 }
					
				 
			 user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			 em.createNativeQuery("INSERT INTO user_info (first_name, last_name, email, password, street_address, street_bill_address, city, city_bill, state, state_bill, zip, zip_bill, card_name, card_number, cvv, card_exp, card_type) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
		      .setParameter(1, user.getFirstName())
		      .setParameter(2, user.getLastName())
		      .setParameter(3, user.getEmail())
		      .setParameter(4, user.getPassword())
		      .setParameter(5, user.getAddress())
		      .setParameter(6, user.getAddressBill())
		      .setParameter(7, user.getCity())
		      .setParameter(8, user.getCityBill())
		      .setParameter(9, user.getState())
		      .setParameter(10, user.getStateBill())
		      .setParameter(11, user.getZip())
		      .setParameter(12, user.getZipBill())
		      .setParameter(13, user.getCardName())
		      .setParameter(14, user.getCardNumber())
		      .setParameter(15, user.getCvv())
		      .setParameter(17, user.getCardType())
		      .setParameter(16, user.getCardExp())
		      .executeUpdate();
		
	}

	@Override
	@Transactional
	public void saveToken(String email, String token, Integer status) {
		 em.joinTransaction();
		String updateToken = "UPDATE user_info SET token = ?, status = ? WHERE email = ?";
		 em.createNativeQuery(updateToken)
		        .setParameter(1, token)
		        .setParameter(2, email)
				.executeUpdate();
		
	}

	@Override
	public String Decode(String email) {
		String passwordVal = "";
		String findPass = "SELECT password FROM user_info WHERE email = '"+email+"'";
		Query query = em.createNativeQuery(findPass);
		List results = query.getResultList();
		passwordVal += results.toString();
		passwordVal = passwordVal.replace('[', ' ');
		passwordVal = passwordVal.replace(']', ' ');
		passwordVal = passwordVal.trim();
		return passwordVal;
	}

	@Override
	public String role(String email) {
		String roleVal = "";
		String findRole = "SELECT role FROM user_info WHERE email = '"+email+"'";
		Query query = em.createNativeQuery(findRole);
		List results = query.getResultList();
		roleVal += results.toString();
		roleVal = roleVal.replace('[', ' ');
		roleVal = roleVal.replace(']', ' ');
		roleVal = roleVal.trim();
		return roleVal;
	}
	@Override
	public String getAllMail() {
		String roleVal = "";
		String getallemail = "SELECT email FROM user_info";
		Query query = em.createNativeQuery(getallemail);
		List results = query.getResultList();
		roleVal += results.toString();
		roleVal = roleVal.replace('[', ' ');
		roleVal = roleVal.replace(']', ' ');
		roleVal = roleVal.trim();
		return roleVal;
	}

	@Override
	public List <User> selectAll(String email) {
	
		String findAll = "SELECT * FROM user_info WHERE email = '"+email+"'";
		Query query = em.createNativeQuery(findAll, User.class);
		 List<User> userList =  query.getResultList();
		
		return userList;
		
	}

	@Override
	@Transactional
	public void updateInfo(User user) {
		// TODO Auto-generated method stub
		 em.joinTransaction();
			String updateToken = "UPDATE user_info SET first_name =  ? , last_name =  ?  , street_address =  ? , street_bill_address =  ? , city =  ? , city_bill =  ? , state =  ? , state_bill =  ? , zip =  ? , zip_bill =  ? , card_name =  ? , card_number =  ? , cvv =  ? , card_exp =  ? , card_type = ? WHERE email = ?";
			 em.createNativeQuery(updateToken)
			 .setParameter(1, user.getFirstName())
		      .setParameter(2, user.getLastName())
		      .setParameter(3, user.getAddress())
		      .setParameter(4, user.getAddressBill())
		      .setParameter(5, user.getCity())
		      .setParameter(6, user.getCityBill())
		      .setParameter(7, user.getState())
		      .setParameter(8, user.getStateBill())
		      .setParameter(9, user.getZip())
		      .setParameter(10, user.getZipBill())
		      .setParameter(11, user.getCardName())
		      .setParameter(12, user.getCardNumber())
		      .setParameter(13, user.getCvv())
		      .setParameter(14, user.getCardExp())
		      .setParameter(15, user.getCardType())
		      .setParameter(16, user.getEmail())
			  .executeUpdate();
		
	}

	@Override
	public void sendSimpleMessageEdit(String email) {
		 SimpleMailMessage message = new SimpleMailMessage();
	        message.setSubject("Edit Profile Comfirmation");
	        message.setText("You have successfully updated your profile!");
	        message.setTo(email);
	        message.setFrom("BookIt!");

	        emailSender.send(message);
		
	}

	@Override
	public void sendSimpleMessageChangePass(String email) {
		 SimpleMailMessage message = new SimpleMailMessage();
	        message.setSubject("Password Reset Comfirmation");
	        message.setText("You have successfully rested your password!");
	        message.setTo(email);
	        message.setFrom("BookIt!");

	        emailSender.send(message);
		
	}

	@Override
	public void sendSimpleMessageForgotPass(String email) {
		 SimpleMailMessage message = new SimpleMailMessage();
	        message.setSubject("Forgot Password- Password Update Comfirmation");
	        message.setText("You have successfully rested your password!");
	        message.setTo(email);
	        message.setFrom("BookIt!");

	        emailSender.send(message);
		
		
	}

	@Override
	@Transactional
	public void savePass(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		 em.joinTransaction();
			String updateToken = "UPDATE user_info SET password = ? WHERE email = ?";
			 em.createNativeQuery(updateToken)
			        .setParameter(1, user.getPassword())
			        .setParameter(2, user.getEmail())
					.executeUpdate();
		
		// TODO Auto-generated method stub
		
	}

	


}
