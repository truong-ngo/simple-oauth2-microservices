package com.example.springauth.config.key;

import org.springframework.context.ApplicationEvent;

import java.time.Instant;

/**
 * Key generation request event
 * */
public class RsaKeyPairGenerationRequestEvent extends ApplicationEvent {

    public RsaKeyPairGenerationRequestEvent(Instant instant) {
        super(instant);
    }

    @Override
    public Instant getSource() {
        return (Instant) super.getSource();
    }
}
