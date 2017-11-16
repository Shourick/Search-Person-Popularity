package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.DataRepositories;

import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Site;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Data.ISiteRepository;

/**
 * Created by skubatko on 13/11/17
 */

public class RESTfulSiteRepository implements ISiteRepository{
    @Override
    public void addSite( Site site ) {

    }

    @Override
    public Site getSite( int id ) {
        return null;
    }

    @Override
    public List getAllSites() {
        return null;
    }

    @Override
    public int getSitesCount() {
        return 0;
    }

    @Override
    public int updateSite( Site site ) {
        return 0;
    }

    @Override
    public void deleteSite( Site site ) {

    }

    @Override
    public void deleteAllSites() {

    }
}
