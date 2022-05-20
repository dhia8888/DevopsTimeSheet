package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

@Service
public class EntrepriseServiceImpl implements IEntrepriseService {

    @Autowired
    EntrepriseRepository entrepriseRepoistory;
    @Autowired
    DepartementRepository deptRepoistory;

    private static final Logger l = LogManager.getLogger(EntrepriseServiceImpl.class);

    public int ajouterEntreprise(Entreprise entreprise) {
        l.info("Methode ajouter entreprise en cours");
        l.debug("enregistrement d'un entreprise");
        if (entreprise.getName() == null || entreprise.getRaisonSocial() == null) {
            l.debug("l'ajout d'un entreprise a echoué");
            l.info("Methode ajouter entreprise terminé");
            return 0;
        } else {
            entrepriseRepoistory.save(entreprise);
            l.debug("Enregistrement d'entreprise effectuée");
            l.info("Methode ajouter entreprise terminé");
            return entreprise.getId();
        }
    }

    public int ajouterDepartement(Departement dep) {
        l.info("Methode ajouter departement en cours");
        l.debug("enregistrement d'une departement");
        deptRepoistory.save(dep);
        l.debug("Enregistrement du departement effectuée");
        l.info("Methode ajouter departement terminé");
        return dep.getId();
    }

    public int affecterDepartementAEntreprise(int depId, int entrepriseId) {
        l.info("Methode affecter departement a entreprise en cours");
        l.debug("recherche d'un entreprise par id ");
        Optional<Entreprise> value = entrepriseRepoistory.findById(entrepriseId);
        if (value.isPresent()) {
            Entreprise entrepriseManagedEntity = value.get();
            l.debug("entreprise" + entrepriseManagedEntity + "trouve");
            l.debug("recherche d'une departement par id  ");
            Optional<Departement> value1 = deptRepoistory.findById(depId);
            if (value1.isPresent()) {
                Departement depManagedEntity = value1.get();
                l.debug("departement" + depManagedEntity + "trouvvé");
                depManagedEntity.setEntreprise(entrepriseManagedEntity);
                deptRepoistory.save(depManagedEntity);
                l.info("Methode affecter departement a entreprise terminée");
            }
        } else {
            l.debug("l'entreprise non trouvé");
			l.debug("departement non trouvéee");
			l.info("Methode affecter departement a entreprise terminée");
        }
        return depId;
    }

    public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		l.info("Methode get all departments names by entreprise en cours");
		l.debug("Recherche d'entreprise par id");
		Optional<Entreprise> value = entrepriseRepoistory.findById(entrepriseId);
		if (value.isPresent())
		{
			Entreprise entrepriseManagedEntity = value.get();
			l.debug("entreprise" + entrepriseManagedEntity + "trouvée");
			List<String> depNames = new ArrayList<>();
			for (Departement dep : entrepriseManagedEntity.getDepartements()) {
				depNames.add(dep.getName());
			}
			l.debug("Ajout des données dans depNames");
			l.info("Methode get all department names by entreprise terminée");
			return depNames;
		}
		else {
			l.debug("l'entreprisee non trouvée");
			l.info("Methode get all department names by entreprise terminée");
			return new ArrayList<>();
		}
    }
    @Transactional
    public void deleteEntrepriseById(int entrepriseId) {
		l.info("Methode delete entreprise by id en cours");
		l.debug("supression d'un entreprise en cours");
		Optional<Entreprise> value = entrepriseRepoistory.findById(entrepriseId);
		if (value.isPresent()) {
			Entreprise ent = value.get();
			entrepriseRepoistory.delete(ent);
			l.debug("supression effectée");
			l.info("Methode delete entreprise by id terminée");
		}
		else {
			l.debug("l'entreprise non trouvée");
			l.info("Methode delete entreprise by id terminée");
		}
	}

    @Transactional
    public void deleteDepartementById(int depId) {
		l.info("Methode delete departement by id en cours");
		l.debug("supression d'une departement en cours");
		Optional<Departement> value = deptRepoistory.findById(depId);
		if (value.isPresent()) {
			Departement dep = value.get();
			deptRepoistory.delete(dep);
			l.debug("supression effectuée");
			l.info("Methode delete department by id terminée");
		}
		else {
			l.debug("le departement non trouvée");
			l.info("Methode delete department by id terminée");
		}
    }

    public Entreprise getEntrepriseById(int entrepriseId) {
		l.info("Methode get entreprise by id en cours");
		l.debug("recherche d'entreprise par id en cours");
		Optional<Entreprise> value = entrepriseRepoistory.findById(entrepriseId);
		if (value.isPresent()) {
			Entreprise ent = value.get();
			l.debug("l'entreprise " + ent + "trouvé");
			l.info("Methode get entreprise by id terminée");
			return ent;
		}
		else {
			l.debug("l'entreprise non trouvée");
			l.info("Methode get entreprise by id terminée");
			return null;
		}
	}


}
