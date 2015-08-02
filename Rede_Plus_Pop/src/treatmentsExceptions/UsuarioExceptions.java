package treatmentsExceptions;

public class UsuarioExceptions extends Exception {

	private String message = null;

	public UsuarioExceptions() {
		super();
	}
	
	public UsuarioExceptions(String message){
		super(message);
        this.message = message;
	}
	
	public UsuarioExceptions(Throwable cause) {
        super(cause);
    }
 
	@Override
    public String toString() {
        return message;
    }
 
    @Override
    public String getMessage() {
        return message;
    }
}
