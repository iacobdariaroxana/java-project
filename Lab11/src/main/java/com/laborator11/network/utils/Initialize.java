package com.laborator11.network.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import org.apache.ibatis.jdbc.ScriptRunner;
import singleton.Database;
import singleton.MyEntityManagerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.StoredProcedureQuery;

public class Initialize {
    public static void runScript(String path){
        ScriptRunner sr = new ScriptRunner(Database.getConnection());
        Reader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sr.runScript(reader);
    }

    public static void populate(String function) {
        EntityManager em = MyEntityManagerFactory.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        StoredProcedureQuery query = em.createStoredProcedureQuery(function);
        query.executeUpdate();
        transaction.commit();
    }

    public static void main(String[] args) {
        runScript("data.sql");
//        populate("insert_users");
//        populate("insert_friends");
//        populate("insert_messages");
    }
}
