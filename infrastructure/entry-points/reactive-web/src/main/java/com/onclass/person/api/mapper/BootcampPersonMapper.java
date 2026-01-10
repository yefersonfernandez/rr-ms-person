package com.onclass.person.api.mapper;

import com.onclass.person.api.dto.request.BootcampPersonRequestDto;
import com.onclass.person.api.dto.response.BootcampPersonResponseDto;
import com.onclass.person.model.bootcampperson.BootcampPerson;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BootcampPersonMapper {
    BootcampPerson toModel(BootcampPersonRequestDto bootcampPersonRequestDto);
    BootcampPersonResponseDto toBootcampPersonResponseDto(BootcampPerson bootcampPerson);
}
