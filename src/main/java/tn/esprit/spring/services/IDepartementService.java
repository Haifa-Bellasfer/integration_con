package tn.esprit.spring.services;

import java.util.List;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;


public interface IDepartementService {
	
	
	public List<Departement> getAllDepartements();
	public int ajouterDepartement(Departement dep);
	public String getDepartmentById(int depId);
	public void deleteDepartementById(int depId);

	
	
	

	
}
