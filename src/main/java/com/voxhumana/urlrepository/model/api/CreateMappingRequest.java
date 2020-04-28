package com.voxhumana.urlrepository.model.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.time.OffsetDateTime;

public class CreateMappingRequest {

    @JsonCreator
    public CreateMappingRequest(
            @JsonProperty("source") String source,
            @JsonProperty("value") String value,
            @JsonProperty("expiresOn") OffsetDateTime expiresOn) {
        this.source = source;
        this.value = value;
        this.expiresOn = expiresOn;
    }

    @NotBlank(message = "Source is required.")
    public String source;

    @NotBlank(message = "Value is required.")
    public String value;

    @Future(message = "ExpiresOn must be in the future")
    public OffsetDateTime expiresOn;

    public String getSource() {
        return source;
    }

    public String getValue() {
        return value;
    }

    public OffsetDateTime getExpiresOn() {
        return expiresOn;
    }
}
