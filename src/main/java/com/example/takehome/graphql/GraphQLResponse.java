package com.example.takehome.graphql;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;

@Getter
@Setter
@NoArgsConstructor
public class GraphQLResponse {
    private LinkedHashMap<String, Object> data;
    private Object errors;
}