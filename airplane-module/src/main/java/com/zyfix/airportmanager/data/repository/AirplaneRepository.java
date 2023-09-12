package com.zyfix.airportmanager.data.repository;
//
//
//import com.zyfix.airportmanager.data.model.Airplane;
//import jakarta.transaction.Transactional;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.UUID;
//
//@Repository
//@Transactional
//public interface AirplaneRepository extends JpaRepository<Airplane, UUID> {
//
//
//    /**
//     * Find all airplanes from manufacturer.
//     *
//     * @param manufacturer Manufacturer name
//     * @return List of airplanes from manufacturer
//     */
//    @Query("SELECT a FROM Airplane a WHERE a.manufacturer = :manufacturer")
//    List<Airplane> findAllByManufacturer(String manufacturer);
//
//
//    /**
//     * Find all manufacturers. Sorted by name.
//     *
//     * @return List of manufacturers
//     */
//    @Query("SELECT DISTINCT a.manufacturer FROM Airplane a ORDER BY a.manufacturer")
//    List<String> findAllManufacturers();
//
//}


import java.util.UUID;

import com.zyfix.airportmanager.data.model.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

// we automatically will be able to
//
//        Create new Employees
//
//        Update existing ones
//
//        Delete Employees
//
//        Find Employees (one, all, or search by simple or complex properties)
@Repository
@Transactional
public interface AirplaneRepository extends JpaRepository<Airplane, UUID> {}
