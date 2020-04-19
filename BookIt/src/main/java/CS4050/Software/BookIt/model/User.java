package CS4050.Software.BookIt.model;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "user_info")
public class User {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="account_id")
private long id;

@Column(name="email" , nullable = false)
private String email;

@Column(name="first_name" , nullable = false)
private String firstName;

@Column(name="last_name" , nullable = false)
private String lastName;

@Column(name="password" , nullable = false)
private String password;

@Column(name="street_address" , nullable = false)
private String address;

@Column(name="city" , nullable = false)
private String city;

@Column(name="state" , nullable = false)
private String state;

@Column(name="zip" , nullable = false)
private String zip;

@Column(name="card_number" , nullable = false)
private String cardNumber;


@Column(name="card_name" , nullable = false)
private String cardName;



@Column(name="cvv", nullable = false)
private String cvv;

@Column(name="card_type", nullable = false)
private String cardType;

@Column(name="card_exp" , nullable = false)
private String cardExp;

@Column(name="street_bill_address", nullable = false)
private String addressBill;

@Column(name="city_bill" , nullable = false)
private String cityBill;

@Column(name="state_bill" , nullable = false)
private String stateBill;

@Column(name="zip_bill" , nullable = false)
private String zipBill;

@Column(name="token" , nullable = false)
private String token;

@Column(name="role" , nullable = false)
private String role;

@Column(name="status" , nullable = true)
private Integer status;


public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getFirstName() {
	return firstName;
}
public void setFirstName(String firstName) {
	this.firstName = firstName;
}
public String getLastName() {
	return lastName;
}
public void setLastName(String lastName) {
	this.lastName = lastName;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public String getState() {
	return state;
}
public void setState(String state) {
	this.state = state;
}
public String getZip() {
	return zip;
}
public void setZip(String zip) {
	this.zip = zip;
}
public String getCardNumber() {
	return cardNumber;
}
public void setCardNumber(String cardNumber) {
	this.cardNumber = cardNumber;
}
public String getCvv() {
	return cvv;
}
public void setCvv(String cvv) {
	this.cvv = cvv;
}
public String getCardType() {
	return cardType;
}
public void setCardType(String cardType) {
	this.cardType = cardType;
}
public String getCardExp() {
	return cardExp;
}
public void setCardExp(String cardExp) {
	this.cardExp = cardExp;
}
public String getAddressBill() {
	return addressBill;
}
public void setAddressBill(String addressBill) {
	this.addressBill = addressBill;
}
public String getCityBill() {
	return cityBill;
}
public void setCityBill(String cityBill) {
	this.cityBill = cityBill;
}
public String getStateBill() {
	return stateBill;
}
public void setStateBill(String stateBill) {
	this.stateBill = stateBill;
}
public String getZipBill() {
	return zipBill;
}
public void setZipBill(String zipBill) {
	this.zipBill = zipBill;
}
public String getToken() {
	return token;
}
public void setToken(String token) {
	this.token = token;
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
public String getCardName() {
	return cardName;
}
public void setCardName(String cardName) {
	this.cardName = cardName;
}
public Integer getStatus() {
	return status;
}
public void setStatus(Integer status) {
	this.status = status;
}
@Override
public String toString() {
	return "User [id=" + id + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", password="
			+ password + ", address=" + address + ", city=" + city + ", state=" + state + ", zip=" + zip
			+ ", cardNumber=" + cardNumber + ", cardName=" + cardName + ", cvv=" + cvv + ", cardType=" + cardType
			+ ", cardExp=" + cardExp + ", addressBill=" + addressBill + ", cityBill=" + cityBill + ", stateBill="
			+ stateBill + ", zipBill=" + zipBill + ", token=" + token + ", role=" + role + "]";
}











}
