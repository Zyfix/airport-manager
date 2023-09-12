package com.zyfix.airportmanager.service;


import com.zyfix.airportmanager.data.model.Steward;


import com.zyfix.airportmanager.data.repo.StewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class StewardServiceImpl implements StewardService {


    @Autowired
    private StewardRepository stewardRepository;

    @Override
    public Optional<Steward> findById(UUID id) {
        return stewardRepository.findById(id);
    }

    @Override
    public Optional<Page<Steward>> findAllPagedBy(int offset, int size) {
        return Optional.of(stewardRepository.findAll(PageRequest.of(offset, size)));
    }

    @Override
    public Optional<List<Steward>> findAllSortedBy(String direction, String field) {
        return Optional.of(stewardRepository.findAll(Sort.by(Sort.Direction.fromString(direction), field)));
    }

    @Override
    public Optional<List<Steward>> findAll() {
        return Optional.of(stewardRepository.findAll());
    }

    @Override
    public Optional<List<Steward>> findStewardsByHireDate(LocalDate from, LocalDate to) {
        return Optional.of(stewardRepository.findStewardsByHireDate(from, to));
    }

    @Override
    public Optional<List<Steward>> findStewardsByBirthDate(LocalDate from, LocalDate to) {
        return Optional.of(stewardRepository.findStewardsByDateOfBirth(from, to));
    }

    @Override
    public Optional<Steward> create(Steward steward) {
        Steward createdSteward = stewardRepository.save(steward);
        return Optional.of(createdSteward);
    }

    @Override
    public Optional<Steward> delete(UUID id) {
        Optional<Steward> toDelete = stewardRepository.findById(id);
        if (toDelete.isPresent()) {
            stewardRepository.delete(toDelete.get());
            return toDelete;
        }
        return Optional.empty();
    }

    @Override
    public void seed() {
        stewardRepository.saveAll(getPreparedStewards());
    }

    @Override
    public void clear() {
        stewardRepository.deleteAll();
    }

    private List<Steward> getPreparedStewards() {
        return List.of(
                new Steward("Jon", "Olsson", LocalDate.of(1990, 7, 1), LocalDate.of(2003, 8, 2)),
                new Steward("Janni", "Olsson", LocalDate.of(1980, 2, 4), LocalDate.of(2005, 1, 4)),
                new Steward("Michal", "Drevo", LocalDate.of(1974, 4, 7), LocalDate.of(1999, 6, 19)),
                new Steward("Ondrej", "Pochoutka", LocalDate.of(2001, 9, 29), LocalDate.of(2017, 11, 30))
        );
    }
}
