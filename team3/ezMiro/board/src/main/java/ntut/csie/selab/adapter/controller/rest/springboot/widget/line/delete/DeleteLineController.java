package ntut.csie.selab.adapter.controller.rest.springboot.widget.line.delete;

import ntut.csie.selab.usecase.widget.line.delete.DeleteLineInput;
import ntut.csie.selab.usecase.widget.line.delete.DeleteLineOutput;
import ntut.csie.selab.usecase.widget.line.delete.DeleteLineUseCase;
import ntut.csie.selab.usecase.widget.line.link.LinkLineUseCase;
import ntut.csie.selab.usecase.widget.stickynote.delete.DeleteStickyNoteInput;
import ntut.csie.selab.usecase.widget.stickynote.delete.DeleteStickyNoteOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "${CORS_URLS}")
public class DeleteLineController {
    private DeleteLineUseCase deleteLineUseCase;

    @Autowired
    public DeleteLineController(DeleteLineUseCase deleteLineUseCase) {
        this.deleteLineUseCase = deleteLineUseCase;
    }

    @DeleteMapping(path = "/${EZ_MIRO_PREFIX}/boards/{boardId}/widgets/lines/{lineId}")
    public String deleteLineById(@PathVariable("lineId") String lineId) {
        DeleteLineInput input = new DeleteLineInput();
        DeleteLineOutput output = new DeleteLineOutput();
        input.setLineId(lineId);

        deleteLineUseCase.execute(input, output);
        return output.getLineId();
    }
}
