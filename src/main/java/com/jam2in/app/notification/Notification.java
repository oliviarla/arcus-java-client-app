package com.jam2in.app.notification;

import java.io.Serial;
import java.io.Serializable;

public record Notification(String user, String activity) implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
}
