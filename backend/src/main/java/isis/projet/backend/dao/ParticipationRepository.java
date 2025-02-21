package isis.projet.backend.dao;

import isis.projet.backend.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Integer> {

    // Requête pour trouver une participation spécifique d'une personne à un projet
    Optional<Participation> findByPersonneMatriculeAndProjetId(Integer matricule, Integer id);

}
