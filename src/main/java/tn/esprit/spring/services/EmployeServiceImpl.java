package tn.esprit.spring.services;

import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class EmployeServiceImpl implements IEmployeService {

	private static final Logger logger = Logger.getLogger(EmployeServiceImpl.class);

	
	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;

	@Override
	public Employe authenticate(String login, String password) {
		Employe emp = null;
		try {
		logger.info("In authenticate(" + login + "," + password + ")");
		emp = employeRepository.getEmployeByEmailAndPassword(login, password);
		logger.info("Out authenticate() : " + emp);
		} catch (Exception e) {logger.error("Erreur : " + e);}
		//return employeRepository.getEmployeByEmailAndPassword(login, password);
		return emp;
	}

	@Override
	public int addOrUpdateEmploye(Employe employe) {
		int id = 0 ;
		try {
			logger.info("In addOrUpdateEmploye(" + employe + ")");
			employeRepository.save(employe);
			id = employe.getId();
			logger.info("Out addOrUpdateEmploye() : " + id);
			} catch (Exception e) {logger.error("Erreur : " + e);}
			//return employeRepository.getEmployeByEmailAndPassword(login, password);
		return employe.getId();
	}
	
	
	@Override
	public Employe retrieveEmploye (int id) {
		Employe emp = null;
		try {
			logger.info("In retrieve Employe: " + id);
			 emp = employeRepository.findById(id).orElse(null);
			logger.info("Out retrieveEmploye() : " + emp);
		}
		catch (Exception e) {logger.error("Erreur : " + e);}
		return emp;
	}
	
	@Override
	public List<Employe> retrieveAllEmployes() {
		logger.info("In  retrieveAllEmployes : "); 
		List<Employe> employes = (List<Employe>) employeRepository.findAll();  
		for (Employe employe : employes) {
			logger.debug("employe +++ : " + employe);
		}
		logger.info("Out of retrieveAllEmployes."); 
		return employes;
		
	}

	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		try {
			logger.info("In mettreAjourEmailByEmployeId(" + email + ","+ employeId +")");
			Employe employe = employeRepository.findById(employeId).get();
			employe.setEmail(email);
			employeRepository.save(employe);
		} catch (Exception e) {logger.error("Erreur : " + e);}
		
	}

	@Transactional	
	public void affecterEmployeADepartement(int employeId, int depId) {
		try {
			logger.info("In affecterEmployeADepartement(" + employeId + ","+ depId +")");
			Departement depManagedEntity = deptRepoistory.findById(depId).get();
			Employe employeManagedEntity = employeRepository.findById(employeId).get();

			if(depManagedEntity.getEmployes() == null){

				List<Employe> employes = new ArrayList<>();
				employes.add(employeManagedEntity);
				depManagedEntity.setEmployes(employes);
			}else{

				depManagedEntity.getEmployes().add(employeManagedEntity);
			}

			// à ajouter? 
			deptRepoistory.save(depManagedEntity); 
		}catch (Exception e) {logger.error("Erreur : " + e);}
		

	}
	@Transactional
	public void desaffecterEmployeDuDepartement(int employeId, int depId)
	{
		try {
			logger.info("In desaffecterEmployeDuDepartement(" + employeId + ","+ depId +")");
			Departement dep = deptRepoistory.findById(depId).get();

			int employeNb = dep.getEmployes().size();
			for(int index = 0; index < employeNb; index++){
				if(dep.getEmployes().get(index).getId() == employeId){
					dep.getEmployes().remove(index);
					break;//a revoir
				}
			}
		}catch (Exception e) {logger.error("Erreur : " + e);}
		
	} 
	
	// Tablesapce (espace disque) 

	public int ajouterContrat(Contrat contrat) {
		int reference = 0 ; 
		try {
			logger.info("In ajouterContrat(" + contrat + ")");
			contratRepoistory.save(contrat);
			reference = contrat.getReference();
			logger.info("Out ajouterContrat() : " + reference);
		}catch (Exception e) {logger.error("Erreur : " + e);}
		
		return contrat.getReference();
	}

	public void affecterContratAEmploye(int contratId, int employeId) {
		try {
			logger.info("In affecterContratAEmploye(" + contratId + ","+employeId+ ")");
		Contrat contratManagedEntity = contratRepoistory.findById(contratId).get();
		Employe employeManagedEntity = employeRepository.findById(employeId).get();

		contratManagedEntity.setEmploye(employeManagedEntity);
		contratRepoistory.save(contratManagedEntity);
		}
		catch (Exception e) {logger.error("Erreur : " + e);}

	}

	public String getEmployePrenomById(int employeId) {
		String prenom ="" ;
		try {
		logger.info("In getEmployePrenomById(" + employeId + ")");
		Employe employeManagedEntity = employeRepository.findById(employeId).get();
		prenom = employeManagedEntity.getPrenom();
		logger.info("Out getEmployePrenomById : " + prenom);
		}catch (Exception e) {logger.error("Erreur : " + e);}

		return prenom;
	}
	 
	public void deleteEmployeById(int employeId)
	{
		try {
			logger.info("In getEmployePrenomById(" + employeId + ")");
			Employe employe = employeRepository.findById(employeId).get();
			for(Departement dep : employe.getDepartements()){
				dep.getEmployes().remove(employe);
			}

			employeRepository.delete(employe);
		}catch (Exception e) {logger.error("Erreur : " + e);}
	}

	public void deleteContratById(int contratId) {
		try {
			logger.info("In deleteContratById(" + contratId + ")");
			Contrat contratManagedEntity = contratRepoistory.findById(contratId).get();
			contratRepoistory.delete(contratManagedEntity);
		}catch (Exception e) {logger.error("Erreur : " + e);}
		
	}

	public int getNombreEmployeJPQL() {
		return employeRepository.countemp();
	}

	public List<String> getAllEmployeNamesJPQL() {
		return employeRepository.employeNames();

	}

	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		return employeRepository.getAllEmployeByEntreprisec(entreprise);
	}

	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);

	}
	public void deleteAllContratJPQL() {
		employeRepository.deleteAllContratJPQL();
	}

	public float getSalaireByEmployeIdJPQL(int employeId) {
		return employeRepository.getSalaireByEmployeIdJPQL(employeId);
	}

	public Double getSalaireMoyenByDepartementId(int departementId) {
		return employeRepository.getSalaireMoyenByDepartementId(departementId);
	}

	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {
		return timesheetRepository.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
	}

	public List<Employe> getAllEmployes() {
		return (List<Employe>) employeRepository.findAll();
	}

}
