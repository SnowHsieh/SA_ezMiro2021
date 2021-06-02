package ntut.csie.sslab.miro.usecase.line.delete;

import ntut.csie.sslab.ddd.usecase.Input;

public interface DeleteLineInput extends Input {

    String getLineId();

    void setLineId(String lineId);
}
