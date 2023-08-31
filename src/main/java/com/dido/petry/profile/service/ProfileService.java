package com.dido.petry.profile.service;

import com.dido.petry.profile.dto.ProfileDTO;
import com.dido.petry.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository repository;
    @Transient
    public Long saveProfile(ProfileDTO dto) {
        return repository.save(dto.toEntity()).getId();
    }
}
