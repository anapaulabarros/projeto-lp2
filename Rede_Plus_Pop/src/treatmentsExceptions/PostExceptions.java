package treatmentsExceptions;

public class PostExceptions extends Exception {

	private String message = null;

	public PostExceptions() {
		super();
	}
	
	public PostExceptions(String message){
		super(message);
        this.message = message;
	}
	
	public PostExceptions(Throwable cause) {
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
