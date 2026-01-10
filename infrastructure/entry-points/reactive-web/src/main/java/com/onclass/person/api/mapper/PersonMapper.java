package com.onclass.person.api.mapper;


import com.onclass.person.api.dto.request.PersonRequestDto;
import com.onclass.person.api.dto.response.PersonResponseDto;
import com.onclass.person.model.person.Person;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PersonMapper {
    Person toModel(PersonRequestDto personRequestDto);
    PersonResponseDto toPersonResponseDto(Person person);
}
