package ntut.csie.sslab.ddd.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.UUID;

public class RemoteDomainEvent implements DomainEvent {
	private final UUID id;
	private final Instant occurredOn;
	private final String jsonEvent;
	private final String eventType;
	private final String tag;

	public RemoteDomainEvent(DomainEvent event, String tag, Instant occurredOn)  {
		this(event, UUID.randomUUID(), tag, occurredOn);
	}

	public RemoteDomainEvent(DomainEvent event, UUID id, String tag, Instant occurredOn){
		this.id = id;
		this.occurredOn = occurredOn;
		this.eventType = event.getClass().getSimpleName();
		this.jsonEvent = eventToString(event);
		this.tag = tag;
	}

	private String eventToString(DomainEvent event) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(event);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("eventToString fail.");
		}
	}

	public String getJsonEvent(){
		return jsonEvent;
	}

	public String getEventType(){
		return eventType;
	}

	public String getTag() {
		return tag;
	}

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public Instant getOccurredOn() {
		return occurredOn;
	}
}