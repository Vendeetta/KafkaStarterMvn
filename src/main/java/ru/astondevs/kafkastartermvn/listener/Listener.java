package ru.astondevs.kafkastartermvn.listener;

import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public interface Listener<T> {

    void receive(Collection<T> data);
}
