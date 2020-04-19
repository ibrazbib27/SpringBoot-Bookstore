package CS4050.Software.BookIt.controller;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import CS4050.Software.BookIt.model.User;
import CS4050.Software.BookIt.service.UserServiceImpl;


@Controller
public class UserController {
	 private List <User> userClass;
	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	private String edProfile = "";
private String token = "";
	@Autowired
	private UserServiceImpl userData;
	@Autowired
	private HttpServletRequest request;
	private String finalToken = "";
	@RequestMapping(value = "/", method= RequestMethod.GET)
    public String homePage() {
		
        return "index";
    }
	
	@RequestMapping(value = "/signout", method= RequestMethod.GET)
	  public String signOut() {
			
	        return "index";
	    }
		

	
	@RequestMapping(value = "/signup", method= RequestMethod.GET)
    public ModelAndView showForm() {
     
		
		 ModelAndView model = new ModelAndView();
		  User user = new User();
		  model.addObject("user", user);
		  model.setViewName("signup");
		  
		  return model;   
		  }
	
	@RequestMapping(value = "/signupcomplete", method= RequestMethod.POST)
	public  String submitForm(User user, BindingResult bindingResult) {
		  ModelAndView model = new ModelAndView();
		  System.out.println((userData.findUserByEmail(user.getEmail())).toString());
		if(((userData.findUserByEmail(user.getEmail())).toString().equals("[]"))) {
		
			userData.saveInfo(user);
			userData.sendSimpleMessage(user.getEmail());
			user.setEmail(" ");
		
			   model.addObject("user", new User());
	
		
		}
		
			
		
		
		 return "signup";
	}

	
	
	@RequestMapping(value = "/accountactivation", method= RequestMethod.GET)
    public ModelAndView accountActivation( String token, User user) {
		ModelAndView model = new ModelAndView(); 
		token = request.getQueryString();
		token = token.substring(6);
		System.out.println(token);
		String [] findToken = (userData.getAllMail()).split(",");
		for(String mail : findToken) {
		if(bCryptPasswordEncoder.matches(mail.trim(), token) == true) {
		System.out.println(mail);
		Integer status = new Integer(1);
		user.setStatus(status);
		userData.saveToken(mail.trim(), token, status);
		break;
		}
		}
        return model;
		  }
	
	
	
	
	 @RequestMapping(value= { "/login"}, method=RequestMethod.GET)
	 public ModelAndView login() {
	   ModelAndView model = new ModelAndView();
	  model.setViewName("login");

	  return model;
	 }
	 /*
	@RequestMapping(value = "/sessionActive", method= RequestMethod.POST)
	public ModelAndView  userReturn(@Valid User user, BindingResult bindingResult, ModelMap model, String token) {
		token = userData.verifyToken(user.getEmail());
	
		 System.out.println(user.getEmail() + " " + token);
if(user.getEmail().length() == 0 || user.getPassword().length() == 0) {
	bindingResult.rejectValue("email", "error.login", "Enter login email address here please");
	bindingResult.rejectValue("password", "error.login", "Enter login password here please");
	ModelAndView modelMap = new ModelAndView();
	  modelMap.addObject("login", new User());
	 return new ModelAndView("redirect:/login?error=true", model);
	
}
else if (token.equals("[]")) {
	bindingResult.rejectValue("email", "error.user", "You must activate your account in order to login. Access the link that was sent to your email when you first registered an account with BookIt.");
	ModelAndView modelMap = new ModelAndView();
	  modelMap.addObject("login", new User());
	 return new ModelAndView("redirect:/login?error=true", model);
	
}
else {
		 
		  return new ModelAndView("redirect:/home", model);
}

/*if(!((userData.findUserByEmail(user.getEmail())).toString().equals("[]"))) {
			 user.setFirstName(userData.getFirstName(user.getEmail()));
			String findPassword = userData.Decode(user.getEmail());
			String checkToken = "" +userData.verifyToken(user.getEmail());
			System.out.println(checkToken.length());
			if((checkToken.equals("[]")) && finalToken.length() > 1) {
				
		
				if(bCryptPasswordEncoder.matches(user.getPassword(), findPassword) == true) {
					String getRole = userData.role(user.getEmail());
					if(getRole.equalsIgnoreCase("Customer")) {
						userClass = userData.selectAll(user);
					
					return "memberlogin";
					}
					if(getRole.equalsIgnoreCase("Admin")) {
						
						return "adminlogin";
					}
				}
				else {
					bindingResult.rejectValue("password", "error.user", "The password you entered is not correct please try again");
					return "login"; //err
				}
			
				
			}
			else if((checkToken.equals("[]")) && finalToken.length() == 1) {
				
				bindingResult.rejectValue("email", "error.user", "Please click on the activation link sent to your email in order to login");
				return "login";//err
			}
	
			else {
				if(bCryptPasswordEncoder.matches(user.getPassword(), findPassword) == true) {
					String getRole = userData.role(user.getEmail());
					
					if(getRole.equalsIgnoreCase("Customer")) {
						userClass = userData.selectAll(user);
						
						return "memberlogin";
						}
						if(getRole.equalsIgnoreCase("Admin")) {
							
							return "adminlogin";
						}
				}
				else {
					bindingResult.rejectValue("password", "error.user", "The password you entered is not correct please try again");
					return "login"; //err
				}
				
			}
	       
		
		}
		else {
			bindingResult.rejectValue("email", "error.user", "The email you entered is not registered in our system. Please try again or create an account");
			return "login";//err
		}*/
		
	//}
	
	
	 @RequestMapping(value="/default", method=RequestMethod.GET)
	 public ModelAndView home(User user, ModelMap model) {
	 
		
		
		
	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		setUserClass( userData.selectAll(auth.getName()));
		  for(User att : getUserClass()) {
				user.setAddress(att.getAddress());
				user.setAddressBill(att.getAddressBill());
				user.setCardExp(att.getCardExp());
				user.setCardName(att.getCardName());
				byte[] decodedBytes = Base64.getDecoder().decode(att.getCardNumber());
				String decodedString = new String(decodedBytes);
				user.setCardNumber(decodedString);
				user.setCardType(att.getCardType());
				user.setCity(att.getCity());
				user.setCityBill(att.getCityBill());
				byte[] decodedBytes1 = Base64.getDecoder().decode(att.getCvv());
				String decodedString1 = new String(decodedBytes1);
				user.setCvv(decodedString1);
				user.setEmail(att.getEmail());
				user.setFirstName(att.getFirstName());
				user.setLastName(att.getLastName());
				user.setState(att.getState());
				user.setStateBill(att.getStateBill());
				user.setZip(att.getZip());
				user.setZipBill(att.getZipBill());
				user.setRole(att.getRole());
		  }
		  
		  System.out.println(user.getRole());
		 String redir = "";
		  if((user.getRole()).equalsIgnoreCase("customer")) {
			
			
				redir= "redirect:/home/customerlogin/memberlogin";
	
				}
				if((user.getRole()).equalsIgnoreCase("Admin")) {
					
					redir= "redirect:/home/adminhome/adminlogin"; 
				}
		 
				return new ModelAndView(redir, model);
	 }
	 
	 @RequestMapping(value="/home/customerlogin/memberlogin", method=RequestMethod.GET)
	 public ModelAndView customerhome(User user) {
		  ModelAndView model = new ModelAndView();
		  for(User att : getUserClass()) {
				user.setAddress(att.getAddress());
				user.setAddressBill(att.getAddressBill());
				user.setCardExp(att.getCardExp());
				user.setCardName(att.getCardName());
				byte[] decodedBytes = Base64.getDecoder().decode(att.getCardNumber());
				String decodedString = new String(decodedBytes);
				user.setCardNumber(decodedString);
				user.setCardType(att.getCardType());
				user.setCity(att.getCity());
				user.setCityBill(att.getCityBill());
				byte[] decodedBytes1 = Base64.getDecoder().decode(att.getCvv());
				String decodedString1 = new String(decodedBytes1);
				user.setCvv(decodedString1);
				user.setEmail(att.getEmail());
				user.setFirstName(att.getFirstName());
				user.setLastName(att.getLastName());
				user.setState(att.getState());
				user.setStateBill(att.getStateBill());
				user.setZip(att.getZip());
				user.setZipBill(att.getZipBill());
				user.setRole(att.getRole());
		  }
		   model.addObject("retUser", 
		  " " + user.getFirstName());
	
		 model.setViewName("home/customerlogin/memberlogin");
		  
		  return model;
	 }
	 @RequestMapping(value="/home/adminhome/adminlogin", method=RequestMethod.GET)
	 public ModelAndView adminhome(User user) {
		  ModelAndView model = new ModelAndView();
		  for(User att : getUserClass()) {
				user.setAddress(att.getAddress());
				user.setAddressBill(att.getAddressBill());
				user.setCardExp(att.getCardExp());
				user.setCardName(att.getCardName());
				byte[] decodedBytes = Base64.getDecoder().decode(att.getCardNumber());
				String decodedString = new String(decodedBytes);
				user.setCardNumber(decodedString);
				user.setCardType(att.getCardType());
				user.setCity(att.getCity());
				user.setCityBill(att.getCityBill());
				byte[] decodedBytes1 = Base64.getDecoder().decode(att.getCvv());
				String decodedString1 = new String(decodedBytes1);
				user.setCvv(decodedString1);
				user.setEmail(att.getEmail());
				user.setFirstName(att.getFirstName());
				user.setLastName(att.getLastName());
				user.setState(att.getState());
				user.setStateBill(att.getStateBill());
				user.setZip(att.getZip());
				user.setZipBill(att.getZipBill());
				user.setRole(att.getRole());
		  }
		
	
		 model.setViewName("home/adminhome/adminlogin");
		  
		  return model;
	 }
	public List<User> getUserClass() {
		return userClass;
	}

	public void setUserClass(List<User> userClass) {
		this.userClass = userClass;
	} 

	
/*	
	@RequestMapping(value = "/viewprofile")
	public String viewProfile(User user, Model mode) {
	
		for(User att : userClass) {
			user.setAddress(att.getAddress());
			user.setAddressBill(att.getAddressBill());
			user.setCardExp(att.getCardExp());
			user.setCardName(att.getCardName());
			byte[] decodedBytes = Base64.getDecoder().decode(att.getCardNumber());
			String decodedString = new String(decodedBytes);
			user.setCardNumber(decodedString);
			user.setCardType(att.getCardType());
			user.setCity(att.getCity());
			user.setCityBill(att.getCityBill());
			byte[] decodedBytes1 = Base64.getDecoder().decode(att.getCvv());
			String decodedString1 = new String(decodedBytes1);
			user.setCvv(decodedString1);
			user.setEmail(att.getEmail());
			user.setFirstName(att.getFirstName());
			user.setLastName(att.getLastName());
			user.setState(att.getState());
			user.setStateBill(att.getStateBill());
			user.setZip(att.getZip());
			user.setZipBill(att.getZipBill());
		}
		
		
		
		 return "viewprofile";
	}
	@RequestMapping(value = "/editprofileinfo", method= RequestMethod.GET)
    public String editModel(Model model,  User user) {
		for(User att : userClass) {
			user.setAddress(att.getAddress());
			user.setAddressBill(att.getAddressBill());
			user.setCardExp(att.getCardExp());
			user.setCardName(att.getCardName());
			byte[] decodedBytes = Base64.getDecoder().decode(att.getCardNumber());
			String decodedString = new String(decodedBytes);
			user.setCardNumber(decodedString);
			user.setCardType(att.getCardType());
			user.setCity(att.getCity());
			user.setCityBill(att.getCityBill());
			byte[] decodedBytes1 = Base64.getDecoder().decode(att.getCvv());
			String decodedString1 = new String(decodedBytes1);
			user.setCvv(decodedString1);
			user.setEmail(att.getEmail());
			user.setFirstName(att.getFirstName());
			user.setLastName(att.getLastName());
			user.setState(att.getState());
			user.setStateBill(att.getStateBill());
			user.setZip(att.getZip());
			user.setZipBill(att.getZipBill());
		}
		List<String> cardType = Arrays.asList("none", "AmericanExpress", "Discover", "Mastercard","Visa");
		List<String> states = Arrays.asList("Select a State", "none", "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "DistrictofColumbia", "Florida", "Georgia", "Guam", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "MinorOutlyingIslands", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "NewHampshire", "NewJersey", "NewMexico", "NewYork", "NorthCarolina", "NorthDakota", "NorthernMarianaIslands", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "PuertoRico", "RhodeIsland", "SouthCarolina", "SouthDakota", "Tennessee", "Texas", "U.S.VirginIslands", "Utah", "Vermont", "Virginia", "Washington", "WestVirginia", "Wisconsin", "Wyoming");
		model.addAttribute("type", cardType);
        model.addAttribute("statePick", states);
        model.addAttribute("statePickBill", states);
	
        model.addAttribute("user", user);
		  return "editprofile";    
		  }
	
	@RequestMapping(value = "/profileChangeComplete", method= RequestMethod.POST)
	public String editComplete( Model mode,  User user) {
		 
	      
		System.out.println(user.getEmail());
		
		
			userData.updateInfo(user);
			userData.sendSimpleMessageEdit(user.getEmail());
		
			userClass = userData.selectAll(user);
			
		
		
		 return "editprofile";
	}
	
	@RequestMapping(value = "/passwordreset", method= RequestMethod.GET)
    public String passwordReset(Model model, User user) {
		
		
		  
		  return "resetpassword";    
		  }
	
	
	
	
	
	@RequestMapping(value = "/passwordresetconfirmation", method= RequestMethod.POST)
	public String passwordResetConfirm(@Valid User user, BindingResult bindingResult, Model mode) {
		
		
if(bCryptPasswordEncoder.matches(user.getPassword(), userData.Decode(user.getEmail()))) {
	bindingResult.rejectValue("password", "error.user", "The password you entered is incorrect, please try again");
	return "passwordreset"; //err
}
else {
	userData.savePass(user);
	
	userClass = userData.selectAll(user);
	return "resetpasswordconfirm"; 
}
	
	
	}   
	
	
	
	
	
	
	@RequestMapping(value = "/forgotpassword", method= RequestMethod.GET)
    public String passwordForgot(Model model, User user) {
		

		  
		  return "forgotpassword";    
		  }
	
	@RequestMapping(value = "/forgotpasswordconfirm", method= RequestMethod.POST)
	public String passwordForgotConfirmm(@Valid User user, BindingResult bindingResult, Model mode) {
		
		if(!((userData.findUserByEmail(user.getEmail())).toString().equals("[]"))) {
			
			userData.savePass(user);
			userData.sendSimpleMessageForgotPass(user.getEmail());
			
			return "forgotpasswordconfirm"; 
		}
		
		else  {
	bindingResult.rejectValue("email", "error.user", "The email you entered is not registered in our system. Please try again");
	return "forgotpassword"; //err
}
	}*/
	
}

