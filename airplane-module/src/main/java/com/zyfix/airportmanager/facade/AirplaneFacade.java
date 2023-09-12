package com.zyfix.airportmanager.facade;

import com.zyfix.airportmanager.api.AirplaneDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AirplaneFacade {

    Optional<List<AirplaneDto>> findAll();

    Optional<Page<AirplaneDto>> findAllPagedBy(int offset, int size);

    Optional<List<AirplaneDto>> findAllSortedBy(String direction, String field);

    Optional<List<AirplaneDto>> findAllByManufacturer(String manufacturer);

    Optional<AirplaneDto> findById(UUID id);

    Optional<AirplaneDto> create(AirplaneDto airplaneDto);

    Optional<AirplaneDto> delete(UUID id);

    List<String> findAllManufacturers();

    void seed();

    void clear();

}
