package com.laborator11.network.services;

import models.Friend;
import models.User;
import org.springframework.stereotype.Service;
import repositories.AbstractRepository;
import repositories.FriendsRepository;
import repositories.UserRepository;

import java.util.*;

@Service
public class RelationshipService {
    private FriendsRepository dataFriend = new FriendsRepository("Friend");
    private AbstractRepository<User, Integer> dataUser = new UserRepository("User");

    public boolean insertRelationship(int user1, String user2) {
        if (dataUser.findByUsername(user2).isEmpty()) return false;
        if (dataUser.findById(user1) != null && !(dataUser.findByUsername(user2).isEmpty()) && dataFriend.findRelationship(user1, dataUser.findByUsername(user2).get(0).getId()) == null) {
            dataFriend.create(new Friend(user1, dataUser.findByUsername(user2).get(0).getId()));
            return true;
        }
        return false;
    }

    public List<Friend> getRelationships() {
        return dataFriend.findAll();
    }

    public List<String> getUserRelationships(int user1) {
        List<String> friends = new ArrayList<>();
        for (Friend friend : dataFriend.findByUser1(user1)) {
            friends.add(dataUser.findById(friend.getUser2()).getUsername());
        }
        return friends;
    }

    public List<String> getFamous(int k) {
        List<Friend> relationships = dataFriend.findAll();
        Map<User, Integer> numberOfFriendships = new HashMap<>();
        relationships.stream().forEach(friend -> {
            User user = new User(dataUser.findById(Math.toIntExact(friend.getUser1())).getUsername());
            if (numberOfFriendships.containsKey(user)) {
                numberOfFriendships.put(user, numberOfFriendships.get(user) + 1);
            } else {
                numberOfFriendships.put(user, 1);
            }
            User userFriend = new User(dataUser.findById(Math.toIntExact(friend.getUser2())).getUsername());
            if (numberOfFriendships.containsKey(userFriend)) {
                numberOfFriendships.put(userFriend, numberOfFriendships.get(userFriend) + 1);
            } else {
                numberOfFriendships.put(userFriend, 1);
            }
        });

        List<Map.Entry<User, Integer>> result = numberOfFriendships
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .toList();
        if (result.size() < k) {
            k = result.size();
        }

        List<User> finalResult = new LinkedList<>();
        for (int i = result.size() - 1; i >= result.size() - k; i--) {
            finalResult.add(result.get(i).getKey());
        }
        List<String> users = new ArrayList<>();
        for (User user : finalResult) {
            users.add(user.getUsername());
        }
        return users;
    }
}
