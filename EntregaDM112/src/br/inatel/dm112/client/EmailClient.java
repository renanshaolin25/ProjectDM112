package br.inatel.dm112.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import br.inatel.dm112.client.mail.stub.MailImpl;
import br.inatel.dm112.client.mail.stub.MailService;
import br.inatel.dm112.client.mail.stub.MailStatusResponse;

public class EmailClient {

	//TODO: modificar este email para enviar para outro endereço
	private static String sendTo = "renan.shaolin.25@gmail.com";
	
	public MailStatusResponse callSendMailService(String from, String password, String to, byte[] content) {

//		local:  http://localhost:8080/UtilityDM112/soap/mailservices?wsdl

		MailService service = new MailService();
		MailImpl port = service.getMailImplPort();
		MailStatusResponse result = port.sendMail(from, password, to, content);
		return result;
	}

	public static void main(String[] args) throws DocumentException {
		try {
			// le o conteúdo do arquivo pdf de teste
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

			EmailClient client = new EmailClient();
			
			MailStatusResponse result = client.callSendMailService(
					"robertorr9@gmail.com", "robertodm112", sendTo, PDFContent);
			
			System.out.println("Resposta do email: " + result.getStatus());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
