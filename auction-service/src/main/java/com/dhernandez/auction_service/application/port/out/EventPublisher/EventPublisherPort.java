package com.dhernandez.auction_service.application.port.out.EventPublisher;

import java.util.List;

import com.dhernandez.auction_service.domain.event.DomainEvent;

public interface EventPublisherPort {
    public void publish(List<DomainEvent> domainEvents);
}
