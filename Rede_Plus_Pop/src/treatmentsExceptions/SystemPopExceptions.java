package treatmentsExceptions;

public class SystemPopExceptions extends Exception {

	private String message = null;

	public SystemPopExceptions() {
		super();
	}
	
	public SystemPopExceptions(String message){
		super(message);
        this.message = message;
	}
	
	public SystemPopExceptions(Throwable cause) {
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
