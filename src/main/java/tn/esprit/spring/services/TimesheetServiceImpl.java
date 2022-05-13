package tn.esprit.spring.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class TimesheetServiceImpl implements ITimesheetService {

	private static final Logger l = LogManager.getLogger(TimesheetServiceImpl.class);

	@Autowired
	MissionRepository missionRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;
	@Autowired
	EmployeRepository employeRepository;
	
	public int ajouterMission(Mission mission) {
		missionRepository.save(mission);
		l.info("Ajoute de la mission----------"+mission.getId());
		return mission.getId();
	}
    
	public void affecterMissionADepartement(int missionId, int depId) {
		Optional<Mission> m = missionRepository.findById(missionId);
		Optional<Departement> d = deptRepoistory.findById(depId);
		Departement departement = new Departement();
		Mission mission = new Mission();
		if (m.isPresent()) {
			mission = m.get();
			l.info("mission exist");
		}else {
			l.warn("mission n'exist pas ");
		}
		if (d.isPresent()) {
			departement = d.get();
			l.info("department exist");
		}else {
			l.warn("department n'exist pas ");
		}
		mission.setDepartement(departement);
		missionRepository.save(mission);
		if (l.isDebugEnabled()) {
			l.debug(String.format("mission %s ajoute au departement %s", mission.getName(), departement.getName()));
		}
	}

	public void ajouterTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin) {
		l.info(" ajoute Timesheet----------------------------");
		TimesheetPK timesheetPK = new TimesheetPK();
		timesheetPK.setDateDebut(dateDebut);
		timesheetPK.setDateFin(dateFin);
		timesheetPK.setIdEmploye(employeId);
		timesheetPK.setIdMission(missionId);
		Timesheet timesheet = new Timesheet();
		timesheet.setTimesheetPK(timesheetPK);
		timesheet.setValide(false); //par defaut non valide
		if (!(timesheetRepository.save(timesheet).isValide())) {
			l.warn("ajoute  Timesheet n'est pas réussit ");
		}
	}

	
	public void validerTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin, int validateurId) {
		l.info("-- valider Timesheet --");
		Optional<Employe> validateurList = employeRepository.findById(validateurId);
		Optional<Mission> missionList = missionRepository.findById(missionId);
		Employe employe = new Employe();
		if (validateurList.isPresent()) {
			employe = validateurList.get();
			if (!employe.getRole().equals(Role.CHEF_DEPARTEMENT)) {
				l.warn("l'employe doit etre chef de departement pour valider Timesheet !");
				return;
			}
		}
		//verifier s'il est le chef de departement de la mission en question
		boolean chefDeLaMission = false;
		for (Departement dep : employe.getDepartements()) {
			if (missionList.isPresent()) {
				Mission mission = missionList.get();
				if (dep.getId() == mission.getDepartement().getId()) {
					chefDeLaMission = true;
					break;
				}
			}
		}
		if (!chefDeLaMission) {
			l.warn("l'employe doit etre chef de departement de la mission");
			return;
		}

		TimesheetPK timesheetPK = new TimesheetPK(missionId, employeId, dateDebut, dateFin);
		Timesheet timesheet = timesheetRepository.findBytimesheetPK(timesheetPK);
		timesheet.setValide(true);
		if (l.isDebugEnabled()) {
			l.debug(String.format("timesheet %s missionId au employeId %s", missionId,employeId));
		}
		//Comment Lire une date de la base de données
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		l.info("dateDebut : " + dateFormat.format(timesheet.getTimesheetPK().getDateDebut()));
	}

	
	public List<Mission> findAllMissionByEmployeJPQL(int employeId) {
		return timesheetRepository.findAllMissionByEmployeJPQL(employeId);
	}

	
	public List<Employe> getAllEmployeByMission(int missionId) {
		return timesheetRepository.getAllEmployeByMission(missionId);
	}

}
