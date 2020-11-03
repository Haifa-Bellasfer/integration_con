package tn.esprit.spring.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.repository.DepartementRepository;

@Service
public class DepartementServiceImpl implements IDepartementService {
	private static final Logger l = Logger.getLogger(EmployeServiceImpl.class);


	@Autowired
	DepartementRepository deptRepoistory;



	public List<Departement> getAllDepartements() {
		l.info("In  retrieveAllDepartement : "); 
		
		 List<Departement> names = (List<Departement>) deptRepoistory.findAll();
		 
		 for (Departement departement : names) {
				l.debug("departement +++ : " + departement);
			}
		 l.info("Out of retrieveAllDepartements."); 
		return names;
		
	}



	@Override
	public int ajouterDepartement(Departement dep) {
		int id = 0 ; 
		try {
			l.info("In ajouterDepartement("+ dep +")");
			deptRepoistory.save(dep);
			id = dep.getId();
			l.info("ajouterEntreprise() : " + id);
		}catch (Exception e) {l.error("Erreur : " + e);}
		
		return dep.getId();
	}



	@Override
	public String getDepartmentById(int depId) {
		String name ="" ;
		try {
		l.info("In getDepartmentById(" + depId + ")");
		Departement ManagedEntity = deptRepoistory.findById(depId).get();
		name = ManagedEntity.getName();
		l.info("Out getDepartmentById : " + name);
		}catch (Exception e) {l.error("Erreur : " + e);}

		return name;
	}



	@Override
	public void deleteDepartementById(int depId) {
		try {
			l.info("In deleteDepartementById(" + depId + ")");
			Departement dep = deptRepoistory.findById(depId).get();
			deptRepoistory.delete(dep);
		}catch (Exception e) {l.error("Erreur : " + e);}
		
	}

}
