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
//		1. 建立ConnectionFactory工厂对象， 需要填入用户名、密码、以及要连接的地址，均时候用默认即可，默认端口为“tcp://localhost:61616"
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"hzc", //ActiveMQConnectionFactory.DEFAULT_USER,
				"hzc", //ActiveMQConnectionFactory.DEFAULT_PASSWORD,
				"tcp://localhost:61616");
		
//		2. 通过ConnectionFactory工厂我们创建一个Connection连接，并且调用Connection的start放放风开启连接，Connection默认是关闭的。
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
//		3. 通过Connection对象创建Session会话（上下文环境对象），用于接收消息，参数配置为1为是否启用多事务， 参数配置2为签收模式，一般我们设置自动签收
		//Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
		Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);
		
//		4. 通过Session创建Destination对象， 指的事一个客户端用来指定生产消息目标和消息来源的对象，在PTP模式中，Destination被称做Queue即队列，在Pub/Sub模式中，Destination被称做Topic即主题。在程序中可以使用多个Queue和Topic。
		Destination desitination = session.createQueue("queue1");
		
//		5. 我们需要通过Session对象创建消息的发送和接收对象（生产者混合消费者）MessageProduce/MessageConsumer
		MessageConsumer messageConsumer = session.createConsumer(desitination);
		
		while(true){
			TextMessage msg = (TextMessage)messageConsumer.receive();
			//手工签收消息，另起一个线程去通知MQ已收到消息
			msg.acknowledge();
			if(msg == null) break;
			System.out.println("收到的内容： " + msg.getText());
		}	
		
		//if(connection != null){
		//	connection.close();
		//}


	}

}
