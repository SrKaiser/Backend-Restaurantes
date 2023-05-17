package servicios;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.json.JsonObject;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import excepciones.EntidadNoEncontrada;
import excepciones.RepositorioException;
import modelos.Plato;
import modelos.Restaurante;
import modelos.ResumenRestaurante;
import modelos.SitioTuristico;
import repositorios.FactoriaRepositorios;
import repositorios.IRepositorioRestaurante;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ServicioRestaurante implements IServicioRestaurante {
	
	private IRepositorioRestaurante repositorioRestaurante;

    public ServicioRestaurante() {
    	this.repositorioRestaurante  = FactoriaRepositorios.getRepositorio(Restaurante.class);
    	
    	// registro del consumidor de eventos
		
    			try {
    			ConnectionFactory factory = new ConnectionFactory();
    			
    			// TODO uri
    			factory.setUri("uri");
    			
    		    Connection connection = factory.newConnection();

    		    Channel channel = connection.createChannel();
    		    
    		    /** Declaración de la cola y enlace con el exchange **/

    			final String exchangeName = "evento.nueva.valoracion";
    			final String queueName = "valoracion-queue";
    			final String bindingKey = "";

    			boolean durable = true;
    			boolean exclusive = false;
    			boolean autodelete = false;
    			Map<String, Object> properties = null; // sin propiedades
    			channel.queueDeclare(queueName, durable, exclusive, autodelete, properties);

    			channel.queueBind(queueName, exchangeName, bindingKey);
    		    
    			/** Configuración del consumidor **/
    			
    			boolean autoAck = false;
    			
    			String etiquetaConsumidor = "servicio-agendas";
    			
    			// Consumidor push
    			
    			channel.basicConsume(queueName, autoAck, etiquetaConsumidor, 
    			  
    			  new DefaultConsumer(channel) {
    			    @Override
    			    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
    			            byte[] body) throws IOException {
    			        
    			        
    			        long deliveryTag = envelope.getDeliveryTag();

    			        String contenido = new String(body);
    			        
    					ObjectMapper mapper = new ObjectMapper(); // Jackson
    					
//    					JsonObject evento = mapper.readValue(contenido, EventoReservaCreada.class);
//    				    
//    				    // Procesamos el evento
//    				    Cita cita = new Cita();
//    				    cita.setTitulo(evento.getTitulo());
//    				    cita.setDescripcion(evento.getDescripcion());
//    				    
//    				    for (String usuario : evento.getUsuarios()) {
//    				    	citas.putIfAbsent(usuario, new LinkedList<>());
//    				    	citas.get(usuario).add(cita);			    	
//    				    }
    			        
    			        // Confirma el procesamiento
    			        channel.basicAck(deliveryTag, false);
    			    }
    			});
    			} catch (Exception e) {
    				throw new RuntimeException(e);
    			}
    	
    }
    
    @Override
    public String altaRestaurante(String nombre, double latitud, double longitud) throws RepositorioException {
    	if (nombre == null || nombre.trim().isEmpty()) {
			throw new RepositorioException("El nombre no puede ser null o vacío");
		}
		if (latitud < -90 || latitud > 90) {
			throw new RepositorioException("La latitud debe estar entre -90 y 90");
		}
		if (longitud < -180 || longitud > 180) {
			throw new RepositorioException("La longitud debe estar entre -180 y 180");
		}
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String gestorId = authentication.getName();	
		return repositorioRestaurante.create(nombre, latitud, longitud, gestorId);
	
        
    }
    
    @Override
    public boolean actualizarRestaurante(String idRestaurante, String nombre, double latitud, double longitud) throws RepositorioException, EntidadNoEncontrada {
    	if (nombre == null || nombre.trim().isEmpty()) {
			throw new RepositorioException("El nombre no puede ser null o vacío");
		}
		if (latitud < -90 || latitud > 90) {
			throw new RepositorioException("La latitud debe estar entre -90 y 90");
		}
		if (longitud < -180 || longitud > 180) {
			throw new RepositorioException("La longitud debe estar entre -180 y 180");
		}
    	return repositorioRestaurante.update(idRestaurante, nombre, latitud, longitud);
    }
    
    @Override
	public List<SitioTuristico> obtenerSitiosTuristicosProximos(String idRestaurante) throws RepositorioException, EntidadNoEncontrada {
		return repositorioRestaurante.findSitiosTuristicosProximos(idRestaurante);
	}
    
    @Override
    public boolean establecerSitiosTuristicosDestacados(String idRestaurante, List<SitioTuristico> sitiosTuristicos) throws RepositorioException, EntidadNoEncontrada {
    	if (sitiosTuristicos == null || sitiosTuristicos.isEmpty()) {
	        throw new RepositorioException("La lista de sitios turísticos no puede ser nula o vacía");
	    }
	    for (SitioTuristico sitio : sitiosTuristicos) {
	        if (sitio.getTitulo() == null || sitio.getTitulo().trim().isEmpty()) {
	            throw new RepositorioException("El título del sitio turístico no puede ser null o vacío");
	        }
	        if (sitio.getResumen() == null || sitio.getResumen().trim().isEmpty()) {
	            throw new RepositorioException("El resumen del sitio turístico no puede ser null o vacío");
	        }
	    }
	    
    	return repositorioRestaurante.setSitiosTuristicosDestacados(idRestaurante, sitiosTuristicos);
    }
    
    @Override
    public boolean añadirPlato(String idRestaurante, Plato plato) throws RepositorioException, EntidadNoEncontrada {
    	if (plato.getNombre() == null || plato.getNombre().trim().isEmpty()) {
	        throw new RepositorioException("El nombre del plato no puede ser null o vacío");
	    }
	    if (plato.getDescripcion() == null || plato.getDescripcion().trim().isEmpty()) {
	        throw new RepositorioException("La descripción del plato no puede ser null o vacía");
	    }
	    if (plato.getPrecio() <= 0) {
	        throw new RepositorioException("El precio del plato debe ser mayor que 0");
	    }
	   
       return repositorioRestaurante.addPlato(idRestaurante, plato);
    }
    
    @Override
    public boolean borrarPlato(String idRestaurante, String nombrePlato) throws RepositorioException, EntidadNoEncontrada {
    	if (nombrePlato == null || nombrePlato.trim().isEmpty()) {
			throw new RepositorioException("El nombre del plato no puede ser null o vacío");
		}
    
        return repositorioRestaurante.removePlato(idRestaurante, nombrePlato);
    }
    
    @Override
    public boolean actualizarPlato(String idRestaurante, Plato plato) throws RepositorioException, EntidadNoEncontrada {
    	if (plato.getNombre() == null || plato.getNombre().trim().isEmpty()) {
	        throw new RepositorioException("El nombre del plato no puede ser null o vacío");
	    }
	    if (plato.getDescripcion() == null || plato.getDescripcion().trim().isEmpty()) {
	        throw new RepositorioException("La descripción del plato no puede ser null o vacía");
	    }
	    if (plato.getPrecio() <= 0) {
	        throw new RepositorioException("El precio del plato debe ser mayor que 0");
	    }

        return repositorioRestaurante.updatePlato(idRestaurante, plato);   
    }
    
    @Override
    public Restaurante recuperarRestaurante(String idRestaurante) throws RepositorioException, EntidadNoEncontrada {
        return repositorioRestaurante.findById(idRestaurante);
    }
    
    @Override
    public boolean borrarRestaurante(String idRestaurante) throws RepositorioException, EntidadNoEncontrada {
        return repositorioRestaurante.delete(idRestaurante);
    }
    
    @Override
    public List<ResumenRestaurante> recuperarTodosRestaurantes() throws RepositorioException {
        return repositorioRestaurante.findAll();
    }
}
