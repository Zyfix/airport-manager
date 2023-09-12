package com.zyfix.airportmanager.service;


import com.zyfix.airportmanager.data.model.Airplane;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AirplaneService {
    Optional<Airplane> findById(UUID id);

    Optional<List<Airplane>> findAll();

    Optional<Page<Airplane>> findAllPagedBy(int offset, int size);

    Optional<List<Airplane>> findAllSortedBy(String direction, String field);

    Optional<List<Airplane>> findAllByManufacturer(String manufacturer);

    Optional<Airplane> create(Airplane airplane);

    Optional<Airplane> delete(UUID id);

    List<String> findAllManufacturers();

    void seed();

    void clear();

}
