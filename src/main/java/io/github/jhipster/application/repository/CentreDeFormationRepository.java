package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.CentreDeFormation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CentreDeFormation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CentreDeFormationRepository extends JpaRepository<CentreDeFormation, Long> {

}
