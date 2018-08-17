package br.inatel.dm112.client;

import java.util.Date;
import java.util.HashMap;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.JerseyClientBuilder;

import br.inatel.dm112.model.Order;
import br.inatel.dm112.model.OrderResponse;
import br.inatel.dm112.model.Orders;

public class OrderClient {

	//local
	private String restURL = "http://localhost:8080/PedidoDM112/rest/";
	//GAE:
	//private String restURL = "http://exemplodm112.appspot.com/rest/";
	
	public Order retrieveOrder(String orderNumber) {
		Client client = JerseyClientBuilder.createClient();

		WebTarget webTarget = client.target(restURL).path("order").path(orderNumber);

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		// GET
		Response response = invocationBuilder.get();

		System.out.println(response.getStatus());
		Order order = response.readEntity(Order.class);
		return order;
	}

	public Orders searchOrders(String cpf) {
		Client client = JerseyClientBuilder.createClient();

		WebTarget webTarget = client.target(restURL).path("ordersByCPF").path(cpf);

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		// GET
		Response response = invocationBuilder.get();

		System.out.println(response.getStatus());
		Orders orders = response.readEntity(Orders.class);
		return orders;
	}

	public OrderResponse updateOrder(Order order) {

		Client client = JerseyClientBuilder.createClient();
		
		WebTarget webTarget = client.target(restURL).path("updateOrder");
		
		Invocation.Builder invocationBuilder = webTarget.request();

		// POST
		Response response = invocationBuilder.post(Entity.entity(order, MediaType.APPLICATION_JSON));
		OrderResponse orderResp = response.readEntity(OrderResponse.class);

//		Order response = invocationBuilder.post(Entity.entity(order, MediaType.APPLICATION_JSON), Order.class);
		return orderResp;
	}
	
	public OrderResponse saveOrder(Order order) {

		Client client = JerseyClientBuilder.createClient();
		
		WebTarget webTarget = client.target(restURL).path("saveOrder");
		
		Invocation.Builder invocationBuilder = webTarget.request();

		// PUT
		Response response = invocationBuilder.post(Entity.entity(order, MediaType.APPLICATION_JSON));
		OrderResponse orderResp = response.readEntity(OrderResponse.class);

//		Order response = invocationBuilder.post(Entity.entity(order, MediaType.APPLICATION_JSON), Order.class);
		return orderResp;
	}

	// https://jersey.java.net/documentation/latest/client.html
	public static void main(String[] args) {

		OrderClient client = new OrderClient();
		
		HashMap<OrderResponse, Order> orderResponses = new HashMap<>();

		OrderResponse resposta = new OrderResponse();
		Order order = new Order();
		order.setNumber(123);
		order.setCpf("111.111.111-11");
		order.setOrderDate(new Date());
		order.setStatus(0);
		order.setValue(500);
		order.setCep("37540-000");
		order.setAddress("Rua x, bairro y, numero w");
		resposta = client.saveOrder(order);
		orderResponses.put(resposta, order);
		
		resposta = new OrderResponse();
		order = new Order();
		order.setNumber(124);
		order.setCpf("111.111.111-11");
		order.setOrderDate(new Date());
		order.setStatus(0);
		order.setValue(550);
		order.setCep("37540-000");
		order.setAddress("Rua x, bairro y, numero w");
		resposta = client.saveOrder(order);
		orderResponses.put(resposta, order);
		
		resposta = new OrderResponse();
		order = new Order();
		order.setNumber(125);
		order.setCpf("111.111.111-11");
		order.setOrderDate(new Date());
		order.setStatus(0);
		order.setValue(600);
		order.setCep("37540-000");
		order.setAddress("Rua x, bairro y, numero w");
		resposta = client.saveOrder(order);
		orderResponses.put(resposta, order);
		
		for (OrderResponse resp : orderResponses.keySet()) {
			System.out.println("Status do update do pedido " + orderResponses.get(resp).getNumber() + ":  " + resp.getStatus());
		}

	}

}
