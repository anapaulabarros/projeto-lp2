package treatmentsExceptions;

public class PostException extends EntradaException {

	public PostException(String message) {
		super("Nao eh possivel criar o post. " + message);
	}
}
