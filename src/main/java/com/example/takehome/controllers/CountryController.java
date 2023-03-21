package com.example.takehome.controllers;


import com.example.takehome.models.ContinentResponseDTO;

import com.example.takehome.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping("/countries")
    public List<ContinentResponseDTO> getCountries(@RequestParam List<String> codes) throws IOException {
        return countryService.getCountries(codes);
    }

}
