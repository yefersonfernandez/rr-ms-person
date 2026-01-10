package com.onclass.person.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("person")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PersonEntity {
    @Id
    @Column("person_id")
    private Long id;
    private String name;
    private String email;
    private Integer age;
}
