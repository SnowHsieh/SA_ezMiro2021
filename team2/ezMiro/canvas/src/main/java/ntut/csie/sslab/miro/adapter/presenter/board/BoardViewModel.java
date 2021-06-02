package ntut.csie.sslab.miro.adapter.presenter.board;

import ntut.csie.sslab.ddd.adapter.presenter.ViewModel;
import ntut.csie.sslab.miro.usecase.line.LineDTO;
import ntut.csie.sslab.miro.usecase.note.NoteDTO;
import java.util.List;

public class BoardViewModel implements ViewModel {
    private String boardId;
    private String boardChannel;
    private List<NoteDTO> notes;
    private List<LineDTO> lines;

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getBoardChannel() {
        return boardChannel;
    }

    public void setBoardChannel(String boardChannel) {
        this.boardChannel = boardChannel;
    }

    public List<NoteDTO> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteDTO> notes) {
        this.notes = notes;
    }

    public List<LineDTO> getLines() {
        return lines;
    }

    public void setLines(List<LineDTO> lines) {
        this.lines = lines;
    }
}