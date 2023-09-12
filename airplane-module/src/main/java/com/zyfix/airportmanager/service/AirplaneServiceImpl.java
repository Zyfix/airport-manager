package com.zyfix.airportmanager.service;


import com.zyfix.airportmanager.data.model.Airplane;

import com.zyfix.airportmanager.data.repository.AirplaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AirplaneServiceImpl implements AirplaneService {


    private final AirplaneRepository airplaneRepository;

    @Autowired
    public AirplaneServiceImpl(AirplaneRepository airplaneRepository) {
        this.airplaneRepository = airplaneRepository;
    }

    @Override
    public Optional<Airplane> findById(UUID id) {
        return airplaneRepository.findById(id);
    }

    @Override
    public Optional<List<Airplane>> findAll() {
        return Optional.of(airplaneRepository.findAll());
    }

    @Override
    public Optional<Page<Airplane>> findAllPagedBy(int offset, int size) {
        return Optional.of(airplaneRepository.findAll(PageRequest.of(offset, size)));
    }

    @Override
    public Optional<List<Airplane>> findAllSortedBy(String direction, String field) {
        return Optional.of(airplaneRepository.findAll(Sort.by(Sort.Direction.fromString(direction), field)));
    }

    @Override
    public Optional<List<Airplane>> findAllByManufacturer(String manufacturer) {
//        return Optional.of(airplaneRepository.findAllByManufacturer(manufacturer));
        return Optional.of(new ArrayList<>());
    }

    @Override
    public List<String> findAllManufacturers() {
//        return airplaneRepository.findAllManufacturers();
        return new ArrayList<>();
    }

    @Override
    public Optional<Airplane> create(Airplane airplane) {
        Airplane airplaneFromDb = airplaneRepository.save(airplane);
        return Optional.of(airplaneFromDb);
    }

    @Override
    public Optional<Airplane> delete(UUID id) {
        Optional<Airplane> airplaneFromDb = airplaneRepository.findById(id);
        if (airplaneFromDb.isPresent()) {
            airplaneRepository.delete(airplaneFromDb.get());
            return airplaneFromDb;
        }

        return Optional.empty();
    }

    @Override
    public void seed() {
        airplaneRepository.saveAll(getPreparedAirplanes());
    }

    @Override
    public void clear() {
        airplaneRepository.deleteAll();
    }

    private List<Airplane> getPreparedAirplanes() {
        return List.of(
                new Airplane("Boeing", "737", 200),
                new Airplane("Airbus", "A320", 180)
//                new Steward("Jon", "Olsson", LocalDate.of(1990, 7, 1), LocalDate.of(2003, 8, 2)),
//                new Steward("Janni", "Olsson", LocalDate.of(1980, 2, 4), LocalDate.of(2005, 1, 4)),
//                new Steward("Michal", "Drevo", LocalDate.of(1974, 4, 7), LocalDate.of(1999, 6, 19)),
//                new Steward("Ondrej", "Pochoutka", LocalDate.of(2001, 9, 29), LocalDate.of(2017, 11, 30))
        );
    }

}
