package com.laborator11.network.services;

import models.Friend;
import models.User;
import org.springframework.stereotype.Service;
import repositories.AbstractRepository;
import repositories.FriendsRepository;
import repositories.UserRepository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class RelationshipService {
    private FriendsRepository dataFriend = new FriendsRepository("Friend");
    private AbstractRepository<User, Integer> dataUser = new UserRepository("User");

    public boolean insertRelationship(int user1, int user2) {
        if (!(dataUser.findById(user1) == null) && !(dataUser.findById(user2) == null) && dataFriend.findRelationship(user1, user2) == null) {
            dataFriend.create(new Friend(user1, user2));
//            dataFriend.create((new Friend(user2, user1)));
            return true;
        }
        return false;
    }

    public List<Friend> getRelationships(){
        return dataFriend.findAll();
    }

    public List<Friend> getUserRelationships(int user1){
        return dataFriend.findByUser1(user1);
    }

    public List<User> getFamous(int k){
        List<Friend> relationships = dataFriend.findAll();
        Map<User, Integer> numberOfFriendships = new HashMap<>();
        relationships.stream().forEach(friend -> {
            User user = new User(dataUser.findById(Math.toIntExact(friend.getUser1())).getUsername());
            if (numberOfFriendships.containsKey(user)) {
                numberOfFriendships.put(user, numberOfFriendships.get(user) + 1);
            } else {
                numberOfFriendships.put(user, 1);
            }
        });

        List<Map.Entry<User, Integer>> result = numberOfFriendships
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .toList();
        List<User> finalResult = new LinkedList<>();
        for (int i = result.size()-1; i >= result.size() - k; i--) {
            finalResult.add(result.get(i).getKey());
        }
        return finalResult;
    }
}
