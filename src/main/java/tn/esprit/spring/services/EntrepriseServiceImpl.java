package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

@Service
public class EntrepriseServiceImpl implements IEntrepriseService {
	private static final Logger logger = Logger.getLogger(EmployeServiceImpl.class);

	@Autowired
    EntrepriseRepository entrepriseRepoistory;
	@Autowired
	DepartementRepository deptRepoistory;
	
	public int ajouterEntreprise(Entreprise entreprise) {
		int id = 0 ; 
		try {
			logger.info("In ajouterEntreprise("+ entreprise +")");
			entrepriseRepoistory.save(entreprise);
			id = entreprise.getId();
			logger.info("ajouterEntreprise() : " + id);
			} catch (Exception e) {logger.error("Erreur : " + e);}
			//return employe	
		return entreprise.getId();
	}
	
	public int ajouterDepartement(Departement dep) {
		int id = 0 ; 
		try {
			logger.info("In ajouterDepartement("+ dep +")");
			deptRepoistory.save(dep);
			id = dep.getId();
			logger.info("ajouterEntreprise() : " + id);
		}catch (Exception e) {logger.error("Erreur : " + e);}
		
		return dep.getId();
	}
	
	public void affecterDepartementAEntreprise(int depId, int entrepriseId) {
		try {
			logger.info("In affecterDepartementAEntreprise(" + depId + ","+ entrepriseId +")");
			Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).get();
			Departement depManagedEntity = deptRepoistory.findById(depId).get();
			depManagedEntity.setEntreprise(entrepriseManagedEntity);
			deptRepoistory.save(depManagedEntity);
		}catch (Exception e) {logger.error("Erreur : " + e);}
		
		
				
		
	}
	
	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).get();
		List<String> depNames = new ArrayList<>();
		for(Departement dep : entrepriseManagedEntity.getDepartements()){
			depNames.add(dep.getName());
		}
		
		return depNames;
	}

	@Transactional
	public void deleteEntrepriseById(int entrepriseId) {
		try {
			logger.info("In deleteEntrepriseById(" + entrepriseId + ")");
			entrepriseRepoistory.delete(entrepriseRepoistory.findById(entrepriseId).get());	
		}catch (Exception e) {logger.error("Erreur : " + e);}
		
	}

	@Transactional
	public void deleteDepartementById(int depId) {
		try {
			logger.info("In deleteDepartementById(" + depId + ")");
			deptRepoistory.delete(deptRepoistory.findById(depId).get());	
		}catch (Exception e) {logger.error("Erreur : " + e);}
		
	}


	public Entreprise getEntrepriseById(int entrepriseId) {
		Entreprise en = null ; 
		try {
			logger.info("In getEntrepriseById(" + entrepriseId + ")");
			en = entrepriseRepoistory.findById(entrepriseId).get();	
			logger.info("Out getEntrepriseById() : " + en);
		}catch (Exception e) {logger.error("Erreur : " + e);}
		return entrepriseRepoistory.findById(entrepriseId).get();	
	}
	
	@Override
	public List<Entreprise> retrieveAllEntreprises() {
		logger.info("In  retrieveAllEmployes : "); 
		List<Entreprise> entreprises = (List<Entreprise>) entrepriseRepoistory.findAll();  
		for (Entreprise entreprise : entreprises) {
			logger.debug("entreprise +++ : " + entreprise);
		}
		logger.info("Out of retrieveAllEntreprises."); 
		return entreprises;
		
	}

}
