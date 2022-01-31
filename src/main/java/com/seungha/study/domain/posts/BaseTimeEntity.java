package com.seungha.study.domain.posts;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // Entity클래스들이 이 클래스를 상속할 경우 필드(createdDate,modifiedDate)들도 컬럼으로 인식
@EntityListeners(AuditingEntityListener.class) // 이 클래스에 Auditing 기능을 포함
public abstract class BaseTimeEntity {

    @CreatedDate // 생성시간 저장
    private LocalDateTime createdDate;

    @LastModifiedDate // 수정시간 저장
    private LocalDateTime modifiedDate;
}
