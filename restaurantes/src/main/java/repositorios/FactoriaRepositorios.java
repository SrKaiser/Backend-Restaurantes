package repositorios;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import utils.Configuracion;

/*
 * Factoría que encapsula la implementación del repositorio.
 * 
 * Utiliza un fichero de propiedades para cargar la implementación.
 */

public class FactoriaRepositorios {
	
	private static Map<Class<?>, Object> repositorios = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	public static <T, K, R extends IRepositorioRestaurante> R getRepositorio(Class<?> entidad) {
							
			try {
				if (repositorios.containsKey(entidad)) {
					return (R) repositorios.get(entidad);
				}
				else {
					Properties properties = Configuracion.cargarConfiguracion();
					String clase = properties.getProperty(entidad.getName());
					R repositorio = (R) Class.forName(clase).getConstructor().newInstance();
					repositorios.put(entidad, repositorio);
					return repositorio;
				}
			}
			catch (Exception e) {
				
				throw new RuntimeException("No se ha podido obtener el repositorio para la entidad: " + entidad.getName());
			}
			
	}
	
}
