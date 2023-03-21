package com.example.takehome.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContinentResponseDTO {

    private String name;
    private List<String> countries;
    private List<String> otherCountries;
}
