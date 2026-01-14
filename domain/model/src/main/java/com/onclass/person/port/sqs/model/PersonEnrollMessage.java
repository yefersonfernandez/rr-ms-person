package com.onclass.person.port.sqs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonEnrollMessage {
    private Long bootcampId;
    private Long personId;
}
