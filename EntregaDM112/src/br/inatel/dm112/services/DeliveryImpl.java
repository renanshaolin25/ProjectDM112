package br.inatel.dm112.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import br.inatel.dm112.client.EmailClient;
import br.inatel.dm112.client.OrderClient;
import br.inatel.dm112.client.mail.stub.MailStatusResponse;
import br.inatel.dm112.interfaces.Delivery;
import br.inatel.dm112.model.DeliveryStatus;
import br.inatel.dm112.model.Order;
import br.inatel.dm112.model.OrderResponse;
import br.inatel.dm112.model.ResponseStatus;

@Path("/")
public class DeliveryImpl implements Delivery {
	
	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/dispatchfOrder/{orderNumber}")
	public DeliveryStatus dispatchfOrder(@PathParam("orderNumber") String orderNumber) {
		if (orderNumber == null) {
			return new DeliveryStatus(-1, "null", "null");
		}else {
			//Expedição de pedido
			OrderClient clientOrder = new OrderClient();
			Order order = clientOrder.retrieveOrder(orderNumber); //consulta o pedido pelo número
			
			if(order != null) { //alguma hora vai ser preciso verificar o status do pedido aqui
				order.setForwardDate(new Date());
				order.setStatus(Order.STATUS.FORWARDED.ordinal()); //confirma a expediçao
				OrderResponse respOrder = clientOrder.updateOrder(order); //atualiza o status do pedido
				
				if(respOrder.getStatus() == 1) { //OK
					return new DeliveryStatus(ResponseStatus.FORWARDED.ordinal(), order.getCpf(), String.valueOf(order.getNumber()));
				} else {
					System.out.println("Erro no serviço de pedido: update");
				}
			} else {
				System.out.println("Erro no serviço de pedido: get");
			}
		}
		
		return new DeliveryStatus(-1, "ERROR", "ERROR");
	}

	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/startDeliveryOfOrder/{orderNumber}/{isDelivery}")
	public DeliveryStatus startDeliveryOfOrder(@PathParam("orderNumber")String orderNumber, @PathParam("isDelivery")Integer isDelivery) throws DocumentException, IOException {
		if (orderNumber == null || isDelivery == null) {
			return new DeliveryStatus(-1, "null", "null");
		}else if(isDelivery == 0) {
			//lógica de pendência de entrega
			//instancia os clientes
			OrderClient clientOrder = new OrderClient();
			
			Order order = clientOrder.retrieveOrder(orderNumber); //consulta o pedido pelo número
			order.setDeliveryDate(new Date());
			order.setStatus(Order.STATUS.NOBODY_HOME.ordinal()); //pedido expedido
			OrderResponse respOrder = clientOrder.updateOrder(order); //atualiza o status do pedido
			return new DeliveryStatus(ResponseStatus.NOBODY_HOME.ordinal(), order.getCpf(), String.valueOf(order.getNumber()));
		}else if(isDelivery == 1) {
			//lógica de entrega
			//instancia os clientes
			OrderClient clientOrder = new OrderClient();
			EmailClient clientEmail = new EmailClient();
			
			Order order = clientOrder.retrieveOrder(orderNumber); //consulta o pedido pelo número
			
			if(order != null) { //alguma hora vai ser preciso verificar o status do pedido aqui
				order.setDeliveryDate(new Date());
				order.setStatus(Order.STATUS.DELIVERED.ordinal()); //pedido expedido
				OrderResponse respOrder = clientOrder.updateOrder(order); //atualiza o status do pedido
				
				if(respOrder.getStatus() == ResponseStatus.DELIVERED.ordinal()) { //OK
					// Criando nota fiscal
					byte[] PDFContent = null;
					try {
						Document nf = new Document();
						PdfWriter.getInstance( nf, new FileOutputStream("/home/renan/nota_fiscal.pdf"));
						nf.open();
						nf.add(new Paragraph("Nota fiscal para o pedido XXX"));
						nf.close();
						
						FileInputStream fis = new FileInputStream("/home/renan/nota_fiscal.pdf");
						byte[] buffer = new byte[1 * 1024 * 1024]; // 1 MB
						int size = fis.read(buffer);
						PDFContent = new byte[size];
						System.arraycopy(buffer, 0, PDFContent, 0, size);
						fis.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					MailStatusResponse respEmail = clientEmail.callSendMailService( //envia email com o pdf
							"robertorr9@gmail.com", "robertodm112","rrocha.roberto@gmail.com", PDFContent);
					
					if(respEmail.getStatus() == 1) {//OK
						return new DeliveryStatus(ResponseStatus.DELIVERED.ordinal(), order.getCpf(), String.valueOf(order.getNumber())); //Retorna sucesso
					} else {
						System.out.println("Erro no serviço de email");
					}
					
				} else {
					System.out.println("Erro no serviço de pedido: update");
				}
			} else {
				System.out.println("Erro no serviço de pedido: get");
			}
		} 
		return new DeliveryStatus(-1, "ERROR", "ERROR");
	}

	

}