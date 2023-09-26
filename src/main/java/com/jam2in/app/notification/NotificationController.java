package com.jam2in.app.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notis")
@RequiredArgsConstructor
public class NotificationController {

  private final NotificationService notificationService;

  @GetMapping("/all")
  public List<Notification> getAll() {
	return notificationService.getAll();
  }

  @PostMapping("/set")
  public boolean setDataToCache() {
	return notificationService.setDataToCache();
  }

  @PostMapping("/update")
  public void updateNotification(@RequestBody Notification notification) {
	notificationService.updateNotification(notification);
  }
}
