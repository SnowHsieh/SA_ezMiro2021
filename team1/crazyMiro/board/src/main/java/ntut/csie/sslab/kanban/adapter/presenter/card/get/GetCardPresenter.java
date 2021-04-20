package ntut.csie.sslab.kanban.adapter.presenter.card.get;

import ntut.csie.sslab.ddd.adapter.presenter.Presenter;
import ntut.csie.sslab.ddd.usecase.Result;
import ntut.csie.sslab.kanban.usecase.card.CardDto;
import ntut.csie.sslab.kanban.usecase.card.get.GetCardOutput;

public class GetCardPresenter extends Result implements Presenter< CardViewModel>, GetCardOutput {
    private CardViewModel viewModel;

    public GetCardPresenter(){
        viewModel = new CardViewModel();
    }

    @Override
    public CardViewModel buildViewModel() {
        return viewModel;
    }

    @Override
    public CardDto getCard() {
        return viewModel.getCard();
    }

    @Override
    public void setCard(CardDto cardDto) {
        viewModel.setCard(cardDto);
    }

}
