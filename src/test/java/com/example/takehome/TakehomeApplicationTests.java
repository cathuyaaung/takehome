package com.example.takehome;

import com.example.takehome.models.ContinentResponseDTO;
import com.example.takehome.services.CountryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TakehomeApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;

    @Test
    public void testGetContinentsByCountryCodes() throws Exception {
        List<String> countryCodes = Arrays.asList("CA", "US");
        ContinentResponseDTO continent = new ContinentResponseDTO();
        continent.setName("North America");
        continent.setCountries(Arrays.asList("CA", "US"));
        continent.setOtherCountries(Arrays.asList("AG", "AI", "AW", "BB"));

        when(countryService.getCountries(countryCodes)).thenReturn(Arrays.asList(continent));

        mockMvc.perform(get("/api/countries?codes=CA,US")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\"CA\", \"US\"]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("North America"))
                .andExpect(jsonPath("$[0].countries[0]").value("CA"))
                .andExpect(jsonPath("$[0].countries[1]").value("US"))
                ;
    }
}


