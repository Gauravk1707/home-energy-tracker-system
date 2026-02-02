package com.example.ingestion_service.controller;

import com.example.ingestion_service.dto.EnergyUsageDto;
import com.example.ingestion_service.service.IngestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ingestion")
public class IngestionController {

    private final IngestionService ingestionService;

    public IngestionController(IngestionService ingestionService){
        this.ingestionService = ingestionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void ingestEnergyData(@RequestBody EnergyUsageDto energyUsageDto){
        ingestionService.ingestEnergyData(energyUsageDto);
    }
}
