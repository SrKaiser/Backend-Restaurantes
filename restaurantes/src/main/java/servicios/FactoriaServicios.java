package servicios;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import utils.Configuracion;

public class FactoriaServicios {
	
	private static Map<Class<?>, Object> servicios = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	public static <T> T getServicio(Class<T> servicio) {
				
			
			try {
				if (servicios.containsKey(servicio)) {
					return (T) servicios.get(servicio);
				}
				else {
					Properties properties = Configuracion.cargarConfiguracion();		
					String clase = properties.getProperty(servicio.getName());
					return (T) Class.forName(clase).getConstructor().newInstance();
				}
				
			}
			catch (Exception e) {
				
				throw new RuntimeException("No se ha podido obtener la implementación del servicio: " + servicio.getName());
			}
			
	}
	
}
