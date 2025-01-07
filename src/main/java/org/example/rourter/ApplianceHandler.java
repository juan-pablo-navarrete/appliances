package org.example.rourter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.ApplianceDTO;
import org.example.service.ApplianceService;

import com.google.inject.Inject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ApplianceHandler {
    private final ObjectMapper objectMapper;
    private final ApplianceService applianceService;
    @Inject
    public ApplianceHandler(ObjectMapper objectMapper, ApplianceService applianceService){
        this.applianceService = applianceService;
        this.objectMapper =objectMapper;
    }

    public void findAll(PrintWriter writer) throws IOException{
        List<ApplianceDTO> applianceList = applianceService.findAll();
        String applianceListJson = objectMapper.writeValueAsString(applianceList);

        writer.println(applianceListJson);
    }

    public void findById(int applianceid, PrintWriter output) throws IOException {
        ApplianceDTO appliance = applianceService.findById(applianceid);
        String applianceJson = objectMapper.writeValueAsString(appliance);

        output.println(applianceid);
    }

    public void deleteById(int applianceId, PrintWriter output) throws IOException {
        boolean isDeleted = applianceService.deleteById(applianceId);

        if (isDeleted) {
            output.println("{\"message\": \"Appliance with id " + applianceId + " deleted successfully.\"}");
        } else {
            output.println("{\"message\": \"Appliance with id " + applianceId + " not found or could not be deleted.\"}");
        }
    }
}
