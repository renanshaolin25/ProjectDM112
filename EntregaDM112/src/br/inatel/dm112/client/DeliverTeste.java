package br.inatel.dm112.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import br.inatel.dm112.client.mail.stub.MailStatusResponse;
import br.inatel.dm112.model.DeliveryStatus;
import br.inatel.dm112.model.Order;
import br.inatel.dm112.model.OrderResponse;
import br.inatel.dm112.model.ResponseStatus;


public class DeliverTeste {

	//TODO: Fique à vontade para alterar estes atributos
	//TODO: Para enviar um email através do gmail, é necessário habilitar o SMTP da conta de envio.
	private String sendToAddress = "renan.shaolin.25@gmail.com";
	private String sendFromAddress = "robertorr9@gmail.com";
	private String sendPassAddress = "robertodm112";
	

	public DeliveryStatus startDeliverOrder(String cpf, String orderNumber) throws IOException, DocumentException {
		if (cpf == null || orderNumber == null) {
			return new DeliveryStatus(ResponseStatus.NOBODY_HOME.ordinal(), cpf, orderNumber);
		}

		//instancia os clientes
		OrderClient clientOrder = new OrderClient();
		EmailClient clientEmail = new EmailClient();

		Order order = clientOrder.retrieveOrder(orderNumber); //consulta o pedido pelo número

		if(order != null) {
			order.setDeliveryDate(new Date());
			order.setStatus(Order.STATUS.DELIVERED.ordinal()); //pendente de Entrega
			OrderResponse respOrder = clientOrder.updateOrder(order); //atualiza o status do pedido com

			if(respOrder.getStatus() == 1) { //OK
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
						sendFromAddress, sendPassAddress, sendToAddress , PDFContent);
			
				if(respEmail.getStatus() == 1) {//OK
					return new DeliveryStatus(ResponseStatus.DELIVERED.ordinal(), cpf, orderNumber); //Retorna sucesso
				} else {
					System.out.println("Erro no serviço de email");
				}
				
			} else {
				System.out.println("Erro no serviço de pedido: update");
			}
		} else {
			System.out.println("Erro no serviço de pedido: get");
		}
		
		return new DeliveryStatus(ResponseStatus.NOBODY_HOME.ordinal(), cpf, orderNumber);
	}

	public static void main(String[] args) throws IOException, DocumentException {
		DeliverTeste pt = new DeliverTeste();
		pt.startDeliverOrder("111.111.111-11", "124");
	}

}