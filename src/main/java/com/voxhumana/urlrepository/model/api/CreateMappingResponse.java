package com.voxhumana.urlrepository.model.api;

public class CreateMappingResponse {

    public CreateMappingResponse(String url) {
        this.url = url;
    }

    public String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
