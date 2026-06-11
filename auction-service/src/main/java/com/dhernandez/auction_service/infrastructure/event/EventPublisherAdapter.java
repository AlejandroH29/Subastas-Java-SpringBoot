package com.dhernandez.auction_service.infrastructure.event;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.dhernandez.auction_service.application.port.out.EventPublisher.EventPublisherPort;
import com.dhernandez.auction_service.domain.event.DomainEvent;

@Component
public class EventPublisherAdapter implements EventPublisherPort{

    private final ApplicationEventPublisher publisher;
    public EventPublisherAdapter(ApplicationEventPublisher publisher){
        this.publisher = publisher;
    }
    @Override
    public void publish(List<DomainEvent> domainEvents) {
        for(DomainEvent event : domainEvents){
            publisher.publishEvent(event);
        }
    }
    
}
