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
import tn.esprit.spring.entities.Mission;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MissionServiceImplTest {
    @Autowired
    private MissionServiceImpl serviceMission;

    private static final Logger log = LogManager.getLogger(MissionServiceImplTest.class);
    @Test
    @Order(1)
    public void add() {
        log.info("TESTING ADD Mission IN PROGRESS");
        Mission m0 = new Mission("design", "design");
        Mission m1 = new Mission("coding", "conding");
        Mission m2 = new Mission("test", "test");
        if(m1 != null) {
            int i1 = serviceMission.add(m1);
        }
        if(m2 != null) {
            int i2 = serviceMission.add(m2);
        }
        int id = serviceMission.add(m0);
        Assert.assertNotNull(serviceMission.getById(id));
        log.info("Mission added successfully");
    }

}
