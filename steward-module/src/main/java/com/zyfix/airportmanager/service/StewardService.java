package com.zyfix.airportmanager.service;


import com.zyfix.airportmanager.data.model.Steward;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StewardService {
    Optional<Steward> findById(UUID id);

    Optional<List<Steward>> findAll();

    Optional<Page<Steward>> findAllPagedBy(int offset, int size);

    Optional<List<Steward>> findAllSortedBy(String direction, String field);

    Optional<List<Steward>> findStewardsByHireDate(LocalDate from, LocalDate to);

    Optional<List<Steward>> findStewardsByBirthDate(LocalDate from, LocalDate to);

    Optional<Steward> create(Steward steward);

    Optional<Steward> delete(UUID id);

    void seed();

    void clear();
}

