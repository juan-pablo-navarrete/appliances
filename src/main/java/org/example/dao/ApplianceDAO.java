package org.example.dao;
import org.example.dto.ApplianceDTO;
import java.util.List;

public interface ApplianceDAO {
    List<ApplianceDTO> findAll();

    ApplianceDTO findById(int id);

    boolean deleteById(int id);

    boolean supports(Class<?> selectedCass);

}
