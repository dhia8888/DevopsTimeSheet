package tn.esprit.spring.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.spring.entities.Entreprise;
import org.junit.Assert;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.repository.EntrepriseRepository;
import java.util.List;
import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class EntrepriseTest {
        private List<Departement> departementList;
        @Autowired
        private IEntrepriseService serviceEntreprise;
        @Autowired
        private EntrepriseRepository repositoryEntreprise;
        @Autowired
        private IDepartementService serviceDepartement;

        @Test
        @Order(1)
        public void AjouterEntreprisetest1() {
            Entreprise ent = new Entreprise("vermeg", "banque");
            int id = serviceEntreprise.ajouterEntreprise(ent);
            Assert.assertNotNull(serviceEntreprise.getEntrepriseById(id));
            log.info("added successfully");
        }

        @Test
        @Order(2)
        public void ajouterEntreprisetest2()  {
            Entreprise ent=new Entreprise(null,null);
            int a=serviceEntreprise.ajouterEntreprise(ent);
            assertEquals(0,a);
            log.info("added successfully");
        }

        @Test
        @Order(3)
        public void getEntrepriseByIdtest() {

            Entreprise ent = new Entreprise("Securite", "analysis");
            int id = serviceEntreprise.ajouterEntreprise(ent);
            Entreprise e1 = serviceEntreprise.getEntrepriseById(id);
            Assert.assertNotNull(e1);
            Entreprise e2 = serviceEntreprise.getEntrepriseById(2);
            assertNull(e2);
        };

        @Test
        @Order(4)
        public void ajouterDepartementtest()  {
            Departement dep =new Departement("Embarque");
            int a=serviceEntreprise.ajouterDepartement(dep);
            assertNotNull(a);
            assertNotEquals(a,0);
            log.info("added successfully");
        }

        @Test
        @Order(5)
        public void getEntrepriseByIdTest()  {
            Entreprise ent=serviceEntreprise.getEntrepriseById(1);
            assertNotNull(ent);
            Entreprise entr=repositoryEntreprise.findById(1).get();
            assertEquals(entr.getName(),ent.getName());
            assertEquals(entr.getRaisonSocial(),ent.getRaisonSocial());
        }

        @Test
        @Order(6)
        public void getEntrepriseByIdTest1()  {
            Entreprise ent=serviceEntreprise.getEntrepriseById(2);
            assertNull(ent);
        }

        @Test
        @Order(7)
        public void getAllDepartementsNamesByEntrepriseTest1()  {
            List<String> names=serviceEntreprise.getAllDepartementsNamesByEntreprise(40);
            assertNotNull(names);
        }

        @Test
        @Order(8)
        void testgetAllDepartements() throws Exception {
        departementList = serviceDepartement.getAllDepartements();
        if(departementList.isEmpty()) {
            log.info("departments not found");
            throw new Exception("Please add a departement");
        }
        }

        @Test
        @Order(9)
        public void testaffecterDepartementAEntreprise() {
            Entreprise ent = new Entreprise("MobileDev", "DEV");
            int idEntrep = serviceEntreprise.ajouterEntreprise(ent);
            Departement dep = new Departement("WebDep");
            int idDep = serviceEntreprise.ajouterDepartement(dep);
            int idEntrepDep = serviceEntreprise.affecterDepartementAEntreprise(idDep, idEntrep);
            Assert.assertEquals(idEntrepDep,idEntrep);
        }

}
