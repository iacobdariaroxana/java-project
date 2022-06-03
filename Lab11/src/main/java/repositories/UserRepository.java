package repositories;

import models.User;
import org.springframework.context.annotation.Bean;

public class UserRepository extends AbstractRepository<User, Integer> {
    public UserRepository(String name){
        super(name);
    }
}