package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Formulaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Formulaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormulaireRepository extends JpaRepository<Formulaire, Long> {

    @Query(value = "select distinct formulaire from Formulaire formulaire left join fetch formulaire.questions",
        countQuery = "select count(distinct formulaire) from Formulaire formulaire")
    Page<Formulaire> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct formulaire from Formulaire formulaire left join fetch formulaire.questions")
    List<Formulaire> findAllWithEagerRelationships();

    @Query("select formulaire from Formulaire formulaire left join fetch formulaire.questions where formulaire.id =:id")
    Optional<Formulaire> findOneWithEagerRelationships(@Param("id") Long id);

}
