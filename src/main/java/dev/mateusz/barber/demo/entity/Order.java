package dev.mateusz.barber.demo.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_order")
	private int idOrder;

	@Column(name = "date")
	private Date date;

	@Column(name = "price")
	private int price;

	@Column(name = "status")
	private String status;
	
	@Column(name = "service")
	private String service;

	@ManyToOne(cascade = { CascadeType.DETACH,
		CascadeType.MERGE,
		CascadeType.PERSIST,
		CascadeType.REFRESH})
	@JoinColumn(name = "id_user")
	private User user;
	
	public Order() {
		
	}

	public Order(Date date, int price, String status, String service, User user) {
		this.date = date;
		this.price = price;
		this.status = status;
		this.service = service;
		this.user = user;
	}

	public int getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Order [idOrder=" + idOrder + ", date=" + date + ", price=" + price
				+ ", status=" + status + ", service=" + service + ", user=" + user + "]";
	}

}
