package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Person;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.IRepositoryUtils;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.DataRepositories.RESTfulPersonRepository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Constants.TABLE_PERSONS;

/**
 * Created by skubatko on 13/11/17
 */

public class RESTfulRepositoryUtils implements IRepositoryUtils {

    private RESTfulPersonRepository personRepository;

    public RESTfulRepositoryUtils( RESTfulRepository repository ) {
        this.personRepository = repository.getPersonRepository();
    }

    @Override
    public void initializeRepository() {

    }

    @Override
    public void showRepositoryInfo() {
        System.out.println( "Table: " + TABLE_PERSONS + " содержит: " + personRepository.getPersonsCount() + " записей" );
//        System.out.println( "Table: " + TABLE_KEYWORDS + " содержит: " + keywordRepository.getKeywordsCount() + " записей" );
//        System.out.println( "Table: " + TABLE_SITES + " содержит: " + siteRepository.getSitesCount() + " записей" );
//        System.out.println( "Table: " + TABLE_USERS + " содержит: " + userRepository.getUsersCount() + " записей" );
//        System.out.println( "Table: " + TABLE_ADMINS + " содержит: " + adminRepository.getAdminsCount() + " записей" );

//        for ( Person o : personRepository.getAllPersons() ) {
//            System.out.println( "Table: " + TABLE_PERSONS + " : " + o.getId() + ", " + o.getName() );
//        }

//        for ( Keyword o : keywordRepository.getAllKeywords() ) {
//            Person person = personRepository.getPersonById( o.getPersonId() );
//            System.out.println( "Table: " +
//                    TABLE_KEYWORDS + " : " +
//                    o.getId() + ", " + o.getName() + ", " +
//                    person.getId() + ", " + person.getName() );
//        }
//
//        for ( Site o : siteRepository.getAllSites() ) {
//            System.out.println( "Table: " + TABLE_SITES + " : " + o.getId() + ", " + o.getName() );
//        }
//
//        for ( User o : userRepository.getAllUsers() ) {
//            System.out.println( "Table: " + TABLE_USERS + " : " +
//                    o.getId() + ", " + o.getNickName() + ", " +
//                    o.getLogin() + ", " + o.getPassword() );
//        }
//
//        for ( Admin o : adminRepository.getAllAdmins() ) {
//            System.out.println( "Table: " + TABLE_ADMINS + " : " +
//                    o.getId() + ", " + o.getLogin() + ", " + o.getPassword() );
//        }

    }
}
