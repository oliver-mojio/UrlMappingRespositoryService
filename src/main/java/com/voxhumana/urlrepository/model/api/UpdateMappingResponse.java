package com.voxhumana.urlrepository.model.api;

public class UpdateMappingResponse {
    public UpdateMappingResponse(String url) {
        this.url = url;
    }

    public String url;

    public String getUrl() {
        return url;
    }
}
