package isis.projet.backend.dao;

import isis.projet.backend.entity.Participation;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ParticipationDAO {
    private final ParticipationRepository participationRepository;

    public ParticipationDAO(ParticipationRepository participationRepository) {
        this.participationRepository = participationRepository;
    }

    // Sauvegarde une participation
    public Participation save(Participation participation) {
        return participationRepository.save(participation);
    }

    // Vérifie si une participation existe déjà entre une personne et un projet
    public Optional<Participation> findByPersonneAndProjet(Integer matricule, Integer idProjet) {
        return participationRepository.findByPersonneMatriculeAndProjetId(matricule, idProjet);
    }

}
