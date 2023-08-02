package com.practice.todo.domain;

import com.practice.todo.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String userId;
    private String name;
    private String email;
    private String password;
    @Embedded
    private UniversityInfo universityInfo;

    @OneToMany(mappedBy = "member")
    List<Todo> todos = new ArrayList<>();
}
