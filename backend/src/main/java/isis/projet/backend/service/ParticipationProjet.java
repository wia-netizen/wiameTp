package isis.projet.backend.service;

import isis.projet.backend.dao.ParticipationDAO;
import isis.projet.backend.dao.PersonneRepository;
import isis.projet.backend.dao.ProjetRepository;
import isis.projet.backend.entity.Participation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;

@Service
public class ParticipationProjet {
    private static final Logger logger = LoggerFactory.getLogger(ParticipationProjet.class);

    private final PersonneRepository personneDao;
    private final ProjetRepository projetDao;
    private final ParticipationDAO participationDAO;  // Utilisation du DAO

    public ParticipationProjet(PersonneRepository personneDao, ProjetRepository projetDao, ParticipationDAO participationDAO) {
        this.projetDao = projetDao;
        this.personneDao = personneDao;
        this.participationDAO = participationDAO;
    }

    @Transactional
    public Participation enregistrerParticipation(Integer matricule, Integer codeProjet, String role, float pourcentage) {
        logger.info("Début de l'enregistrement d'une participation : matricule={}, projet={}, rôle={}, pourcentage={}",
                matricule, codeProjet, role, pourcentage);

        var affectation = projetDao.findById(codeProjet)
                .orElseThrow(() -> new NoSuchElementException("Projet non trouvé avec le code : " + codeProjet));

        if (affectation.getFin() != null) {
            throw new IllegalStateException("Le projet est terminé");
        }

        var contributeur = personneDao.findById(matricule)
                .orElseThrow(() -> new NoSuchElementException("Personne non trouvée avec le matricule : " + matricule));

        var existingParticipation = participationDAO.findByPersonneAndProjet(matricule, codeProjet);
        if (existingParticipation.isPresent()) {
            throw new IllegalStateException("Cette personne participe déjà à ce projet.");
        }

        var participation = new Participation(role, pourcentage, affectation, contributeur);
        participationDAO.save(participation);

        logger.info("Participation enregistrée avec succès : {}", participation);
        return participation;
    }
}
