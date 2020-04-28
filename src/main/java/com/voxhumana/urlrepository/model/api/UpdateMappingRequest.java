package com.voxhumana.urlrepository.model.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.time.OffsetDateTime;

public class UpdateMappingRequest {

    @JsonCreator
    public UpdateMappingRequest(
            @JsonProperty("source") String source,
            @JsonProperty("value") String value,
            @JsonProperty("expiresOn") OffsetDateTime expiresOn) {
        this.source = source;
        this.value = value;
        this.expiresOn = expiresOn;
    }

    @NotBlank
    public String source;

    public String value;

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
