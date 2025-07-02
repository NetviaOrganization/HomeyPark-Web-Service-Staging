package com.homeypark.web_service.reservations.infrastructure.external;

public record ImgbbThumbData(
        String filename,
        String name,
        String mime,
        String extension,
        String url
) {
}
