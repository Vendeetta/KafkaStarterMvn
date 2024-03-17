package ru.astondevs.kafkastartermvn.listener;

import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public class KafkaListenerDefaultStrategy<T> implements Listener<T>{
    private final Listener<T> listener;
    @Override
    public void receive(Collection<T> data) {
        listener.receive(data);
    }
}
