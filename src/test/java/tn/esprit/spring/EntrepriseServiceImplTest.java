package tn.esprit.spring;

import static org.junit.Assert.assertEquals;
import java.text.ParseException;
import java.util.List;

import org.junit.Test;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import tn.esprit.spring.entities.*;
import tn.esprit.spring.services.IEntrepriseService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntrepriseServiceImplTest {
	
	@Autowired 
	IEntrepriseService es; 

	@Test
	public void testAddEntreprise() throws ParseException {
		Entreprise e = new Entreprise("test", "test"); 
		int entrepriseAdded = es.ajouterEntreprise(e); 
		assertEquals(entrepriseAdded,e.getId());
	}
	
	@Test
	public void testAddDepartement() throws ParseException {
		Departement d = new Departement("DepartementnameTest"); 
		int departementAdded = es.ajouterDepartement(d); 
		assertEquals(departementAdded,d.getId());
	}
	
	@Test
	public void testRetrieveAllDeparetement() {
		int entrepriseId = 2;
		List<String> deps = es.getAllDepartementsNamesByEntreprise(entrepriseId); 
		// if there are 0 dep in DB : 
		assertEquals(0,deps.size());
	}
	
	@Test
	public void testDeleteEntrepriseById() {
		es.deleteEntrepriseById(1);
		List<Entreprise> listEntreprises = es.retrieveAllEntreprises();
		assertEquals(4, listEntreprises.size());
	}

}