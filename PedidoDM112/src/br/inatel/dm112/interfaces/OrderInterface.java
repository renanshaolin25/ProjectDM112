package br.inatel.dm112.interfaces;

import br.inatel.dm112.model.Order;
import br.inatel.dm112.model.OrderResponse;
import br.inatel.dm112.model.Orders;

public interface OrderInterface {

	public Order getOrder(int orderNumber);

	public Orders searchOrdersByCPF(String cpf);
	
	public Orders searchOrdersByCep(String cep);

	public OrderResponse updateOrder(Order order);
	
	public OrderResponse saveOrder(Order order);

}