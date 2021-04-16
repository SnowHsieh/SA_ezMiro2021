package ntut.csie.sslab.ddd.adapter.presenter.cqrs;

import ntut.csie.sslab.ddd.adapter.presenter.Presenter;
import ntut.csie.sslab.ddd.usecase.Output;
import ntut.csie.sslab.ddd.usecase.Result;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

public class CqrsCommandPresenter extends Result implements Presenter<CqrsCommandViewModel>, CqrsCommandOutput {

    private CqrsCommandViewModel viewModel;

    private CqrsCommandPresenter(){
        super();
        viewModel = new CqrsCommandViewModel();
    }

    public static CqrsCommandPresenter newInstance(){
        return new CqrsCommandPresenter();
    }


    @Override
    public CqrsCommandViewModel buildViewModel() {
        return viewModel;
    }

    @Override
    public String getId() {
        return viewModel.getId();
    }

    @Override
    public CqrsCommandOutput setId(String id) {
        viewModel.setId(id);
        return this;
    }

    @Override
    public Output setMessage(String message) {
        super.setMessage(message);
        viewModel.setMessage(message);
        return this;
    }
}
