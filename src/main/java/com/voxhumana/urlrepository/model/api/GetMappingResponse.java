package com.voxhumana.urlrepository.model.api;

public class GetMappingResponse {

    public GetMappingResponse(String url) {
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
