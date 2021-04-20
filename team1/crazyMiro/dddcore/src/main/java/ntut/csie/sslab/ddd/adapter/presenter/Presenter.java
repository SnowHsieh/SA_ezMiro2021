package ntut.csie.sslab.ddd.adapter.presenter;

public interface Presenter<M extends ViewModel> {
    public M buildViewModel();
}
