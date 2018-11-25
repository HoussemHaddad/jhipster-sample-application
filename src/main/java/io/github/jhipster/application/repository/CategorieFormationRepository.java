package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.CategorieFormation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CategorieFormation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategorieFormationRepository extends JpaRepository<CategorieFormation, Long> {

}
