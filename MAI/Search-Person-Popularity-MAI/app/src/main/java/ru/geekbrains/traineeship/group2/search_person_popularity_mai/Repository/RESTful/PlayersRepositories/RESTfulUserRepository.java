package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.PlayersRepositories;

import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Players.IUserRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.User;

/**
 * Created by skubatko on 13/11/17
 */

public class RESTfulUserRepository implements IUserRepository{
    @Override
    public void addUser( User user ) {

    }

    @Override
    public User getUser( int id ) {
        return null;
    }

    @Override
    public List getAllUsers() {
        return null;
    }

    @Override
    public int getUsersCount() {
        return 0;
    }

    @Override
    public int updateUser( User user ) {
        return 0;
    }

    @Override
    public void deleteUser( User user ) {

    }

    @Override
    public void deleteAllUsers() {

    }
}
