package dev.mateusz.barber.demo.dto;

import java.util.Date;

public class DtoOrder {

	private int idOrder;

	private String date;

	private int price;

	private String status;

	private String service;

	private String userName;

	public DtoOrder() {

	}

	public DtoOrder(int idOrder, String date, int price, String status, String service,
			String userName) {
		this.idOrder = idOrder;
		this.date = date;
		this.price = price;
		this.status = status;
		this.service = service;
		this.userName = userName;
	}

	public int getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "DtoOrder [idOrder=" + idOrder + ", date=" + date +  ", price=" + price
				+ ", status=" + status + ", service=" + service + ", userName=" + userName + "]";
	}

}
