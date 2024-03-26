package com.card_inventory.cards.Advice;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PerformanceHandler implements ObservationHandler<Observation.Context> {

        @Override
        public void onStart(Observation.Context context) {
            log.info("Execution started{}",context.getName());
            context.put("Time", System.currentTimeMillis());

        }

        @Override
        public void onError(Observation.Context context) {
            log.info("Error occured{}",
                    context.getError().getMessage());
        }

        @Override
        public void onEvent(Observation.Event event, Observation.Context context) {
            ObservationHandler.super.onEvent(event, context);
        }

        @Override
        public void onScopeOpened(Observation.Context context) {
            ObservationHandler.super.onScopeOpened(context);
        }

        @Override
        public void onScopeClosed(Observation.Context context) {
            ObservationHandler.super.onScopeClosed(context);
        }

        @Override
        public void onScopeReset(Observation.Context context) {
            ObservationHandler.super.onScopeReset(context);
        }

        @Override
        public void onStop(Observation.Context context) {
            log.info("Execution stopped"
                    +context.getName()
                    +"duration"
                    +(System.currentTimeMillis()-context.getOrDefault("Time",0L)));
        }

        @Override
        public boolean supportsContext(Observation.Context context) {
            return true;
        }
}
