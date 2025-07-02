package com.homeypark.web_service.reservations.infrastructure.external;

public record ImgbbImageData(
        String filename,
        String name,
        String mime,
        String extension,
        String url
) {
}
