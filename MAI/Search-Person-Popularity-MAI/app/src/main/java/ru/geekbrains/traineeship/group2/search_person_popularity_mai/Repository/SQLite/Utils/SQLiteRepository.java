package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils;

import android.content.Context;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Keyword;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Person;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Site;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.IRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.Admin;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.User;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.DataRepositories.SQLiteKeywordRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.DataRepositories.SQLitePersonRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.DataRepositories.SQLiteSiteRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.PlayerRepositories.SQLiteAdminRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.PlayerRepositories.SQLiteUserRepository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_ADMINS;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_KEYWORDS;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_PERSONS;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_SITES;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_USERS;

/**
 * Класс для работы с базой SQLite в качестве буфера обмена с API
 * <p>
 * Created by skubatko on 27/10/17.
 */
public class SQLiteRepository extends SQLiteHelper implements IRepository {

    private SQLitePersonRepository mPersons;
    private SQLiteKeywordRepository mKeywords;
    private SQLiteSiteRepository mSites;
    private SQLiteUserRepository mUsers;
    private SQLiteAdminRepository mAdmins;

    public SQLiteRepository( Context context, String dbName ) {
        super( context, dbName );

        this.mPersons = new SQLitePersonRepository( this );
        this.mKeywords = new SQLiteKeywordRepository( this );
        this.mSites = new SQLiteSiteRepository( this );
        this.mUsers = new SQLiteUserRepository( this );
        this.mAdmins = new SQLiteAdminRepository( this );
    }

    @Override
    public SQLitePersonRepository getPersonRepository() {
        return mPersons;
    }

    @Override
    public SQLiteKeywordRepository getKeywordRepository() {
        return mKeywords;
    }

    @Override
    public SQLiteSiteRepository getSiteRepository() {
        return mSites;
    }

    @Override
    public SQLiteUserRepository getUserRepository() {
        return mUsers;
    }

    @Override
    public SQLiteAdminRepository getAdminRepository() {
        return mAdmins;
    }


    // TODO: метод удалить после отладки
    public void showRepositoryInfo() {

        System.out.println( "Table: " + TABLE_PERSONS + " содержит: " + mPersons.getPersonsCount() + " записей" );
        System.out.println( "Table: " + TABLE_KEYWORDS + " содержит: " + mKeywords.getKeywordsCount() + " записей" );
        System.out.println( "Table: " + TABLE_SITES + " содержит: " + mSites.getSitesCount() + " записей" );
        System.out.println( "Table: " + TABLE_USERS + " содержит: " + mUsers.getUsersCount() + " записей" );
        System.out.println( "Table: " + TABLE_ADMINS + " содержит: " + mAdmins.getAdminsCount() + " записей" );

        for ( Person o : mPersons.getAllPersons() ) {
            System.out.println( "Table: " + TABLE_PERSONS + " : " + o.getId() + ", " + o.getName() );
        }

        for ( Keyword o : mKeywords.getAllKeywords() ) {
            Person person = mPersons.getPersonById( o.getPersonId() );
            System.out.println( "Table: " +
                    TABLE_KEYWORDS + " : " +
                    o.getId() + ", " + o.getName() + ", " +
                    person.getId() + ", " + person.getName() );
        }

        for ( Site o : mSites.getAllSites() ) {
            System.out.println( "Table: " + TABLE_SITES + " : " + o.getId() + ", " + o.getName() );
        }

        for ( User o : mUsers.getAllUsers() ) {
            System.out.println( "Table: " + TABLE_USERS + " : " +
                    o.getId() + ", " + o.getNickName() + ", " +
                    o.getLogin() + ", " + o.getPassword() );
        }

        for ( Admin o : mAdmins.getAllAdmins() ) {
            System.out.println( "Table: " + TABLE_ADMINS + " : " +
                    o.getId() + ", " + o.getNickName() + ", " +
                    o.getLogin() + ", " + o.getPassword() );
        }

    }
}
