package ntut.csie.sslab.ddd.usecase.cqrs;

public enum ExitCode {

    SUCCESS("Success", 0),
    FAILURE("Failure", 1);


    private final String string;
    private final int code;

    ExitCode(String string, int code)
    {
        this.string = string;
        this.code = code;
    }

    public String getSuccess(){
        return this.string;
    }

    public int getCode(){
        return this.code;
    }

}
