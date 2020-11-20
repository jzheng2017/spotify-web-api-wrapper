package spotify.api.interfaces;

import spotify.models.users.User;

public interface UserApi {
    User getCurrentUser();

    User getUserById(String userId);
}
