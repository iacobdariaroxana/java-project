package repositories;

import models.Message;

import javax.persistence.NoResultException;
import java.util.List;

public class MessageRepository extends AbstractRepository<Message, Integer>{
    public MessageRepository(String name) {
        super(name);
    }

    public List<Message> findByHashtag(String hashtag)
    {
        String query = this.getClassName() + ".findByHashtag";
        List<Message> messages = null;
        try{
            messages = em.createNamedQuery(query)
                    .setParameter(1, hashtag)
                    .getResultList();
            return messages;
        }
        catch(NoResultException exception){
            return null;
        }
    }

    public List<Message> findByFromId(int fromId)
    {
        String query = this.getClassName() + ".findByFromId";
        List<Message> messages = null;
        try{
            messages = em.createNamedQuery(query)
                    .setParameter(1, fromId)
                    .getResultList();
            return messages;
        }
        catch(NoResultException exception){
            return null;
        }
    }

    public List<Message> findByToId(int toId)
    {
        String query = this.getClassName() + ".findByToId";
        List<Message> messages = null;
        try{
            messages = em.createNamedQuery(query)
                    .setParameter(1, toId)
                    .getResultList();
            return messages;
        }
        catch(NoResultException exception){
            return null;
        }
    }

    public List<Message> findByType(int type)
    {
        String query = this.getClassName() + ".findByType";
        List<Message> messages = null;
        try{
            messages = em.createNamedQuery(query)
                    .setParameter(1, type)
                    .getResultList();
            return messages;
        }
        catch(NoResultException exception){
            return null;
        }
    }

    public List<Message> findById(int id)
    {
        String query = this.getClassName() + ".findById";
        List<Message> messages = null;
        try{
            messages = em.createNamedQuery(query)
                    .setParameter(1, id)
                    .getResultList();
            return messages;
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

    public void updateMessage(int id, String updatedMessage){
        beginTransaction();
        String query = this.getClassName() + ".updateMessage";
        em.createNamedQuery(query)
                .setParameter(1, id)
                .setParameter(2, updatedMessage)
                .executeUpdate();
        commit();
    }
}
