package com.ivkos.tracker.core.models.device;


import lombok.*;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

import static java.lang.Long.parseUnsignedLong;
import static java.time.OffsetDateTime.now;
import static java.util.UUID.nameUUIDFromBytes;

@Entity
@Table(name = "device", indexes = {
      @Index(columnList = "dateCreated"),
      @Index(columnList = "lastSeen")
})
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class Device
{
   public static final Device ANONYMOUS = new Device(nameUUIDFromBytes(new byte[] { 0 }), 0L);

   @Id
   @NonNull
   private UUID id;

   @Column(unique = true, updatable = false)
   @NonNull
   private Long hardwareId;

   @Setter
   private String name;

   @Column(updatable = false)
   private OffsetDateTime dateCreated = now();

   @Setter
   @Nullable
   private OffsetDateTime lastSeen;

   public Device(UUID id, String hardwareId)
   {
      this(id, parseUnsignedLong(hardwareId, 16));
   }

   public Device(UUID id, long hardwareId)
   {
      this.id = id;
      this.hardwareId = hardwareId;
   }
}
