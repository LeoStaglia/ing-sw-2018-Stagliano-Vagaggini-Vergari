package Eccezioni;

public class FullGameException extends Exception{
    private String message;
    public FullGameException(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
