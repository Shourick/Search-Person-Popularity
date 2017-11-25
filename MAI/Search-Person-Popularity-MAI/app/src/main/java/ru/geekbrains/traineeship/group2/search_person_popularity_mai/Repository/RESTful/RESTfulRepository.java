package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Data.IKeywordRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Data.IPersonRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Data.ISiteRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.IRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Players.IAdminRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Players.IUserRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.DataRepositories.RESTfulKeywordRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.DataRepositories.RESTfulPersonRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.DataRepositories.RESTfulSiteRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.PlayersRepositories.RESTfulAdminRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.PlayersRepositories.RESTfulUserRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.DataRepositories.SQLiteKeywordRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.DataRepositories.SQLitePersonRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.DataRepositories.SQLiteSiteRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.PlayerRepositories.SQLiteAdminRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.PlayerRepositories.SQLiteUserRepository;

/**
 * Created by skubatko on 15/11/17
 */

public class RESTfulRepository implements IRepository {
    private RESTfulPersonRepository mPersons;
    private RESTfulKeywordRepository mKeywords;
    private RESTfulSiteRepository mSites;
    private RESTfulUserRepository mUsers;
    private RESTfulAdminRepository mAdmins;

    public RESTfulRepository(  ) {
        this.mPersons = new RESTfulPersonRepository();
        this.mKeywords = new RESTfulKeywordRepository();
        this.mSites = new RESTfulSiteRepository();
        this.mUsers = new RESTfulUserRepository();
        this.mAdmins = new RESTfulAdminRepository();
    }

    @Override
    public RESTfulPersonRepository getPersonRepository() {
        return mPersons;
    }

    @Override
    public RESTfulKeywordRepository getKeywordRepository() {
        return mKeywords;
    }

    @Override
    public RESTfulSiteRepository getSiteRepository() {
        return mSites;
    }

    @Override
    public RESTfulUserRepository getUserRepository() {
        return mUsers;
    }

    @Override
    public RESTfulAdminRepository getAdminRepository() {
        return mAdmins;
    }
}
