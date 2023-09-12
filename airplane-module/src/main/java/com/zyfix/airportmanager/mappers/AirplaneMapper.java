package com.zyfix.airportmanager.mappers;

import com.zyfix.airportmanager.api.AirplaneDto;
import com.zyfix.airportmanager.data.model.Airplane;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AirplaneMapper {

    AirplaneDto mapToDto(Airplane airplane);

    Airplane mapToEntity(AirplaneDto airplaneDto);

    List<AirplaneDto> mapToList(List<Airplane> airplaneList);


    default Page<AirplaneDto> mapToAirplaneDtoPage(Page<Airplane> airplanes) {
        return airplanes.map(this::mapToDto);
    }
}

