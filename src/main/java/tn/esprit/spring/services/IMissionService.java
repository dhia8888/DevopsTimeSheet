package tn.esprit.spring.services;

import tn.esprit.spring.entities.Mission;

import java.util.Optional;

public interface IMissionService {
    public int add(Mission mission) ;
    public Mission update(Mission mission);
    public void delete(int missionId);
    public Optional<Mission> getById(int missionId);
    public Iterable<Mission> getAll();
}
