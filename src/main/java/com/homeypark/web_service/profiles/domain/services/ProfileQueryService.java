package com.homeypark.web_service.profiles.domain.services;

import com.homeypark.web_service.profiles.domain.model.aggregates.Profile;
import com.homeypark.web_service.profiles.domain.model.queries.GetAllProfilesQuery;
import com.homeypark.web_service.profiles.domain.model.queries.GetProfileByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface ProfileQueryService {
    Optional<Profile> handle(GetProfileByUserIdQuery query);

    List<Profile> handle(GetAllProfilesQuery query);
}
