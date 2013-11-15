package com.qsoft.ondio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileResponse
{
    @JsonProperty("data")
    Profile data;

    public Profile getData()
    {
        return data;
    }
}
