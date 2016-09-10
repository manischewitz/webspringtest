package alert;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;


import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import objects.JacksonJsonConverter;



@Component
public class AlertServiceImp implements AlertService {

	@Autowired
	private ActiveMQConnectionFactory factory;
	
	public static final String QUEUE_NAME = "AppQueue";
	
	
	@Override
	public void sendObjectAlert(Object messageToSend) {
		Connection connection = null;
		Session session = null;
		try{
			connection = this.factory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = new ActiveMQQueue(QUEUE_NAME);
			MessageProducer producer = session.createProducer(destination);
			TextMessage textMessage = session.createTextMessage();
			String json = JacksonJsonConverter.instance().map(messageToSend);
			textMessage.setText(json);
			producer.send(textMessage);
		}catch(JMSException jmse){
		}finally{
			try {
				if (session != null) session.close();
				
				if (connection != null) connection.close();
				
			}catch (JMSException ex) {
		}
	}
}


	@Override
	public String receiveTextMessage() {
		Connection conn = null;
		Session session = null;
		try {
		conn = this.factory.createConnection();
		conn.start();
		session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = new ActiveMQQueue(QUEUE_NAME);
		MessageConsumer consumer = session.createConsumer(destination);
		Message message = consumer.receive();
		TextMessage textMessage = (TextMessage) message;
		String text = textMessage.getText();
		conn.start();
		return text;
		} catch (JMSException e) {
		} finally {
			try {
			if (session != null) session.close();
			
			if (conn != null) conn.close();
			
			} catch (JMSException ex) {}
		}
		return null;
	}


	

	

}
