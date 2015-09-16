package treatmentsExceptions;

public class PostException extends Exception {

	private String message = null;

	public PostException() {
		super();
	}
	
	public PostException(String message){
		super(message);
        this.message = message;
	}
	
	public PostException(Throwable cause) {
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
