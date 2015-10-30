package treatmentsExceptions;

public class ErroCadastroException extends EntradaException {

	public ErroCadastroException(String message){
		super("Erro no cadastro de Usuarios. " + message);
	}

}
