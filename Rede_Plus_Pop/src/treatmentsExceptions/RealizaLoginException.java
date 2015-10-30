package treatmentsExceptions;

public class RealizaLoginException extends EntradaException{

	public RealizaLoginException(String message) {
		super("Nao foi possivel realizar login. " + message);
	}
}
