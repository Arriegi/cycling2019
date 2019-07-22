package eus.jarriaga.imputador.business.repository;

import eus.jarriaga.cycling.business.BusinessConfig;
import eus.jarriaga.cycling.business.entities.*;
import eus.jarriaga.cycling.business.repositories.*;
import eus.jarriaga.cycling.web.WebConfig;
import eus.jarriaga.cycling.web.security.WebSecurityConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {WebSecurityConfig.class, WebConfig.class, BusinessConfig.class})
@WebAppConfiguration
public class WorkPartTest {

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private WorkPartRepository workPartRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    private Operation operation1, operation2;
    private User user1, user2;
    private Client client1, client2;
    private Project project1, project2, project3;
    private WorkPart part1, part2, part3, part4, part5;

    @Before
    public void setUp() {
        client1 = new Client();
        client1.setName("Client1");
        client1.setDischargeDate(LocalDate.now());
        client1 = clientRepository.save(client1);

        client2 = new Client();
        client2.setName("Client2");
        client2.setDischargeDate(LocalDate.now());
        client2 = clientRepository.save(client2);

        project1 = new Project();
        project1.setName("Project1");
        project1.setArchived(false);
        project1.setAcceptedBudget(false);
        project1.setClient(client1);
        project1.setDischargeDate(LocalDate.now());

        project2 = new Project();
        project2.setName("Project2");
        project2.setArchived(true);
        project2.setAcceptedBudget(true);
        project2.setClient(client1);
        project2.setDischargeDate(LocalDate.now());

        project3 = new Project();
        project3.setName("Project3");
        project3.setArchived(true);
        project3.setAcceptedBudget(false);
        project3.setClient(client2);
        project3.setDischargeDate(LocalDate.now());

        project1 = projectRepository.save(project1);
        project2= projectRepository.save(project2);
        project3= projectRepository.save(project3);

        operation1 = new Operation();
        operation1.setName("Presupuestar");
        operation1.setDescription("Deskribapena presupuestar");

        operation2 = new Operation();
        operation2.setName("Diseñar");
        operation2.setDescription("Deskribapena diseñar");

        operation1 = operationRepository.save(operation1);
        operation2 = operationRepository.save(operation2);

        user1 = userRepository.findById(1L).orElseThrow(()->new UsernameNotFoundException("1"));
        user2 = userRepository.findById(2L).orElseThrow(()->new UsernameNotFoundException("2"));

        LocalDate today = LocalDate.now();

        part1 = new WorkPart();
        //User1-en parte guztiak
        part1.setUser(user1);
        part1.setDate(today.minusDays(30));
        part1.setOperation(operation1);
        part1.setProject(project1);
        part1.setDuration(Duration.ofHours(4));

        part2 = new WorkPart();
        //User1-en parte guztiak
        part2.setUser(user1);
        part2.setDate(today.minusDays(2));
        part2.setOperation(operation1);
        part2.setProject(project2);
        part2.setDuration(Duration.ofHours(6));

        part3 = new WorkPart();
        //User1-en parte guztiak
        part3.setUser(user1);
        part3.setDate(today);
        part3.setOperation(operation2);
        part3.setProject(project3);
        part3.setDuration(Duration.ofHours(2));

        part4 = new WorkPart();
        //User1-en parte guztiak
        part4.setUser(user2);
        part4.setDate(today.minusDays(7));
        part4.setOperation(operation2);
        part4.setProject(project2);
        part4.setDuration(Duration.ofHours(1));

        part5 = new WorkPart();
        //User1-en parte guztiak
        part5.setUser(user2);
        part5.setDate(today.minusDays(22));
        part5.setOperation(operation1);
        part5.setProject(project3);
        part5.setDuration(Duration.ofMinutes(15));

        part1 = workPartRepository.save(part1);
        part2 = workPartRepository.save(part2);
        part3 = workPartRepository.save(part3);
        part4 = workPartRepository.save(part4);
        part5 = workPartRepository.save(part5);
    }

    @Test
    public void testCount() {
        assertEquals(3, projectRepository.count());
        assertEquals(2, clientRepository.count());
        assertEquals(2, userRepository.count());
        assertEquals(2, operationRepository.count());
        assertEquals(5, workPartRepository.count());
    }

    @Test
    public void testWorkParts() {
        assertEquals(5, workPartRepository.count());
        assertEquals(2, workPartRepository.findAllByProjectClient(client2).size());

        assertEquals(1, workPartRepository.findAllByProject(project1).size());
        assertEquals(3, workPartRepository.findAllByDateBetween(LocalDate.now().minusDays(10),LocalDate.now()).size());

        assertEquals(3, workPartRepository.findAllByUser(user1).size());
        assertEquals(2, workPartRepository.findAllByUser(user2).size());

        assertEquals(2,
                workPartRepository.findAllByUserAndDateBetween(user1, LocalDate.now().minusDays(10),LocalDate.now()).size());
        assertEquals(0,
                workPartRepository.findAllByProjectAndDateBetween(project1, LocalDate.now().minusDays(10),LocalDate.now()).size());

        assertEquals(2,
                workPartRepository.findAllByProjectClientAndDateBetween(client1, LocalDate.now().minusDays(10),LocalDate.now()).size());
    }

    @After
    public void tearDown() {
        workPartRepository.delete(part1);
        workPartRepository.delete(part2);
        workPartRepository.delete(part3);
        workPartRepository.delete(part4);
        workPartRepository.delete(part5);

        operationRepository.delete(operation1);
        operationRepository.delete(operation2);

        projectRepository.delete(project1);
        projectRepository.delete(project2);
        projectRepository.delete(project3);

        clientRepository.delete(client1);
        clientRepository.delete(client2);
    }

}
