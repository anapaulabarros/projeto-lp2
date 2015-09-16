package treatmentsExceptions;

public class LogicaException extends Exception{

	private String message = null;

	public LogicaException() {
		super();
	}
	
	public LogicaException(String message){
		super(message);
        this.message = message;
	}
	
	public LogicaException(Throwable cause) {
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
