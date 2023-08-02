package com.practice.todo.domain;

import com.practice.todo.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String body;
    private boolean isDone;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;
}
