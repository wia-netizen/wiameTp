package isis.projet.backend.service;

import isis.projet.backend.dao.ParticipationDAO;
import isis.projet.backend.entity.Participation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ParticipationService {
    private final ParticipationDAO participationDAO;

    public ParticipationService(ParticipationDAO participationDAO) {
        this.participationDAO = participationDAO;
    }

    @Transactional
    public Participation enregistrerParticipation(Participation participation) {
        // Logique de validation ou de traitement supplémentaire peut être ajoutée ici
        return participationDAO.save(participation);
    }

    public Optional<Participation> trouverParticipation(Integer matricule, Integer codeProjet) {
        return participationDAO.findByPersonneAndProjet(matricule, codeProjet);
    }
}
