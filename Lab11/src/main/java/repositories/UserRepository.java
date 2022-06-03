package repositories;

import models.User;

public class UserRepository extends AbstractRepository<User, Integer> {
    public UserRepository(String name){
        super(name);
    }
}