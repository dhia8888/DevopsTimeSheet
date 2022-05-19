package tn.esprit.spring.services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.repository.MissionRepository;

import java.util.Optional;

public class MissionServiceImpl implements IMissionService{

    private MissionRepository missionRepository;
    private static final Logger l = LogManager.getLogger(TimesheetServiceImpl.class);

    @Override
    public int add(Mission mission) {
        missionRepository.save(mission);
        l.info("Ajoute de la mission----------"+mission.getId());
        return mission.getId();
    }

    @Override
    public Mission update(Mission mission) {
        return missionRepository.save(mission);
    }

    @Override
    public void delete(int missionId) {
        missionRepository.deleteById(missionId);
    }

    @Override
    public Optional<Mission> getById(int missionId) {
        return missionRepository.findById(missionId);
    }

    @Override
    public Iterable<Mission> getAll() {
        return missionRepository.findAll();
    }
}
