package treatmentsExceptions;

public class AtualizaPerfilException extends EntradaException {

	public AtualizaPerfilException(String message) {
		super("Erro na atualizacao de perfil. " + message);
	}
}
