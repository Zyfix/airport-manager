package com.zyfix.airportmanager.mappers;


import com.zyfix.airportmanager.api.StewardDto;
import com.zyfix.airportmanager.data.model.Steward;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;


@Mapper(componentModel = "spring")
public interface StewardMapper {
    StewardDto mapToDto(Steward steward);
    Steward mapToEntity(StewardDto stewardDto);
    List<StewardDto> mapToList(List<Steward> stewardList);

    default Page<StewardDto> mapToStewardDtoPage(Page<Steward> stewards) {
        return stewards.map(this::mapToDto);
    }

}

