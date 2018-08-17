package br.inatel.dm112.interfaces;

import java.io.IOException;

import javax.ws.rs.PathParam;

import com.lowagie.text.DocumentException;

import br.inatel.dm112.model.DeliveryStatus;

public interface Delivery {

	public DeliveryStatus dispatchfOrder(String orderNumber);

	public DeliveryStatus startDeliveryOfOrder(String orderNumber, Integer isDelivery) throws DocumentException, IOException;
}