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

    private SQLitePersonRepository persons;
    private SQLiteKeywordRepository keywords;
    private SQLiteSiteRepository sites;
    private SQLiteUserRepository users;
    private SQLiteAdminRepository admins;

    public SQLiteRepository( Context context, String dbName ) {
        super( context, dbName );

        this.persons = new SQLitePersonRepository( this );
        this.keywords = new SQLiteKeywordRepository( this );
        this.sites = new SQLiteSiteRepository( this );
        this.users = new SQLiteUserRepository( this );
        this.admins = new SQLiteAdminRepository( this );
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


    public void showRepositoryInfo() {

        System.out.println( "Table: " + TABLE_PERSONS + " содержит: " + persons.getPersonsCount() + " записей" );
        System.out.println( "Table: " + TABLE_KEYWORDS + " содержит: " + keywords.getKeywordsCount() + " записей" );
        System.out.println( "Table: " + TABLE_SITES + " содержит: " + sites.getSitesCount() + " записей" );
        System.out.println( "Table: " + TABLE_USERS + " содержит: " + users.getUsersCount() + " записей" );
        System.out.println( "Table: " + TABLE_ADMINS + " содержит: " + admins.getAdminsCount() + " записей" );

        for ( Person o : persons.getAllPersons() ) {
            System.out.println( "Table: " + TABLE_PERSONS + " : " + o.getId() + ", " + o.getName() );
        }

        for ( Keyword o : keywords.getAllKeywords() ) {
            Person person = persons.getPersonById( o.getPersonId() );
            System.out.println( "Table: " +
                    TABLE_KEYWORDS + " : " +
                    o.getId() + ", " + o.getName() + ", " +
                    person.getId() + ", " + person.getName() );
        }

        for ( Site o : sites.getAllSites() ) {
            System.out.println( "Table: " + TABLE_SITES + " : " + o.getId() + ", " + o.getName() );
        }

        for ( User o : users.getAllUsers() ) {
            System.out.println( "Table: " + TABLE_USERS + " : " +
                    o.getId() + ", " + o.getNickName() + ", " +
                    o.getLogin() + ", " + o.getPassword() );
        }

        for ( Admin o : admins.getAllAdmins() ) {
            System.out.println( "Table: " + TABLE_ADMINS + " : " +
                    o.getId() + ", " + o.getNickName() + ", " +
                    o.getLogin() + ", " + o.getPassword() );
        }

    }}
