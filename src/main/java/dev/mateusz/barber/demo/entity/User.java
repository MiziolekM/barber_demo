package dev.mateusz.barber.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user")
	private int idUser;
	
	@Column(unique = true, name = "username")
	private String userName;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(unique = true, name = "phone_number")
	private int phoneNumber;
	
	@Column(unique = true, name = "email")
	private String email;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
		@JoinTable(name = "users_roles", 
		joinColumns = {@JoinColumn(name = "user_id")}, 
		inverseJoinColumns = {@JoinColumn(name = "role_id")})
	private List<Role> roles;
	
	/*
	{CascadeType.DETACH,
		CascadeType.MERGE,
		CascadeType.PERSIST,
		CascadeType.REFRESH}*/
	
	@OneToMany(fetch = FetchType.LAZY, 
			mappedBy = "user", orphanRemoval = true,
			cascade = {CascadeType.ALL}, 
			targetEntity = Order.class)
	private List<Order> orders;
	
	public User() {
		
	}

	public User(String userName, String password, String firstName, String lastName, int phoneNumber, String email,
			List<Role> roles) {
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.roles = roles;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [id_user=" + idUser + ", userName=" + userName + ", password=" + password + ", firstName="
				+ firstName + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber + ", email=" + email
				+ ", roles=" + roles + "]";
	}
	
	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	// funkcja dodatkowa do relacki dwukierunkowej
	public void add (Order theOrder) {
		if(orders == null) {
			orders = new ArrayList<>();
		}
		
		orders.add(theOrder);
		theOrder.setUser(this);
	}
	
}
