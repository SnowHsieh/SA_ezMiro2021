package ntut.csie.sslab.ddd.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public interface DomainEvent extends Serializable {
	UUID getId();
	Instant getOccurredOn();
}
