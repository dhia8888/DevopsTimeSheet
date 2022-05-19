package tn.esprit.spring.services;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.services.IDepartementService;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartementServiceTest {
    
    private static final Logger log = LogManager.getLogger(DepartementServiceTest.class);

    
    private List<Departement> departementList;
    
    @Autowired
    private IDepartementService departementService;
    
    @Test
    @Order(1)
    public void testgetAllDepartements() {
        
        log.info("TESTING GET ALL DEPARTEMENTS IN PROGRESS");
        
        try{
            departementList = departementService.getAllDepartements();
            
            if(departementList.isEmpty()) {
                log.warn("departement not found");
            }
            
        }catch (Exception e) {
            log.error("Please add a departement ! " + e.getMessage());
        }
    }
    

}

