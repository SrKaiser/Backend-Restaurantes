package rabbitmq;

import java.io.IOException;

import com.rabbitmq.client.AMQP;

public class Productor {

    private RabbitMQConfig config;

    public Productor() throws Exception {
        config = new RabbitMQConfig();
    }

    public void publicarNuevaValoracion(String mensaje) throws IOException {
        config.getChannel().basicPublish(config.getExchangeName(), config.getRoutingKey(), new AMQP.BasicProperties.Builder().build(), mensaje.getBytes());
    }
}

