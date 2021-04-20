package ntut.csie.sslab.kanban.entity.model.workflow;

import ntut.csie.sslab.ddd.model.ValueObject;

public class WipLimit extends ValueObject {

	private int value;
	
	public WipLimit(int value) {
		super();
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}

	public static WipLimit valueOf(int value){
		return new WipLimit(value);
	}
}
