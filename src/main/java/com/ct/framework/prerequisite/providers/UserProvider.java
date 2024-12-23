package com.ct.framework.prerequisite.providers;


import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.ct.framework.aut.pojo.User;

public class UserProvider implements Provider<User> {

    @Inject
    @Named("username")
    public String username;

    @Inject
    @Named("password")
    public String password;

    @Override
    public User get() {
        return new User(username,password);
    }
}
