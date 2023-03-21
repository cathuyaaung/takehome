package com.example.takehome.services;

import com.example.takehome.graphql.GraphQLRequest;
import com.example.takehome.graphql.GraphQLResponse;
import com.example.takehome.graphql.GraphqlSchemaReaderUtil;
import com.example.takehome.models.ContinentDTO;
import com.example.takehome.models.ContinentResponseDTO;
import com.example.takehome.models.CountryDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CountryService {

    @Value("${countries.graphql.endpoint}")
    private String API_URL;
    private final OkHttpClient client = new OkHttpClient();
    public List<ContinentResponseDTO> getCountries(List<String> codes) throws IOException {

        HashMap<String, Object> variables = new HashMap<>();
        variables.put("codes",codes);

        // Create GraphQL Request
        GraphQLRequest request = new GraphQLRequest();
        request.setQuery(GraphqlSchemaReaderUtil.getSchemaFromFileName("schema"));
        request.setVariables(variables);

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(new ObjectMapper().writeValueAsString(request), mediaType);
        Request httpRequest = new Request.Builder()
                .url(API_URL)
                .post(body)
                .build();

        Response httpResponse = client.newCall(httpRequest).execute();
        String responseStr = httpResponse.body().string();
        log.info(responseStr);
        GraphQLResponse response = new ObjectMapper().readValue(responseStr, GraphQLResponse.class);

        // Cast Response into List<CountryDTO>
        List<LinkedHashMap<String, Object>> data = (List<LinkedHashMap<String, Object>>) response.getData().get("countries");
        List<CountryDTO> countries = new ArrayList<>();
        for (LinkedHashMap<String, Object> country : data) {
            CountryDTO dto = new CountryDTO();
            dto.setCode((String) country.get("code"));
            LinkedHashMap<String, Object> continentMap = (LinkedHashMap<String, Object>) country.get("continent");
            ContinentDTO continentDTO = new ContinentDTO();
            continentDTO.setName((String)continentMap.get("name"));

            List<CountryDTO> continentCountryDTOs = new ArrayList<>();
            List<LinkedHashMap<String, String>> continentCountries = (List<LinkedHashMap<String, String>>) continentMap.get("countries");
            for (LinkedHashMap<String, String> continentCountry : continentCountries) {
                CountryDTO continentCountryDTO = new CountryDTO();
                continentCountryDTO.setCode(continentCountry.get("code"));
                continentCountryDTOs.add(continentCountryDTO);
            }
            continentDTO.setCountries(continentCountryDTOs);
            dto.setContinent(continentDTO);
            countries.add(dto);
        }

        return convertCountriesIntoContinents(countries);
    }

    private List<ContinentResponseDTO> convertCountriesIntoContinents(List<CountryDTO> countries) {
        HashMap<String, ContinentResponseDTO> continentMap = new HashMap<>();
        for (CountryDTO country: countries) {
            if (!continentMap.keySet().contains(country.getContinent().getName())) {
                ContinentResponseDTO continent = new ContinentResponseDTO();
                List<String> otherCountries = country.getContinent().getCountries()
                        .stream()
                        .map(CountryDTO::getCode)
                        .collect(Collectors.toList());
                continent.setName(country.getContinent().getName());
                continent.setOtherCountries(otherCountries);
                continentMap.put(country.getContinent().getName(), continent);
            }
            ContinentResponseDTO continent = continentMap.get(country.getContinent().getName());
            List<String> countryCodes = continent.getCountries();
            if (countryCodes == null)
                countryCodes = new ArrayList<>();
            countryCodes.add(country.getCode());
            continent.setCountries(countryCodes);

            List<String> otherCountries = continent.getOtherCountries();
            otherCountries.remove(country.getCode());
            continent.setOtherCountries(otherCountries);

        }

        return continentMap.values().stream().toList();
    }
}
