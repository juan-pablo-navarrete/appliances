package org.example.repository;

import com.google.inject.Inject;
import org.example.commons.PropertiesReader;
import org.example.dao.ApplianceDAO;
import org.example.dto.ApplianceDTO;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class ApplianceRepository {
    private final Set<ApplianceDAO> applianceDAOSet;

    @Inject
    public ApplianceRepository(Set<ApplianceDAO> applianceDAOSet) {
        this.applianceDAOSet = applianceDAOSet;
    }

    public List<ApplianceDTO> findAll() throws IOException {
        return selectDAO().findAll();
    }

    public ApplianceDTO findById(int id) throws IOException {
        return selectDAO().finById(id);
    }

    public boolean deleteById(int id) throws IOException {
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
