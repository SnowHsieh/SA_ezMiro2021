package ntut.csie.sslab.kanban.adapter.presenter.card.get;

import ntut.csie.sslab.ddd.adapter.presenter.ViewModel;
import ntut.csie.sslab.kanban.usecase.card.CardDto;

import java.util.List;

//To be deleted
public class GetCardsByLaneIdViewModel implements ViewModel {

    private List<CardDto> generalCardList;
    private List<CardDto> expediteCardList;


    public List<CardDto> getGeneralCardList() {
        return generalCardList;
    }

    public void setGeneralCardList(List<CardDto> generalCardList) {
        this.generalCardList = generalCardList;
    }

    public List<CardDto> getExpediteCardList() {
        return expediteCardList;
    }

    public void setExpediteCardList(List<CardDto> expediteCardList) {
        this.expediteCardList = expediteCardList;
    }
}
