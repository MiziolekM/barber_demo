package dev.mateusz.barber.demo.dto;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import dev.mateusz.barber.demo.entity.Role;
import dev.mateusz.barber.demo.validation.FieldMatch;
import dev.mateusz.barber.demo.validation.ValidEmail;

@FieldMatch.List({
    @FieldMatch(first = "password", 
    		second = "matchingPassword", 
    		message = "Oba hasła muszą być zgodne")
})
public class CrmUser{

	private int idUser;
	
	@NotNull(message = "Wymagany")
	@Size(min = 3, max = 16, message = "Wymagany - od 3 do 16 znaków")
	@Pattern(regexp="^[a-zA-Z0-9]*", message="Tylko małe/duże litery oraz liczby")
	private String userName;
	
	@NotNull(message = "Wymagany")
	@Size(min = 3, max = 60, message = "Wymagany - od 3 do 16 znaków")
	private String password;
	
	@NotNull(message = "Wymagany")
	@Size(min = 3, max = 60,  message = "Wymagany - od 3 do 16 znaków")
	private String matchingPassword;
	
	@NotNull(message = "Wymagany")
	@Size(min = 3, max = 16, message = "Wymagany - od 3 do 16 znaków")
	@Pattern(regexp="^[a-zA-Z]*", message="Tylko małe/dużo litery")
	private String firstName;
	
	@NotNull(message = "Wymagany")
	@Size(min = 3, max = 16, message = "Wymagany - od 3 do 16 znaków")
	@Pattern(regexp="^[a-zA-Z]*", message="Tylko małe/dużo litery")
	private String lastName;
	
	@NotNull(message = "Wymagany")
	@Min(value=100000000, message="Numer telefonu musi zawierać dokładnie 9 cyfr")
	@Max(value=999999999, message="Numer telefonu musi zawierać dokładnie 9 cyfr")
	private int phoneNumber;
	
	@ValidEmail
	@NotNull(message = "Wymagany")
	@Size(min = 3, message = "Wymagany - od 3 do 16 znaków")
	private String email;
	
	private List<Role> roles;
	
	public CrmUser() {
		
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
}
