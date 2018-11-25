package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Formation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Formation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {

}
