package repositories;

import models.Friend;

import javax.persistence.NoResultException;
import java.util.List;

public class FriendsRepository extends AbstractRepository<Friend, Integer> {
    public FriendsRepository(String name) {
        super(name);
    }

    public Friend findRelationship(int user1, int user2){
        String query = this.getClassName() + ".findRelationship";
        Friend friend = null;
        try{
            friend = (Friend) em.createNamedQuery(query)
                    .setParameter(1, user1).
                    setParameter(2, user2)
                    .getSingleResult();
            return friend;
        }
        catch(NoResultException exception){
            return null;
        }
    }

    public List<Friend> findByUser1(int user1) {
        String query = this.getClassName() + ".findByUser1";
        List<Friend> friends = null;
        try{
            friends = em.createNamedQuery(query)
                    .setParameter(1, user1)
                    .getResultList();
            return friends;
        }
        catch(NoResultException exception){
            return null;
        }
    }

    public void deleteByUserId(int id){
        beginTransaction();
        String query = this.getClassName() + ".deleteByUserId";
        em.createNamedQuery(query)
                .setParameter(1, id).executeUpdate();
        commit();
    }
}
