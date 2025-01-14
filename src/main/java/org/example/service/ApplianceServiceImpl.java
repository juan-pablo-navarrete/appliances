package org.example.service;

import org.example.dto.ApplianceDTO;
import org.example.repository.ApplianceRepository;
import com.google.inject.Inject;
import java.util.List;

public class ApplianceServiceImpl implements ApplianceService{

    private final ApplianceRepository repository;

    @Inject
    public ApplianceServiceImpl(ApplianceRepository applianceRepository){
        this.repository = applianceRepository;
    }

    public List<ApplianceDTO> findAll() {
        List<ApplianceDTO> applianceList = repository.findAll();
        return applianceList;
    }
    @Override
    public ApplianceDTO findById(int id) {
        ApplianceDTO applianceId = repository.findById(id);
        return applianceId;
    }

    public boolean deleteById(int id) {
        return repository.deleteById(id);
    }
}
