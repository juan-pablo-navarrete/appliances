package org.example.repository;

import com.google.inject.Inject;
import org.example.commons.PropertiesReader;
import org.example.dao.ApplianceDAO;
import org.example.dto.ApplianceDTO;

import java.util.List;
import java.util.Set;

public class ApplianceRepository {
    private final Set<ApplianceDAO> applianceDAOSet;

    @Inject
    public ApplianceRepository(Set<ApplianceDAO> applianceDAOSet) {
        this.applianceDAOSet = applianceDAOSet;
    }

    public List<ApplianceDTO> findAll()  {
        return selectDAO().findAll();
    }

    public ApplianceDTO findById(int id)  {
        return selectDAO().findById(id);
    }

    public boolean deleteById(int id) {
        return selectDAO().deleteById(id);
    }

    private ApplianceDAO selectDAO() {
        Class<?> selectedClass = PropertiesReader.getPropertyClass("appliances.dao.selector-class");
        for(ApplianceDAO dao: this.applianceDAOSet) {
            if(dao.supports(selectedClass))
                return dao;
        }
        throw new IllegalArgumentException("No such DAO");
    }
}
