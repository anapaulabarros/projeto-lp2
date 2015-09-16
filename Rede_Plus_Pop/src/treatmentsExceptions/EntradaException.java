package treatmentsExceptions;

public class EntradaException extends Exception{

	private String message = null;

	public EntradaException() {
		super();
	}
	
	public EntradaException(String message){
		super(message);
        this.message = message;
	}
	
	public EntradaException(Throwable cause) {
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
