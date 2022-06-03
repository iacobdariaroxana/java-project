package com.laborator11.network.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import models.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import repositories.AbstractRepository;
import repositories.FriendsRepository;
import repositories.MessageRepository;
import repositories.UserRepository;
import java.util.List;

@Component
@Service
public class UserService {

    private AbstractRepository<User, Integer> dataUser = new UserRepository("User");
    private FriendsRepository dataFriend = new FriendsRepository("Friend");

    private MessageRepository dataMessage = new MessageRepository("Message");

    public User findUser(int id) {
        return dataUser.findById(id);
    }

    public List<User> getUsers() {
        return dataUser.findAll();
    }

    public String createUserObject(User user) {
        if(dataUser.findByUsername(user.getUsername()).isEmpty()){
            dataUser.create(new User(user.getUsername(), getEncodedPassword(user.getPassword())));
            return "success";
        }
        return "username already exists";
    }

    public boolean updateUser(int id, String name) {
        User user = dataUser.findById(id);
        if (user == null) {
            return false;
        }
        dataUser.updateUsername(user.getId(), name);
        user.setUsername(name);
        return true;
    }

    public boolean deleteUser(int id) {
        User user = dataUser.findById(id);
        if (user == null) {
            return false;
        }
        dataUser.delete(id);
        dataFriend.delete(Math.toIntExact(user.getId()));
        dataMessage.deleteByUserId(user.getId());
        return true;
    }

    private String getEncodedPassword(String password){
        String encodedPassword= BCrypt.withDefaults().hashToString(12, password.toCharArray());
//        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), bcryptHashString);
        return encodedPassword;
    }
}
