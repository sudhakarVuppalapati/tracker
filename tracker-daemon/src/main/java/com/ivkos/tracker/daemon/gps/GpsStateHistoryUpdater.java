package com.ivkos.tracker.daemon.gps;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.ivkos.tracker.core.models.gps.GpsState;

public class GpsStateHistoryUpdater
{
   private final GpsStateHistoryHolder holder;
   private final GpsStatePeriodicConsumer consumer;

   @Inject
   public GpsStateHistoryUpdater(GpsStateHistoryHolder holder,
                                 GpsStatePeriodicConsumer consumer,
                                 @Named("daemon.gpsStateHistoryUpdateInterval") int historyInterval)
   {
      this.holder = holder;
      this.consumer = consumer;

      this.consumer.setInterval(historyInterval);
      this.consumer.setAction(this::storeGpsState);
      this.consumer.start();
   }

   public void storeGpsState(GpsState state)
   {
      if (state == null) return;
      if (!state.isFixAvailable()) return;

      holder.add(state);
   }
}
