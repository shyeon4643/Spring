package com.practice.todo.domain;

import lombok.*;

import javax.persistence.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UniversityInfo {
    private String univName;
    private String major;
}
