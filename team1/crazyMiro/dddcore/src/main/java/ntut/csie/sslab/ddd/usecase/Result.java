package ntut.csie.sslab.ddd.usecase;

import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

public class Result implements Output {

    private String message;
    private ExitCode exitCode;

    public String getMessage() {
        return message;
    }

    public Output setMessage(String message) {
        this.message = message;
        return this;
    }

    public ExitCode getExitCode() {
        return exitCode;
    }

    public Output setExitCode(ExitCode exitCode) {
        this.exitCode = exitCode;
        return this;
    }
}
