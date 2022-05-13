package tn.esprit.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.spring.entities.Mission;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Role;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TimesheetServiceImplTest {
    @Autowired
    private ITimesheetService sTimesheet;
    @Autowired
    private IEmployeService sEmploye;
    @Autowired
    private IEntrepriseService sEntreprise;


    @Test
    public void testAjouterMission() {
        Mission mission = new Mission("FBI", "TUNIS");
        int id = sTimesheet.ajouterMission((mission));

        assertNotNull(sTimesheet.getMissionById(id));
    }

    @Test
    public void testGetMissionById() {

        Mission mission1 = new Mission("alfa", "b");
        int id = sTimesheet.ajouterMission(mission1);

        Mission mission2 = sTimesheet.getMissionById(id);
        assertNotNull(mission2);

        Mission mission3 = sTimesheet.getMissionById(213232);
        assertNull(mission3);
    };

    @Test
    public void testAffecterMissionADepartement() {
        Mission mission = new Mission("FBI", "TUNIS");
        int idMission = sTimesheet.ajouterMission(mission);

        Departement departement = new Departement("AI");
        int idDepartement = sEntreprise.ajouterDepartement(departement);
        int idDepAffecte = sTimesheet.affecterMissionADepartement(idMission, idDepartement);
        assertEquals(idDepAffecte, idDepartement);

    }

    @Test
    public void testValiderTimesheet() {
        Mission mission = new Mission("FBI", "TUNIS");
        sTimesheet.ajouterMission(mission);

        Employe ingenieur = new Employe("DHIA", "HAN", "Ahmed.Saidi@esprit.tn", true, Role.INGENIEUR);
        Employe chef = new Employe("ABDKADER", "ARF", "Ahmed.Saidi@esprit.tn", true, Role.CHEF_DEPARTEMENT);

        Date dateDebut = new Date(System.currentTimeMillis());
        Date dateFin = new Date(System.currentTimeMillis());

        sEmploye.addOrUpdateEmploye(ingenieur);
        sEmploye.addOrUpdateEmploye(chef);

        assertEquals(0,sTimesheet.validerTimesheet(mission.getId(), ingenieur.getId(), dateDebut, dateFin, chef.getId()));
        assertEquals(-1,sTimesheet.validerTimesheet(mission.getId(), ingenieur.getId(), dateDebut, dateFin, ingenieur.getId()));

    }
}
