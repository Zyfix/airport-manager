package com.zyfix.airportmanager.data.repo;


import com.zyfix.airportmanager.data.model.Steward;
import org.springframework.context.annotation.Bean;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Repository
@Transactional
public interface StewardRepository extends JpaRepository<Steward, UUID> {

    @Query("select s from Steward s where s.hireDate >= :from and s.hireDate <= :to")
    List<Steward> findStewardsByHireDate(LocalDate from, LocalDate to);

    @Query("select s from Steward s where s.dateOfBirth >= :from and s.dateOfBirth <= :to")
    List<Steward> findStewardsByDateOfBirth(LocalDate from, LocalDate to);

}
