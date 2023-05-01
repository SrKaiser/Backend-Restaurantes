package excepciones;

public class RestauranteNotFoundException extends RuntimeException {
    public RestauranteNotFoundException(String message) {
        super(message);
    }
}

