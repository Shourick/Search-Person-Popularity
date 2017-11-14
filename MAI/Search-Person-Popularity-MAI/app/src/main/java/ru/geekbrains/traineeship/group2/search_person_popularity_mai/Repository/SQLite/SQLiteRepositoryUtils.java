package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite;

import android.content.Context;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Keyword;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Person;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Site;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.IRepositoryUtils;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.Admin;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.User;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.Admins.RESTfulAdminsLoader;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.Keywords.RESTfulKeywordsLoader;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.Persons.RESTfulPersonsLoader;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.Sites.RESTfulSitesLoader;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.Users.RESTfulUsersLoader;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.DataRepositories.SQLiteKeywordRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.DataRepositories.SQLitePersonRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.DataRepositories.SQLiteSiteRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.PlayerRepositories.SQLiteAdminRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.PlayerRepositories.SQLiteUserRepository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Constants.TABLE_ADMINS;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Constants.TABLE_KEYWORDS;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Constants.TABLE_PERSONS;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Constants.TABLE_SITES;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Constants.TABLE_USERS;

/**
 * Created by skubatko on 08/11/17
 */

public class SQLiteRepositoryUtils implements IRepositoryUtils {

    private SQLitePersonRepository personRepository;
    private SQLiteKeywordRepository keywordRepository;
    private SQLiteSiteRepository siteRepository;
    private SQLiteUserRepository userRepository;
    private SQLiteAdminRepository adminRepository;

    public SQLiteRepositoryUtils( SQLiteRepository repository ) {
        this.personRepository = repository.getPersonRepository();
        this.keywordRepository = repository.getKeywordRepository();
        this.siteRepository = repository.getSiteRepository();
        this.userRepository = repository.getUserRepository();
        this.adminRepository = repository.getAdminRepository();
    }

    @Override
    public void initializeRepository(Context context) {

        personRepository.deleteAllPersons();
        new RESTfulPersonsLoader(context).execute();

        keywordRepository.deleteAllKeywords();
//        new RESTfulKeywordsLoader(context).execute();
//
//        siteRepository.deleteAllSites();
//        new RESTfulSitesLoader(context).execute();
//
//        userRepository.deleteAllUsers();
//        new RESTfulUsersLoader(context).execute();
//
//        adminRepository.deleteAllAdmins();
//        new RESTfulAdminsLoader(context).execute();

    }

    @Override
    public void showRepositoryInfo() {

        System.out.println( "Table: " + TABLE_PERSONS + " содержит: " + personRepository.getPersonsCount() + " записей" );
        System.out.println( "Table: " + TABLE_KEYWORDS + " содержит: " + keywordRepository.getKeywordsCount() + " записей" );
        System.out.println( "Table: " + TABLE_SITES + " содержит: " + siteRepository.getSitesCount() + " записей" );
        System.out.println( "Table: " + TABLE_USERS + " содержит: " + userRepository.getUsersCount() + " записей" );
        System.out.println( "Table: " + TABLE_ADMINS + " содержит: " + adminRepository.getAdminsCount() + " записей" );

        for ( Person o : personRepository.getAllPersons() ) {
            System.out.println( "Table: " + TABLE_PERSONS + " : " + o.getId() + ", " + o.getName() );
        }

        for ( Keyword o : keywordRepository.getAllKeywords() ) {
            Person person = personRepository.getPersonById( o.getPersonId() );
            System.out.println( "Table: " +
                    TABLE_KEYWORDS + " : " +
                    o.getId() + ", " + o.getName() + ", " +
                    person.getId() + ", " + person.getName() );
        }

        for ( Site o : siteRepository.getAllSites() ) {
            System.out.println( "Table: " + TABLE_SITES + " : " + o.getId() + ", " + o.getName() );
        }

        for ( User o : userRepository.getAllUsers() ) {
            System.out.println( "Table: " + TABLE_USERS + " : " +
                    o.getId() + ", " + o.getNickName() + ", " +
                    o.getLogin() + ", " + o.getPassword() );
        }

        for ( Admin o : adminRepository.getAllAdmins() ) {
            System.out.println( "Table: " + TABLE_ADMINS + " : " +
                    o.getId() + ", " + o.getLogin() + ", " + o.getPassword() );
        }

    }
}
