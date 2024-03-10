package ru.astondevs.kafkastartermvn.listener;

import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public class KafkaListenerDefaultStrategy implements Listener<String>{
    private final Listener<String> listener;
    @Override
    public void receive(Collection<String> data) {
        listener.receive(data);
    }
}
