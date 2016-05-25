package de.androidbytes.recipr.data.entity.mapper;

import de.androidbytes.recipr.data.entity.UserEntity;
import de.androidbytes.recipr.domain.model.User;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class UserMapper {

    @Inject public UserMapper() {}

    public User map(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getGoogleId(),
                userEntity.getName(),
                userEntity.getImageUrl(),
                userEntity.isLoggedIn()
        );
    }
}
