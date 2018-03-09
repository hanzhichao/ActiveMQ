package com.activemq.com_lesson01;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Sender {

	public static void main(String[] args) throws JMSException {

//		1. ����ConnectionFactory�������� ��Ҫ�����û��������롢�Լ�Ҫ���ӵĵ�ַ����ʱ����Ĭ�ϼ��ɣ�Ĭ�϶˿�Ϊ��tcp://localhost:61616"
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"hzc",//ActiveMQConnectionFactory.DEFAULT_USER,
				"hzc", //ActiveMQConnectionFactory.DEFAULT_PASSWORD,
				"tcp://localhost:61616");
		
//		2. ͨ��ConnectionFactory�������Ǵ���һ��Connection���ӣ����ҵ���Connection��start�ŷŷ翪�����ӣ�ConnectionĬ���ǹرյġ�
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
//		3. ͨ��Connection���󴴽�Session�Ự�������Ļ������󣩣����ڽ�����Ϣ����������Ϊ1Ϊ�Ƿ����ö����� ��������2Ϊǩ��ģʽ��һ�����������Զ�ǩ��
		//Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
		
		//ʹ������ķ�ʽ������Ϣ����
		//Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		
		//ʹ��Client��ǩ�յķ�ʽ
		Session session = connection.createSession(Boolean.TRUE, Session.CLIENT_ACKNOWLEDGE);
		
		
		
//		4. ͨ��Session����Destination���� ָ����һ���ͻ�������ָ��������ϢĿ�����Ϣ��Դ�Ķ�����PTPģʽ�У�Destination������Queue�����У���Pub/Subģʽ�У�Destination������Topic�����⡣�ڳ����п���ʹ�ö��Queue��Topic��
		Destination desitination = session.createQueue("queue1");
		
//		5. ������Ҫͨ��Session���󴴽���Ϣ�ķ��ͺͽ��ն��������߻�������ߣ�MessageProduce/MessageConsumer
		//MessageProducer messageProducer = session.createProducer(desitination);
		MessageProducer messageProducer = session.createProducer(null);
//		6. ���ǿ���ʹ��MessageProducer��setDeliveryMode����Ϊ�����ó־û����Ժͷǳ־û����ԣ�DeliveryMode)
		//messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		
//		7. ��������ʹ��JMS�淶��TextMessage��ʽ�������ݣ�ͨ��Session����,����MessageProducer��send�ŷŷ緢�����ݡ�ͬ��ͻ���ʹ��receive�������н������ݣ����Ҫ���ǹر�Connection���ӣ�
		for(int i = 0; i< 5; i++) {
			TextMessage textMessage = session.createTextMessage();
			textMessage.setText("������Ϣ����, idΪ: " + i);
			//messageProducer.send(textMessage);
			
			//����1��Ŀ�ĵأ�����2����Ϣ;����3����Ϣ�־û�ģʽ������4�����ȼ�0-9��Ĭ��4������5����Ч��
			messageProducer.send(desitination, textMessage, DeliveryMode.NON_PERSISTENT, i, 1000 * 10);;
			System.out.println("�����ߣ� " + textMessage.getText());
		}
		
		// ʹ�������ύ
		session.commit();
		
		if(connection != null){
			connection.close();
		}
		

	}

}
