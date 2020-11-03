package tn.esprit.spring;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.services.IDepartementService;

public class DepartmentServiceImpTest {
	@Autowired
	IDepartementService IDepartement;
	
	@Test
	public void getAllDepartements() {
		List<Departement> departements = IDepartement.getAllDepartements();
		assertEquals(4,departements.size());	
	}
	
	@Test
	public void testAddDepartement() throws ParseException {
		Departement d = new Departement("DepartementnameTest"); 
		int departementAdded = IDepartement.ajouterDepartement(d); 
		assertEquals(departementAdded,d.getId());
	}
	@Test
	public void testDeleteEntrepriseById() {
		IDepartement.deleteDepartementById(1);
		List<Departement> listDep = IDepartement.getAllDepartements();
		assertEquals(4, listDep.size());
	}

	
}
