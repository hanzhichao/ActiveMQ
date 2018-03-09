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

//		1. 建立ConnectionFactory工厂对象， 需要填入用户名、密码、以及要连接的地址，均时候用默认即可，默认端口为“tcp://localhost:61616"
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"hzc",//ActiveMQConnectionFactory.DEFAULT_USER,
				"hzc", //ActiveMQConnectionFactory.DEFAULT_PASSWORD,
				"tcp://localhost:61616");
		
//		2. 通过ConnectionFactory工厂我们创建一个Connection连接，并且调用Connection的start放放风开启连接，Connection默认是关闭的。
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
//		3. 通过Connection对象创建Session会话（上下文环境对象），用于接收消息，参数配置为1为是否启用多事务， 参数配置2为签收模式，一般我们设置自动签收
		//Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
		
		//使用事务的方式进行消息发送
		//Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		
		//使用Client端签收的方式
		Session session = connection.createSession(Boolean.TRUE, Session.CLIENT_ACKNOWLEDGE);
		
		
		
//		4. 通过Session创建Destination对象， 指的事一个客户端用来指定生产消息目标和消息来源的对象，在PTP模式中，Destination被称做Queue即队列，在Pub/Sub模式中，Destination被称做Topic即主题。在程序中可以使用多个Queue和Topic。
		Destination desitination = session.createQueue("queue1");
		
//		5. 我们需要通过Session对象创建消息的发送和接收对象（生产者混合消费者）MessageProduce/MessageConsumer
		//MessageProducer messageProducer = session.createProducer(desitination);
		MessageProducer messageProducer = session.createProducer(null);
//		6. 我们可以使用MessageProducer的setDeliveryMode方法为其设置持久化特性和非持久化特性（DeliveryMode)
		//messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		
//		7. 最后后我们使用JMS规范的TextMessage形式创建数据（通过Session对象）,并用MessageProducer的send放放风发送数据。同理客户段使用receive方法进行接收数据，最后不要忘记关闭Connection连接）
		for(int i = 0; i< 5; i++) {
			TextMessage textMessage = session.createTextMessage();
			textMessage.setText("我是消息内容, id为: " + i);
			//messageProducer.send(textMessage);
			
			//参数1：目的地；参数2：消息;参数3：消息持久化模式；参数4：优先级0-9，默认4；参数5：有效期
			messageProducer.send(desitination, textMessage, DeliveryMode.NON_PERSISTENT, i, 1000 * 10);;
			System.out.println("生产者： " + textMessage.getText());
		}
		
		// 使用事务提交
		session.commit();
		
		if(connection != null){
			connection.close();
		}
		

	}

}
