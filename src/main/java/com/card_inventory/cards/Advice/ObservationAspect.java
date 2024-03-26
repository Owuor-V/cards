package com.card_inventory.cards.Advice;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObservationAspect {
    @Bean
    public ObservedAspect observedAspect(ObservationRegistry observationRegistry){
        observationRegistry.observationConfig().observationHandler(new PerformanceHandler());
        return new ObservedAspect(observationRegistry);
    }
}
