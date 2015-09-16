package treatmentsExceptions;

public class PostException extends EntradaException {

	private String message = "Nao eh possivel criar o post.";
	
	public PostException() {
		super();
	}

	public PostException(String message) {
		super(message);
	}
}
