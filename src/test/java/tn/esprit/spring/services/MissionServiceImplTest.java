package tn.esprit.spring.services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MissionServiceImplTest {
    @Autowired
    private IMissionService serviceMission;
    @Autowired
    private ITimesheetService timesheetService ;

    private static final Logger log = LogManager.getLogger(MissionServiceImplTest.class);
    @Test
    @Order(1)
    public void add() {
        log.info("TESTING ADD Mission IN PROGRESS");
        Mission m0 = new Mission("design", "design");
        Mission m1 = new Mission("coding", "conding");
        Mission m2 = new Mission("test", "test");
        timesheetService.ajouterMission(m1);
        timesheetService.ajouterMission(m2);
        int id =  timesheetService.ajouterMission(m0);
        Assert.assertNotNull(timesheetService.getMissionById(id));
        log.info("Mission added successfully");
    }

    @Test
    @Order(2)
    public void testgetEntrepriseById() {
        log.info("TESTING GET Missio, BY ID IN PROGRESS");
       Mission m = new Mission("Package", "Package");
        int id = timesheetService.ajouterMission(m);
        System.out.println("get id test verification"+id);
        Mission m2 = timesheetService.getMissionById(id);
        Assert.assertEquals(m.getId(),m2.getId());
    }
    @Test
    @Order(3)
    public void testgetAllMission()  {
        log.info("TESTING GET ALL MISSIO? IN PROGRESS");
        try{
            List<Mission> missions=serviceMission.getAll();
            if(missions.isEmpty()) {
                log.warn("Mission not found");
            }
        }catch (Exception e) {
            log.error("Please add a Mission ! " + e.getMessage());
        }
    }
   
}
