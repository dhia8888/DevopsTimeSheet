package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Contrat;
import java.util.Optional;
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
		return employeRepository.getEmployeByEmailAndPassword(login, password);
	}

	@Override
	public int addOrUpdateEmploye(Employe employe) {
		employeRepository.save(employe);
		return employe.getId();
	}


	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		Optional<Employe> e = employeRepository.findById(employeId);
		Employe employe = new Employe();
		//Employe employe = employeRepository.findById(employeId).get();
		if (e.isPresent()){
			employe = e.get();
			employe.setEmail(email);
			employeRepository.save(employe);

		}


	}

	@Transactional
	public void affecterEmployeADepartement(int employeId, int depId) {
		Optional<Departement> d = deptRepoistory.findById(depId);
		Optional<Employe> e = employeRepository.findById(employeId);
		Employe employeManagedEntity = new Employe();
		Departement depManagedEntity = new Departement();


		if(depManagedEntity.getEmployes() == null){
			if(e.isPresent()){
				employeManagedEntity = e.get();
				List<Employe> employes = new ArrayList<>();
				employes.add(employeManagedEntity);


			if(d.isPresent()){
				depManagedEntity = d.get();
				depManagedEntity.setEmployes(employes);


			}}



		}else{

			depManagedEntity.getEmployes().add(employeManagedEntity);
		}

		// à ajouter?
		deptRepoistory.save(depManagedEntity);

	}
	@Transactional
	public void desaffecterEmployeDuDepartement(int employeId, int depId)
	{
		Optional<Departement> d = deptRepoistory.findById(depId);
		Departement dep = new Departement();
		if(d.isPresent()){
			dep =d.get();

		}


		int employeNb = dep.getEmployes().size();
		for(int index = 0; index < employeNb; index++){
			if(dep.getEmployes().get(index).getId() == employeId){
				dep.getEmployes().remove(index);
				break;//a revoir
			}
		}
	}

	// Tablesapce (espace disque)

	public int ajouterContrat(Contrat contrat) {
		contratRepoistory.save(contrat);
		return contrat.getReference();
	}

	public void affecterContratAEmploye(int contratId, int employeId) {


		Contrat contratManagedEntity = new Contrat();
		Employe employeManagedEntity = new Employe();
		Optional<Contrat> c = contratRepoistory.findById(contratId);
		Optional<Employe> e = employeRepository.findById(employeId);



		//Contrat contratManagedEntity = contratRepoistory.findById(contratId).get();
		//Employe employeManagedEntity = employeRepository.findById(employeId).get();
		if(c.isPresent() && e.isPresent())
		{
			contratManagedEntity = c.get();
			employeManagedEntity = e.get();
			contratManagedEntity.setEmploye(employeManagedEntity);
			contratRepoistory.save(contratManagedEntity);


		}



	}

	public String getEmployePrenomById(int employeId) {
		Optional<Employe> e = employeRepository.findById(employeId);
		Employe employeManagedEntity = new Employe();
		if(e.isPresent())
		{
			employeManagedEntity = e.get();
		}
		//Employe employeManagedEntity = employeRepository.findById(employeId).get();
		return employeManagedEntity.getPrenom();
	}

	public void deleteEmployeById(int employeId)
	{
		Optional<Employe> e = employeRepository.findById(employeId);
		Employe employe = new Employe();
		if(e.isPresent())
		{
			employe = e.get();
		}
		//Employe employe = employeRepository.findById(employeId).get();

		//Desaffecter l'employe de tous les departements
		//c'est le bout master qui permet de mettre a jour
		//la table d'association
		for(Departement dep : employe.getDepartements()){
			dep.getEmployes().remove(employe);
		}

		employeRepository.delete(employe);
	}

	public void deleteContratById(int contratId) {
		Optional<Contrat> c = contratRepoistory.findById(contratId);
		Contrat contratManagedEntity = new Contrat();
		if ( c.isPresent())
		{
			contratManagedEntity = c.get();

		}
		contratRepoistory.delete(contratManagedEntity);

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
