package ntut.csie.selab.adapter.controller.rest.springboot.widget.delete;

import ntut.csie.selab.usecase.widget.delete.DeleteStickyNoteInput;
import ntut.csie.selab.usecase.widget.delete.DeleteStickyNoteOutput;
import ntut.csie.selab.usecase.widget.delete.DeleteStickyNoteUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "${CORS_URLS}")
public class DeleteStickyNoteController {
    private DeleteStickyNoteUseCase deleteStickyNoteUseCase;

    @Autowired
    public DeleteStickyNoteController(DeleteStickyNoteUseCase deleteStickyNoteUseCase) {
        this.deleteStickyNoteUseCase = deleteStickyNoteUseCase;
    }

    @DeleteMapping(path = "/${EZ_MIRO_PREFIX}/boards/{boardId}/widgets/sticky-notes/{stickyNoteId}")
    public String deleteStickyNoteById(@PathVariable("stickyNoteId") String stickyNoteId) {
        DeleteStickyNoteInput input = new DeleteStickyNoteInput();
        DeleteStickyNoteOutput output = new DeleteStickyNoteOutput();
        input.setStickyNoteId(stickyNoteId);

        deleteStickyNoteUseCase.execute(input, output);
        return "ok";
    }
}
