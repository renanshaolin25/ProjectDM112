package br.inatel.dm112.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "order")
public class Order {

	@XmlTransient
	public static enum STATUS {
		PROCESSING, FORWARDED, DELIVERED, NOBODY_HOME
	}

	private int number;

	private String cpf;

	private float value;

	private int status;

	private Date orderDate;

	private Date forwardDate;

	private Date deliveryDate;

	private String address;

	private String cep;

	public Order() {
	}

	public Order(int number, String cpf, float value, int status, Date orderDate, Date forwardDate, Date deliveryDate,
			String address, String cep) {
		super();
		this.number = number;
		this.cpf = cpf;
		this.value = value;
		this.status = status;
		this.orderDate = orderDate;
		this.forwardDate = forwardDate;
		this.deliveryDate = deliveryDate;
		this.cep = cep;
		this.address = address;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getForwardDate() {
		return forwardDate;
	}

	public void setForwardDate(Date forwardDate) {
		this.forwardDate = forwardDate;
	}

	@Override
	public String toString() {
		return "Order [number=" + number + ", cpf=" + cpf + ", value=" + value + ", status=" + status + ", orderDate="
				+ orderDate + ", forwardDate=" + forwardDate + ", deliveryDate=" + deliveryDate + ", address=" + address
				+ ", cep=" + cep + "]";
	}

}
