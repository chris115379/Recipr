package de.androidbytes.recipr.data.repository.domain;

import de.androidbytes.recipr.data.entity.UserEntity;
import de.androidbytes.recipr.data.entity.mapper.UserMapper;
import de.androidbytes.recipr.data.repository.data.UserDataRepository;
import de.androidbytes.recipr.domain.model.User;
import de.androidbytes.recipr.domain.repository.UserRepository;
import rx.Observable;

import javax.inject.Inject;

public class UserDomainRepository implements UserRepository {

    @Inject
    UserDataRepository userRepository;

    @Inject
    UserMapper userMapper;

    @Inject
    public UserDomainRepository () {
    }

    @Override
    public Observable<User> getLoggedInUser() {
        UserEntity userEntity = userRepository.findLoggedInUser();

        if (userEntity != null) {
            return Observable.just(userMapper.map(userEntity));
        } else {
            return Observable.empty();
        }
    }

    @Override
    public Observable<User> loginUser(User user) {
        UserEntity userEntity = userRepository.loginUser(new UserEntity(user.getId(), user.getGoogleId(), user.getName(), user.getImageUrl(), user.isLoggedIn()));

        if (userEntity != null) {
            return Observable.just(userMapper.map(userEntity));
        } else {
            return Observable.empty();
        }
    }
}
