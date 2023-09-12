package com.zyfix.airportmanager.facade;



import com.zyfix.airportmanager.api.StewardDto;
import com.zyfix.airportmanager.data.model.Steward;
import com.zyfix.airportmanager.mappers.StewardMapper;

import com.zyfix.airportmanager.service.StewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class StewardFacadeImpl implements StewardFacade {
    private final StewardService stewardService;
    private final StewardMapper stewardMapper;

    @Autowired
    public StewardFacadeImpl(StewardService stewardService, StewardMapper stewardMapper) {
        this.stewardService = stewardService;
        this.stewardMapper = stewardMapper;
    }

    @Override
    public Optional<StewardDto> findById(UUID id) {
        Optional<Steward> steward = stewardService.findById(id);
        return steward.map(stewardMapper::mapToDto);
    }

    @Override
    public Optional<List<StewardDto>> findAll() {
        Optional<List<Steward>> stewardList = stewardService.findAll();
        return stewardList.map(stewardMapper::mapToList);
    }

    public Optional<Page<StewardDto>> findAllPagedBy(int offset, int size) {
        Optional<Page<Steward>> page = stewardService.findAllPagedBy(offset, size);
        if (page.isPresent()) {
            var stewardDtos = stewardMapper.mapToStewardDtoPage(page.get());
            return Optional.of(stewardDtos);
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<StewardDto>> findAllSortedBy(String direction, String field) {
        var sortedStewards = stewardService.findAllSortedBy(direction, field);
        return sortedStewards.map(stewardMapper::mapToList);
    }

    @Override
    public Optional<List<StewardDto>> findStewardsByHireDate(LocalDate from, LocalDate to) {
        var stewards = stewardService.findStewardsByHireDate(from, to);
        return stewards.map(stewardMapper::mapToList);
    }

    @Override
    public Optional<List<StewardDto>> findStewardsByBirthDate(LocalDate from, LocalDate to) {
        var stewards = stewardService.findStewardsByBirthDate(from, to);
        return stewards.map(stewardMapper::mapToList);
    }

    @Override
    public Optional<StewardDto> create(StewardDto stewardDto) {
        Steward steward = stewardMapper.mapToEntity(stewardDto);
        Optional<Steward> stewardFromDb = stewardService.create(steward);
        return stewardFromDb.map(stewardMapper::mapToDto);
    }

    @Override
    public Optional<StewardDto> delete(UUID id) {
        Optional<Steward> steward = stewardService.delete(id);
        return steward.map(stewardMapper::mapToDto);
    }

    @Override
    public void seed() {
        stewardService.seed();
    }

    @Override
    public void clear() {
        stewardService.clear();
    }
}
