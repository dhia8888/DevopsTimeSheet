package tn.esprit.spring.services;
import tn.esprit.spring.entities.Contrat;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Assert;
import java.util.List;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ContratServiceImplTest {

    private List<Contrat> contratList;
    @Autowired
    private IContratService serviceContrat;

    private static final Logger log = LogManager.getLogger(ContratServiceImplTest.class);

    @Test
    @Order(1)
    public void testgetAllContrats() {
        log.info("TESTING GET ALL CONTRATS IN PROGRESS");
        try{
            contratList = serviceContrat.getAllContrats();
            if(contratList.isEmpty()) {
                log.warn("contrats not found");
            }
        }catch (Exception e) {
            log.error("Please add a contrat ! " + e.getMessage());
        }
    }
}