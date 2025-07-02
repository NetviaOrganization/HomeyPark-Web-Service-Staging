package com.homeypark.web_service.profiles.application.internal.queryservices;

import com.homeypark.web_service.profiles.domain.model.aggregates.Profile;
import com.homeypark.web_service.profiles.domain.model.queries.GetAllProfilesQuery;
import com.homeypark.web_service.profiles.domain.model.queries.GetProfileByUserIdQuery;
import com.homeypark.web_service.profiles.domain.model.valueobject.UserId;
import com.homeypark.web_service.profiles.domain.services.ProfileQueryService;
import com.homeypark.web_service.profiles.infrastructure.persistence.repositories.jpa.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileQueryServiceImpl implements ProfileQueryService {
    private final ProfileRepository profileRepository;

    public ProfileQueryServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Optional<Profile> handle(GetProfileByUserIdQuery query)
    {
        return profileRepository.findProfileByUserId(new UserId(query.userId()));
    }

    @Override
    public List<Profile> handle(GetAllProfilesQuery query) {
        return profileRepository.findAll();
    }


}
