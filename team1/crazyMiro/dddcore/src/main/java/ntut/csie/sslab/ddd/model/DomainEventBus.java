package ntut.csie.sslab.ddd.model;

public interface DomainEventBus {
    void post(DomainEvent domainEvent);
    void postAll(AggregateRoot aggregateRoot);
    void register(Object object);
    void unregister(Object object);
}
