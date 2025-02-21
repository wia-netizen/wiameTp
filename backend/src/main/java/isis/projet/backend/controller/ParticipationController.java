package isis.projet.backend.controller;

import isis.projet.backend.controller.ParticipationCreationDTO;
import isis.projet.backend.controller.ParticipationDTO;
import isis.projet.backend.entity.Participation;
import isis.projet.backend.service.ParticipationProjet;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/gestion/participation")
public class ParticipationController {

    private final ParticipationProjet participationService;
    private final ModelMapper mapper;

    public ParticipationController(ParticipationProjet participationService, ModelMapper mapper) {
        this.participationService = participationService;
        this.mapper = mapper;
    }

    /**
     * Enregistre une participation d'une personne à un projet.
     *
     * @param dto Contient le matricule de la personne, le code du projet, le rôle et le pourcentage
     * @return Réponse HTTP avec la participation créée ou un message d'erreur
     */
    @PostMapping
    public ResponseEntity<?> enregistrerParticipation(@RequestBody ParticipationCreationDTO dto) {
        try {
            // Appel du service métier
            Participation participation = participationService.enregistrerParticipation(
                    dto.getMatricule(),
                    dto.getCodeProjet(),
                    dto.getRole(),
                    dto.getPourcentage()
            );
            // Transformation de l'entité en DTO d'affichage
            ParticipationDTO body = mapper.map(participation, ParticipationDTO.class);
            return ResponseEntity.ok(body);
        } catch (NoSuchElementException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(new ApiErrorDTO(e.getMessage()));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(new ApiErrorDTO("Cette personne participe déjà au projet"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiErrorDTO("Une erreur est survenue : " + e.getMessage()));
        }
    }
}