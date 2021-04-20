package ntut.csie.sslab.ddd.adapter.presenter.cqrs;

import ntut.csie.sslab.ddd.adapter.presenter.ViewModel;

public class CqrsCommandViewModel implements ViewModel {

    private String id;
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
