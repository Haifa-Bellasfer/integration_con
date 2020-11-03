package tn.esprit.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.*;
import tn.esprit.spring.services.IEmployeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeServiceImplTest {
	
	@Autowired 
	IEmployeService es; 
				
	@Test
	public void testAddOrUpdateEmploye() {
		Employe e = new Employe("bannouri","ac","ab@esprit.tn","abc",true,Role.ADMINISTRATEUR);
		int id = es.addOrUpdateEmploye(e);
		assertEquals(id,e.getId());
	}
	
	@Test
	public void testRetrieveEmploye() {
		Employe emp = es.retrieveEmploye(1);
		assertEquals(1,emp.getId());
		
	}
	
	@Test
	public void testRetrieveAllEmployes() {
		List<Employe> listEmployes = es.retrieveAllEmployes(); 
		// if there are 5 users in DB : 
		assertEquals(5, listEmployes.size());
	}
	
	@Test
	public void testDeleteEmployeById() {
		es.deleteEmployeById(8);
		List<Employe> listEmployes = es.retrieveAllEmployes();
		assertEquals(4, listEmployes.size());
	}
	
	@Test 
	public void testAjouterContrat() {
		Employe e = new Employe(3,"bannouri","ac","ab@esprit.tn","abc",true,Role.ADMINISTRATEUR);
		String sDate1="31/12/1998";  
	    Date date1=new Date(sDate1);  
		Contrat c = new Contrat(1,date1,"Type",53995992,e,1200);
		assertEquals(c.getReference(), es.ajouterContrat(c));
	}
	
	@Test
	public void testGetEmployePrenomById() {
		assertEquals("bannouri",es.getEmployePrenomById(1));
				
	}
	
}