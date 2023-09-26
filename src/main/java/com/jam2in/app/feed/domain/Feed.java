package com.jam2in.app.feed.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Feed implements Serializable {
  @Serial
  private static final long serialVersionUID = 12235423L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private Long userId;
  private String content;
  @CreatedDate
  private LocalDateTime createdAt;
  @LastModifiedDate
  private LocalDateTime modifiedAt;

  public Feed(Long userId, String content) {
    this.userId = userId;
    this.content = content;
  }
}
