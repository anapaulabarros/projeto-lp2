package treatmentsExceptions;

public class PostNaoEncontradoException extends LogicaException {


	public PostNaoEncontradoException(String message) {
		super("Post nao encontrado no sistema." + message);
	}
}
