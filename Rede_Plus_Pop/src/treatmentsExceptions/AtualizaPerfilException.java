package treatmentsExceptions;

public class AtualizaPerfilException extends EntradaException {

	private String message = "Erro na atualizacao de perfil.";
	
	public AtualizaPerfilException() {
		super();
	}

	public AtualizaPerfilException(String message) {
		super(message);
	}
}
