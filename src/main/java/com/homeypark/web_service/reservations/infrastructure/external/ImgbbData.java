package com.homeypark.web_service.reservations.infrastructure.external;


import com.fasterxml.jackson.annotation.JsonProperty;

public record ImgbbData(
        String id,
        String title,
        @JsonProperty("url_viewer") String urlViewer,
        String url,
        @JsonProperty("display_url") String displayUrl,
        String width,
        String height,
        String size,
        String time,
        String expiration,
        ImgbbImageData image,
        ImgbbThumbData thumb,
        ImgbbMediumData medium,
        @JsonProperty("delete_url") String deleteUrl
) {
}
