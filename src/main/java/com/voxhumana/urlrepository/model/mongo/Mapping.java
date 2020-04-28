package com.voxhumana.urlrepository.model.mongo;

import org.springframework.data.annotation.Id;

import java.time.OffsetDateTime;

public class Mapping {
    @Id
    public String source;

    public String value;

    public OffsetDateTime createdOn;

    public OffsetDateTime lastUpdated;

    public OffsetDateTime expiresOn;

    public Mapping() {}

    public Mapping(String source, String value, OffsetDateTime expiresOn) {
        this.source = source;
        this.value = value;
        this.createdOn = OffsetDateTime.now();
        this.lastUpdated = createdOn;
        this.expiresOn = expiresOn;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public OffsetDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(OffsetDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public OffsetDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(OffsetDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public OffsetDateTime getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(OffsetDateTime expiresOn) {
        this.expiresOn = expiresOn;
    }
}
