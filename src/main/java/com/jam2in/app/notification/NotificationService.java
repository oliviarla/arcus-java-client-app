package com.jam2in.app.notification;

import lombok.RequiredArgsConstructor;
import net.spy.memcached.ArcusClient;
import net.spy.memcached.collection.CollectionAttributes;
import net.spy.memcached.collection.CollectionOverflowAction;
import net.spy.memcached.internal.CollectionFuture;
import net.spy.memcached.ops.CollectionOperationStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class NotificationService {
  private final ArcusClient arcusClient;
  private static final String NOTIFICATIONS_KEY = "notifications";

  public List<Notification> getAll() {
	CollectionFuture<List<Object>> future = null;

	// 조회
	try {
	  future = arcusClient.asyncLopGet(NOTIFICATIONS_KEY, 0, 10, false, false);
	} catch (IllegalStateException e) {
	  throw new RuntimeException("Arcus client failed", e.getCause());
	}

	if (future == null) {
	  return Collections.emptyList();
	}

	try {
	  List<Object> notiList = future.get(1000L, TimeUnit.MILLISECONDS);
	  return notiList.stream().map(o -> (Notification) o).toList();
	} catch (InterruptedException | TimeoutException | ExecutionException e) {
	  future.cancel(true);
	  return Collections.emptyList();
	}
  }

  public boolean setDataToCache() {
	List<Notification> notifications = List.of(
	  new Notification("user1", "liked your photo."),
	  new Notification("user2", "now following you."),
	  new Notification("user3", "now following you."),
	  new Notification("user4", "liked your video."),
	  new Notification("user5", "liked your photo.")
	);

	CollectionFuture<Map<Integer, CollectionOperationStatus>> mapCollectionFuture;
	try {
	  mapCollectionFuture =
		arcusClient.asyncLopPipedInsertBulk(NOTIFICATIONS_KEY, 0, Collections.singletonList(notifications),
		  new CollectionAttributes(0, 10L, CollectionOverflowAction.head_trim));
	} catch (Exception e) {
	  throw new RuntimeException("Arcus client failed.");
	}

	if (mapCollectionFuture == null) {
	  return false;
	}

	try {
	  Map<Integer, CollectionOperationStatus> result =
		mapCollectionFuture.get(1000L, TimeUnit.MILLISECONDS);
	  return result.isEmpty();
	} catch (InterruptedException | TimeoutException | ExecutionException e) {
	  mapCollectionFuture.cancel(true);
	  return false;
	}
  }

  public void updateNotification(Notification notification) {
	CollectionFuture<Boolean> booleanCollectionFuture;
	try {
	  booleanCollectionFuture =
		arcusClient.asyncLopInsert(NOTIFICATIONS_KEY, 0, notification, null);
	} catch (Exception e) {
	  throw new RuntimeException("Arcus client failed.");
	}

	if (booleanCollectionFuture == null) {
	  throw new RuntimeException("Future is null");
	}

	try {
	  boolean result = booleanCollectionFuture.get(1000L, TimeUnit.MILLISECONDS);
	  if (!result) {
		throw new RuntimeException("Failed to update notification");
	  }
	} catch (InterruptedException | TimeoutException | ExecutionException e) {
	  booleanCollectionFuture.cancel(true);
	  throw new RuntimeException("Future is cancelled");
	}
  }
}
