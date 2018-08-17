package br.inatel.dm112.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.inatel.dm112.model.Order.STATUS;

@Entity
@Table(name = "Pedido")
public class OrderEntity {

	@Id
	@Column(name = "numero")
	private int number;

	private String cpf;

	@Column(name = "valor")
	private float value;

	private int status;

	@Column(name = "dataPedido", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date orderDate;

	@Column(name = "dataExpedição", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date forwardDate;

	@Column(name = "dataEntrega", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date deliveryDate;

	@Column(name = "address")
	private String address;

	@Column(name = "cep")
	private String cep;

	public OrderEntity() {
		this.status = STATUS.FORWARDED.ordinal();
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

	public Date getForwardDate() {
		return forwardDate;
	}

	public void setForwardDate(Date forwardDate) {
		this.forwardDate = forwardDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@Override
	public String toString() {
		return "OrderEntity [number=" + number + ", cpf=" + cpf + ", value=" + value + ", status=" + status
				+ ", orderDate=" + orderDate + ", forwardDate=" + forwardDate + ", deliveryDate=" + deliveryDate
				+ ", address=" + address + ", cep=" + cep + "]";
	}

}
