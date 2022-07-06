package com.qbaaa.StarWars.repositories;

import com.qbaaa.StarWars.models.Heroes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HeroesRepository extends JpaRepository<Heroes, Integer> {

    List<Heroes> findAll();
    Optional<Heroes> findById(Integer integer);
}
