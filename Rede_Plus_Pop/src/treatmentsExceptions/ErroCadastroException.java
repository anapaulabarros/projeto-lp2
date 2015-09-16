package treatmentsExceptions;

public class ErroCadastroException extends EntradaException {
	
	private String message = "Erro no cadastro de Usuarios.";
	
	public ErroCadastroException(){
		super();
	}
	
	public ErroCadastroException(String message){
		super(message);
	}

}
