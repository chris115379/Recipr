package de.androidbytes.recipr.domain.repository;

import de.androidbytes.recipr.domain.model.User;
import rx.Observable;

/**
 * Created by Christoph on 17.05.2016.
 */
public interface UserRepository {
    Observable<User> getLoggedInUser();
    Observable<User> loginUser(User user);
}
