package ntut.csie.sslab.ddd.model.common;

public class PostconditionViolationException extends Error {

    public PostconditionViolationException(){
        super();
    }

    public PostconditionViolationException(String message){
        super(message);
    }

    public PostconditionViolationException(String message, Throwable cause) {
        super(message, cause);
    }

}
