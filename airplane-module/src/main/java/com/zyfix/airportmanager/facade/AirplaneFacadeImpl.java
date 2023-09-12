package com.zyfix.airportmanager.facade;


import com.zyfix.airportmanager.api.AirplaneDto;
import com.zyfix.airportmanager.data.model.Airplane;
import com.zyfix.airportmanager.mappers.AirplaneMapper;
import com.zyfix.airportmanager.service.AirplaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AirplaneFacadeImpl implements AirplaneFacade {

    private final AirplaneService airplaneService;
    private final AirplaneMapper airplaneMapper;


    @Autowired
    public AirplaneFacadeImpl(AirplaneService airplaneService, AirplaneMapper airplaneMapper) {
        this.airplaneService = airplaneService;
        this.airplaneMapper = airplaneMapper;
    }

    @Override
    public Optional<AirplaneDto> findById(UUID id) {
        Optional<Airplane> airplane = airplaneService.findById(id);
        return airplane.map(airplaneMapper::mapToDto);
    }

    @Override
    public Optional<List<AirplaneDto>> findAll() {
        Optional<List<Airplane>> airplaneList = airplaneService.findAll();
        return airplaneList.map(airplaneMapper::mapToList);
    }

    @Override
    public Optional<Page<AirplaneDto>> findAllPagedBy(int offset, int size) {
        Optional<Page<Airplane>> airplanePage = airplaneService.findAllPagedBy(offset, size);
        if (airplanePage.isPresent()) {
            var airplaneDtos = airplaneMapper.mapToAirplaneDtoPage(airplanePage.get());
            return Optional.of(airplaneDtos);
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<AirplaneDto>> findAllSortedBy(String direction, String field) {
        Optional<List<Airplane>> airplanesSorted = airplaneService.findAllSortedBy(direction, field);
        return airplanesSorted.map(airplaneMapper::mapToList);
    }

    @Override
    public Optional<List<AirplaneDto>> findAllByManufacturer(String manufacturer) {
        Optional<List<Airplane>> airplanesByManufacturer = airplaneService.findAllByManufacturer(manufacturer);
        return airplanesByManufacturer.map(airplaneMapper::mapToList);
    }

    @Override
    public List<String> findAllManufacturers() {
        return airplaneService.findAllManufacturers();
    }

    @Override
    public Optional<AirplaneDto> create(AirplaneDto airplaneDto) {
        Airplane airplane = airplaneMapper.mapToEntity(airplaneDto);
        Optional<Airplane> airplaneFromDb =  airplaneService.create(airplane);
        return airplaneFromDb.map(airplaneMapper::mapToDto);
    }

    @Override
    public Optional<AirplaneDto> delete(UUID id) {
        Optional<Airplane> airplane = airplaneService.delete(id);
        return airplane.map(airplaneMapper::mapToDto);
    }

    @Override
    public void seed() {
        airplaneService.seed();
    }

    @Override
    public void clear() {
        airplaneService.clear();
    }
}
