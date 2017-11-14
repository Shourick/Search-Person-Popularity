package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Keyword;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Person;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Site;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.IRepositoryUtils;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.Admin;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.User;
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

    private SQLiteRepository repository;
    private SQLitePersonRepository personRepository;
    private SQLiteKeywordRepository keywordRepository;
    private SQLiteSiteRepository siteRepository;
    private SQLiteUserRepository userRepository;
    private SQLiteAdminRepository adminRepository;

    public SQLiteRepositoryUtils( SQLiteRepository repository ) {
        this.repository = repository;
        this.personRepository = repository.getPersonRepository();
        this.keywordRepository = repository.getKeywordRepository();
        this.siteRepository = repository.getSiteRepository();
        this.userRepository = repository.getUserRepository();
        this.adminRepository = repository.getAdminRepository();
    }

    @Override
    public void initializeRepository() {

        personRepository.deleteAllPersons();
        personRepository.addPerson( new Person( "Путин" ) );
        personRepository.addPerson( new Person( "Медведев" ) );
        personRepository.addPerson( new Person( "Жириновский" ) );

        keywordRepository.deleteAllKeywords();
        keywordRepository.addKeyword( new Keyword( "Путину" ), personRepository.getPersonByName( "Путин" ).getId() );
        keywordRepository.addKeyword( new Keyword( "Путина" ), personRepository.getPersonByName( "Путин" ).getId() );
        keywordRepository.addKeyword( new Keyword( "Путиным" ), personRepository.getPersonByName( "Путин" ).getId() );
        keywordRepository.addKeyword( new Keyword( "Медведеву" ), personRepository.getPersonByName( "Медведев" ).getId() );
        keywordRepository.addKeyword( new Keyword( "Медведева" ), personRepository.getPersonByName( "Медведев" ).getId() );
        keywordRepository.addKeyword( new Keyword( "Медведевым" ), personRepository.getPersonByName( "Медведев" ).getId() );
        keywordRepository.addKeyword( new Keyword( "Жириновскому" ), personRepository.getPersonByName( "Жириновский" ).getId() );
        keywordRepository.addKeyword( new Keyword( "Жириновского" ), personRepository.getPersonByName( "Жириновский" ).getId() );
        keywordRepository.addKeyword( new Keyword( "Жириновским" ), personRepository.getPersonByName( "Жириновский" ).getId() );

        siteRepository.deleteAllSites();
        siteRepository.addSite( new Site( "http://gazeta.ru" ) );
        siteRepository.addSite( new Site( "http://yandex.ru" ) );
        siteRepository.addSite( new Site( "http://rbc.ru" ) );

        userRepository.deleteAllUsers();
        userRepository.addUser( new User( "u1", "user1", "pass1" ) );
        userRepository.addUser( new User( "u2", "user2", "pass2" ) );
        userRepository.addUser( new User( "u3", "user3", "pass3" ) );

        adminRepository.deleteAllAdmins();
        adminRepository.addAdmin( new Admin( "admin1", "pass1" ) );
        adminRepository.addAdmin( new Admin( "admin2", "pass2" ) );

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
