package CS4050.Software.BookIt.service;
import java.util.List;

import CS4050.Software.BookIt.model.User;
public interface UserService {
	public String getFirstName(String email);
	public String getAllMail();
	 public String findUserByEmail(String email);
	 public List <User> selectAll(String email);
	 public String verifyToken (String token);
	 public void saveToken(String email, String token, Integer status);
	 public void saveInfo(User user);
	 public void savePass(User user);
	 public void updateInfo(User user);
	 public String Decode(String email);
	 public String role(String email);
	 public void sendSimpleMessage(String email);
	 public void sendSimpleMessageEdit(String email);
	 public void sendSimpleMessageChangePass(String email);
	 public void sendSimpleMessageForgotPass(String email);
}
