package org.example.service;

import org.example.dto.ApplianceDTO;

import java.io.IOException;
import java.util.List;

public interface ApplianceService {
    List<ApplianceDTO> findAll() throws IOException;

    ApplianceDTO findById(int id) throws IOException;

    boolean deleteById(int id) throws IOException;
}
