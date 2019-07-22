package eus.jarriaga.cycling.business.repositories;

import eus.jarriaga.cycling.business.entities.GeneralConfiguration;
import org.springframework.data.repository.CrudRepository;

public interface GeneralConfigurationRepository extends CrudRepository<GeneralConfiguration,Long> {
    Iterable<GeneralConfiguration> findAllByOrderById();
}
