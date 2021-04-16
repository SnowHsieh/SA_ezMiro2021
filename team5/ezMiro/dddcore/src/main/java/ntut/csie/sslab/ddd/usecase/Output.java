package ntut.csie.sslab.ddd.usecase;

import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

public interface Output {

    String getMessage();

    Output setMessage(String message);

    ExitCode getExitCode();

    Output setExitCode(ExitCode exitCode);

}
