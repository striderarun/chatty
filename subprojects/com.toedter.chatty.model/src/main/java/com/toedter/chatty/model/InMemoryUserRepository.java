/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepository implements UserRepository {
    ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    @Override
    public List<User> getAll() {
        return new ArrayList(users.values());
    }

    @Override
    public User getUserById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null.");
        }

        User user = users.get(id);
        if (user == null) {
            throw new IllegalArgumentException("user with id '" + id + "' not found.");
        }

        return user;
    }

    @Override
    public User createUser(User user) {
        if (user == null || user.getId() == null || "".equals(user.getId().trim())) {
            throw new IllegalArgumentException("user must have a non empty, non null id that is not used by any other user");
        }

        if (users.get(user.getId()) != null) {
            throw new IllegalArgumentException("user id '" + user.getId() + "' already exists.");
        }

        users.putIfAbsent(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        // TODO write test and implement then
        return null;
    }

    @Override
    public void deleteUserById(String id) {
        getUserById(id);
        users.remove(id);
    }

    @Override
    public void deleteAll() {
        users.clear();
    }

    @Override
    public long getSize() {
        return users.size();
    }
}
