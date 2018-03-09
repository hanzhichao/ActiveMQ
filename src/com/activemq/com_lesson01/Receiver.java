package com.activemq.com_lesson01;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Receiver {

	public static void main(String[] args) throws JMSException {
//		1. ����ConnectionFactory�������� ��Ҫ�����û��������롢�Լ�Ҫ���ӵĵ�ַ����ʱ����Ĭ�ϼ��ɣ�Ĭ�϶˿�Ϊ��tcp://localhost:61616"
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"hzc", //ActiveMQConnectionFactory.DEFAULT_USER,
				"hzc", //ActiveMQConnectionFactory.DEFAULT_PASSWORD,
				"tcp://localhost:61616");
		
//		2. ͨ��ConnectionFactory�������Ǵ���һ��Connection���ӣ����ҵ���Connection��start�ŷŷ翪�����ӣ�ConnectionĬ���ǹرյġ�
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
//		3. ͨ��Connection���󴴽�Session�Ự�������Ļ������󣩣����ڽ�����Ϣ����������Ϊ1Ϊ�Ƿ����ö����� ��������2Ϊǩ��ģʽ��һ�����������Զ�ǩ��
		//Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
		Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);
		
//		4. ͨ��Session����Destination���� ָ����һ���ͻ�������ָ��������ϢĿ�����Ϣ��Դ�Ķ�����PTPģʽ�У�Destination������Queue�����У���Pub/Subģʽ�У�Destination������Topic�����⡣�ڳ����п���ʹ�ö��Queue��Topic��
		Destination desitination = session.createQueue("queue1");
		
//		5. ������Ҫͨ��Session���󴴽���Ϣ�ķ��ͺͽ��ն��������߻�������ߣ�MessageProduce/MessageConsumer
		MessageConsumer messageConsumer = session.createConsumer(desitination);
		
		while(true){
			TextMessage msg = (TextMessage)messageConsumer.receive();
			//�ֹ�ǩ����Ϣ������һ���߳�ȥ֪ͨMQ���յ���Ϣ
			msg.acknowledge();
			if(msg == null) break;
			System.out.println("�յ������ݣ� " + msg.getText());
		}	
		
		//if(connection != null){
		//	connection.close();
		//}


	}

}
