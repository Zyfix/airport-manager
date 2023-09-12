package com.zyfix.airportmanager.facade;


import com.zyfix.airportmanager.api.StewardDto;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface StewardFacade {
    Optional<StewardDto> findById(UUID id);

    Optional<List<StewardDto>> findAll();

    Optional<Page<StewardDto>> findAllPagedBy(int offset, int size);

    Optional<List<StewardDto>> findAllSortedBy(String direction, String field);

    Optional<List<StewardDto>> findStewardsByHireDate(LocalDate from, LocalDate to);

    Optional<List<StewardDto>> findStewardsByBirthDate(LocalDate from, LocalDate to);

    Optional<StewardDto> create(StewardDto stewardDto);

    Optional<StewardDto> delete(UUID id);

    void seed();

    void clear();
}
