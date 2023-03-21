package com.example.takehome.graphql;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
public class GraphQLRequest {
    private String query;
    private HashMap<String, Object> variables;
}