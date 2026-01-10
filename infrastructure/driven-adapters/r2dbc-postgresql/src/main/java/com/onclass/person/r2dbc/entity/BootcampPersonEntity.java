package com.onclass.person.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("bootcamp_person")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BootcampPersonEntity {
    @Id
    @Column("bootcamp_person_id")
    private Long id;
    @Column("bootcamp_id")
    private Long bootcampId;
    @Column("person_id")
    private Long personId;
}
