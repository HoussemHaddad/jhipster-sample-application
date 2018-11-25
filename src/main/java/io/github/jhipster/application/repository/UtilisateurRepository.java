package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Utilisateur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    @Query(value = "select distinct utilisateur from Utilisateur utilisateur left join fetch utilisateur.roles",
        countQuery = "select count(distinct utilisateur) from Utilisateur utilisateur")
    Page<Utilisateur> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct utilisateur from Utilisateur utilisateur left join fetch utilisateur.roles")
    List<Utilisateur> findAllWithEagerRelationships();

    @Query("select utilisateur from Utilisateur utilisateur left join fetch utilisateur.roles where utilisateur.id =:id")
    Optional<Utilisateur> findOneWithEagerRelationships(@Param("id") Long id);

}
