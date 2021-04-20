package ntut.csie.sslab.ddd.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public abstract class DomainEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	private final UUID id;
	private final Date occurredOn;

	public DomainEvent(Date occurredOn){
		id = UUID.randomUUID();
		this.occurredOn = occurredOn;
	}

	public Date occurredOn() {
		return occurredOn;
	}

	public String detail() {
		String formatDate = String.format("occurredOn='%1$tY-%1$tm-%1$td %1$tH:%1$tM']", occurredOn());
		String format = String.format(
				"%s[Name='%s'] ",
				this.getClass().getSimpleName());
		return format + formatDate;
	}

	public UUID id() {
		return id;
	}
}
