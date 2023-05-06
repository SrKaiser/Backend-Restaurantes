package rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQConfig {
    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;
    private String exchangeName = "arso-exchange";
    private String routingKey = "arso";

    public RabbitMQConfig() throws Exception {
        factory = new ConnectionFactory();
        factory.setUri("uri");
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName, "direct", true);
    }

    public Channel getChannel() {
        return channel;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public String getRoutingKey() {
        return routingKey;
    }
}
