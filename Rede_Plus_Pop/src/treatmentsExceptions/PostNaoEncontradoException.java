package treatmentsExceptions;

public class PostNaoEncontradoException extends LogicaException {

	private static String message = "Post nao encontrado no sistema.";

	public PostNaoEncontradoException() {
		super(message);
	}
}
