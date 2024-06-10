package ru.webdev.location.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.webdev.location.model.Geodata;

@Repository
public interface GeodataRepository extends CrudRepository<Geodata, Integer> {
}
