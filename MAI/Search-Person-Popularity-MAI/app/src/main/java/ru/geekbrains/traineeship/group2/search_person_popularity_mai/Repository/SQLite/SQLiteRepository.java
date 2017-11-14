package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite;

import android.content.Context;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.IRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.DataRepositories.SQLiteKeywordRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.DataRepositories.SQLitePersonRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.DataRepositories.SQLiteSiteRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.PlayerRepositories.SQLiteAdminRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.PlayerRepositories.SQLiteUserRepository;

/**
 * Класс для работы с базой SQLite в качестве буфера обмена с API
 * <p>
 * Created by skubatko on 27/10/17.
 */
public class SQLiteRepository extends SQLiteHelper implements IRepository {

    private SQLitePersonRepository persons;
    private SQLiteKeywordRepository keywords;
    private SQLiteSiteRepository sites;
    private SQLiteUserRepository users;
    private SQLiteAdminRepository admins;
    private SQLiteRepositoryUtils repositoryUtils;

    /**
     * Конструктор {@link SQLiteRepository}.
     *
     * @param context Контекст приложения
     */
    public SQLiteRepository( Context context ) {
        super( context );

        this.persons = new SQLitePersonRepository( this );
        this.keywords = new SQLiteKeywordRepository( this );
        this.sites = new SQLiteSiteRepository( this );
        this.users = new SQLiteUserRepository( this );
        this.admins = new SQLiteAdminRepository( this );
        this.repositoryUtils = new SQLiteRepositoryUtils( this );
    }

    @Override
    public SQLitePersonRepository getPersonRepository() {
        return persons;
    }

    @Override
    public SQLiteKeywordRepository getKeywordRepository() {
        return keywords;
    }

    @Override
    public SQLiteSiteRepository getSiteRepository() {
        return sites;
    }

    @Override
    public SQLiteUserRepository getUserRepository() {
        return users;
    }

    @Override
    public SQLiteAdminRepository getAdminRepository() {
        return admins;
    }

    @Override
    public SQLiteRepositoryUtils getRepositoryUtils() {
        return repositoryUtils;
    }
}
