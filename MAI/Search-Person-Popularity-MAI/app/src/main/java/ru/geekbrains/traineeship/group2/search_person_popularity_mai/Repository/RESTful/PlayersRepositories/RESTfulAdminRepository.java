package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.PlayersRepositories;

import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Players.IAdminRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.Admin;

/**
 * Created by skubatko on 13/11/17
 */

public class RESTfulAdminRepository implements IAdminRepository{
    @Override
    public void addAdmin( Admin admin ) {

    }

    @Override
    public Admin getAdmin( int id ) {
        return null;
    }

    @Override
    public List getAllAdmins() {
        return null;
    }

    @Override
    public int getAdminsCount() {
        return 0;
    }

    @Override
    public int updateAdmin( Admin admin ) {
        return 0;
    }

    @Override
    public void deleteAdmin( Admin admin ) {

    }

    @Override
    public void deleteAllAdmins() {

    }
}
