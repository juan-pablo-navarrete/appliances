package org.example.dao;

import lombok.NoArgsConstructor;
import org.example.commons.CsvReader;
import org.example.dto.ApplianceDTO;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class ApplianceCsvDAOImpl implements ApplianceDAO {
    private final String FILE = "/appliances/appliances.csv";
    private static final char SEPARATOR = ',';

    @Override
    public List<ApplianceDTO> findAll() {
        List<ApplianceDTO> applianceList = new ArrayList<>();
        try {
            CsvReader.getRecords(FILE, SEPARATOR)
                    .forEach(csvRecord -> {
                        ApplianceDTO appliance = ApplianceDTO.builder()
                                .name(csvRecord.get("NAME"))
                                .id(Integer.parseInt(csvRecord.get("ID")))
                                .watts(Integer.parseInt(csvRecord.get("WATTS")))
                                .build();

                        applianceList.add(appliance);
                    });
        }catch (Exception exception) {
            throw new IllegalArgumentException("Error reading CSV", exception);
        }
        return applianceList;
    }

    @Override
    public ApplianceDTO findById(int id) {
        List<ApplianceDTO> allAppliance = this.findAll();
        for (ApplianceDTO appliance: allAppliance){
            if (appliance.getId() == id) {
                return appliance;
            }
        }
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        throw new UnsupportedOperationException("Delete operation is not supported in ApplianceCsvDAOImpl");
    }


    @Override
    public boolean supports(Class<?> selectedCass) {
        return this.getClass().isAssignableFrom(selectedCass);
    }

}
