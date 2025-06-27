package tr.unvercanunlu.todoapp.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateTimeUtil {

  private static final ZoneId UTC = ZoneId.of("UTC");

  public LocalDateTime convertDateTimeZone(LocalDateTime dateTimeFrom, ZoneId zoneFrom, ZoneId zoneTo) {
    ZonedDateTime zonedFrom = dateTimeFrom.atZone(zoneFrom);
    ZonedDateTime zonedTo = zonedFrom.withZoneSameInstant(zoneTo);

    return zonedTo.toLocalDateTime();
  }

  public LocalDateTime nowUtc() {
    ZoneId currentZone = ZoneId.systemDefault();
    LocalDateTime now = LocalDateTime.now(currentZone);
    return convertDateTimeZone(now, currentZone, UTC);
  }

}
