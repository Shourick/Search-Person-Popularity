package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Keyword;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Person;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Site;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.IAdminRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.IKeywordRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.IPersonRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.ISiteRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.IUserRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.Admin;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.User;

/**
 * Класс для работы с базой SQLite для возможности тестирования бизнес-логики
 * пока создается Веб-сервис
 * <p>
 * Created by skubatko on 27/10/17.
 */

public class SQLiteRepository extends SQLiteHelper implements IPersonRepository, IKeywordRepository, ISiteRepository, IUserRepository, IAdminRepository {

    /**
     * Конструктор {@link SQLiteRepository}.
     *
     * @param context Контекст приложения
     */
    public SQLiteRepository( Context context ) {
        super( context );
    }

    /**
     * -------------------------------------------------------------------
     * РЕАЛИЗАЦИЯ КОНТРАКТА IDatabaseHelper / приципов CRUD для Repository
     * -------------------------------------------------------------------
     */


    /**
     * Persons
     */
    @Override
    public void addPerson( Person person ) {
        try ( SQLiteDatabase db = this.getWritableDatabase() ) {
            ContentValues contentValues = new ContentValues();
            contentValues.put( TABLE_PERSONS_FIELD_NAME, person.getName() );

            db.insert( TABLE_PERSONS, null, contentValues );
        }
    }

    @Override
    public Person getPersonById( int id ) {
        Person person = new Person();
        SQLiteDatabase db = this.getReadableDatabase();

        try ( Cursor cursorPersons = db.query(
                TABLE_PERSONS,                              // table
                new String[] { KEY_ID,
                        TABLE_PERSONS_FIELD_NAME },          // columns
                KEY_ID + " = ?",                            // columns WHERE
                new String[] { Integer.toString( id ) },         // values WHERE
                null,                                       // group by
                null,                                       // having
                null ) )                                     // order by
        {
            if ( cursorPersons.moveToFirst() ) {
                person.setId( Integer.parseInt( cursorPersons.getString( 0 ) ) );
                person.setName( cursorPersons.getString( 1 ) );
            }
        }
        return person;
    }

    @Override
    public Person getPersonByName( String name ) {
        Person person = new Person();
        SQLiteDatabase db = this.getReadableDatabase();

        try ( Cursor cursorPersons = db.query(
                TABLE_PERSONS,                              // table
                new String[] { KEY_ID,
                        TABLE_PERSONS_FIELD_NAME },          // columns
                TABLE_PERSONS_FIELD_NAME + " = ?",          // columns WHERE
                new String[] { name },                         // values WHERE
                null,                                       // group by
                null,                                       // having
                null ) )                                     // order by
        {
            if ( cursorPersons.moveToFirst() ) {
                person.setId( Integer.parseInt( cursorPersons.getString( 0 ) ) );
                person.setName( cursorPersons.getString( 1 ) );
            }
        }
        return person;
    }

    @Override
    public List<Person> getAllPersons() {
        List<Person> personList = new ArrayList<>();
        String personListQuery = "SELECT * FROM " + TABLE_PERSONS;
        SQLiteDatabase db = this.getReadableDatabase();

        try ( Cursor cursorPersons = db.rawQuery( personListQuery, null ) ) {
            if ( cursorPersons.moveToFirst() ) {
                do {
                    Person person = new Person();
                    person.setId( Integer.parseInt( cursorPersons.getString( 0 ) ) );
                    person.setName( cursorPersons.getString( 1 ) );
                    personList.add( person );
                } while ( cursorPersons.moveToNext() );
            }
        }

        return personList;
    }

    @Override
    public int getPersonsCount() {
        int count = 0;
        String countQuery = "SELECT * FROM " + TABLE_PERSONS;
        SQLiteDatabase db = this.getReadableDatabase();

        try ( Cursor cursorPersons = db.rawQuery( countQuery, null ) ) {
            count = cursorPersons.getCount();
        }
        return count;
    }

    @Override
    public int updatePerson( Person person ) {
        int result = 0;
        try ( SQLiteDatabase db = this.getWritableDatabase() ) {
            ContentValues contentValues = new ContentValues();
            contentValues.put( TABLE_PERSONS_FIELD_NAME, person.getName() );

            result = db.update( TABLE_PERSONS,
                    contentValues,
                    KEY_ID + " = ?",
                    new String[] { String.valueOf( person.getId() ) } );
        }
        return result;
    }

    @Override
    public void deletePerson( Person person ) {
        try ( SQLiteDatabase db = this.getWritableDatabase() ) {
            db.delete( TABLE_PERSONS,
                    KEY_ID + " = ?",
                    new String[] { String.valueOf( person.getId() ) } );
        }
    }

    @Override
    public void deleteAllPersons() {
        try ( SQLiteDatabase db = this.getWritableDatabase() ) {
            db.delete( TABLE_PERSONS, null, null );
        }
    }

    /**
     * Keywords
     */
    @Override
    public void addKeyword( Keyword keyword, int personId ) {
        try ( SQLiteDatabase db = this.getWritableDatabase() ) {
            ContentValues contentValues = new ContentValues();
            contentValues.put( TABLE_KEYWORDS_FIELD_NAME, keyword.getName() );
            contentValues.put( TABLE_KEYWORDS_FIELD_PERSON_ID, personId );

            db.insert( TABLE_KEYWORDS, null, contentValues );
        }

    }

    @Override
    public Keyword getKeyword( int id ) {
        Keyword keyword = new Keyword();
        SQLiteDatabase db = this.getReadableDatabase();

        try ( Cursor cursorKeywords = db.query(
                TABLE_KEYWORDS,                             // table
                new String[] { KEY_ID,
                        TABLE_KEYWORDS_FIELD_NAME,
                        TABLE_KEYWORDS_FIELD_PERSON_ID },    // columns
                KEY_ID + " = ?",                            // columns WHERE
                new String[] { Integer.toString( id ) },         // values WHERE
                null,                                       // group by
                null,                                       // having
                null ) )                                     // order by
        {
            if ( cursorKeywords.moveToFirst() ) {
                keyword.setId( Integer.parseInt( cursorKeywords.getString( 0 ) ) );
                keyword.setName( cursorKeywords.getString( 1 ) );
                keyword.setPersonId( Integer.parseInt( cursorKeywords.getString( 2 ) ) );
            }
        }
        return keyword;
    }

    @Override
    public List<Keyword> getPersonKeywords( int personId ) {
        List<Keyword> keywordList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        try ( Cursor cursorKeywords = db.query(
                TABLE_KEYWORDS,                                     // table
                new String[] { KEY_ID,
                        TABLE_KEYWORDS_FIELD_NAME,
                        TABLE_KEYWORDS_FIELD_PERSON_ID },            // columns
                TABLE_KEYWORDS_FIELD_PERSON_ID + " = ?",            // columns WHERE
                new String[] { Integer.toString( personId ) },           // values WHERE
                null,                                               // group by
                null,                                               // having
                null ) )                                             // order by
        {
            if ( cursorKeywords.moveToFirst() ) {
                do {
                    Keyword keyword = new Keyword();
                    keyword.setId( Integer.parseInt( cursorKeywords.getString( 0 ) ) );
                    keyword.setName( cursorKeywords.getString( 1 ) );
                    keyword.setPersonId( Integer.parseInt( cursorKeywords.getString( 2 ) ) );
                    keywordList.add( keyword );
                } while ( cursorKeywords.moveToNext() );
            }
        }
        return keywordList;
    }

    @Override
    public List<Keyword> getAllKeywords() {
        List<Keyword> keywordList = new ArrayList<>();
        String keywordListQuery = "SELECT * FROM " + TABLE_KEYWORDS;
        SQLiteDatabase db = this.getReadableDatabase();

        try ( Cursor cursorKeywords = db.rawQuery( keywordListQuery, null ) ) {
            if ( cursorKeywords.moveToFirst() ) {
                do {
                    Keyword keyword = new Keyword();
                    keyword.setId( Integer.parseInt( cursorKeywords.getString( 0 ) ) );
                    keyword.setName( cursorKeywords.getString( 1 ) );
                    keyword.setPersonId( Integer.parseInt( cursorKeywords.getString( 2 ) ) );
                    keywordList.add( keyword );
                } while ( cursorKeywords.moveToNext() );
            }
        }

        return keywordList;
    }

    @Override
    public int getKeywordsCount() {
        int count = 0;
        String countQuery = "SELECT * FROM " + TABLE_KEYWORDS;
        SQLiteDatabase db = this.getReadableDatabase();

        try ( Cursor cursorKeywords = db.rawQuery( countQuery, null ) ) {
            count = cursorKeywords.getCount();
        }
        return count;
    }

    @Override
    public int updateKeyword( Keyword keyword ) {
        int result = 0;
        try ( SQLiteDatabase db = this.getWritableDatabase() ) {
            ContentValues contentValues = new ContentValues();
            contentValues.put( TABLE_KEYWORDS_FIELD_NAME, keyword.getName() );

            result = db.update( TABLE_KEYWORDS,
                    contentValues,
                    KEY_ID + " = ?",
                    new String[] { String.valueOf( keyword.getId() ) } );
        }
        return result;
    }

    @Override
    public void deleteKeyword( Keyword keyword ) {
        try ( SQLiteDatabase db = this.getWritableDatabase() ) {
            db.delete( TABLE_KEYWORDS,
                    KEY_ID + " = ?",
                    new String[] { String.valueOf( keyword.getId() ) } );
        }
    }

    @Override
    public void deleteAllKeywords() {
        try ( SQLiteDatabase db = this.getWritableDatabase() ) {
            db.delete( TABLE_KEYWORDS, null, null );
        }
    }

    /**
     * Sites
     */
    @Override
    public void addSite( Site site ) {
        try ( SQLiteDatabase db = this.getWritableDatabase() ) {
            ContentValues contentValues = new ContentValues();
            contentValues.put( TABLE_SITES_FIELD_NAME, site.getName() );

            db.insert( TABLE_SITES, null, contentValues );
        }

    }

    @Override
    public Site getSite( int id ) {
        Site site = new Site();
        SQLiteDatabase db = this.getReadableDatabase();

        try ( Cursor cursorSites = db.query(
                TABLE_SITES,                              // table
                new String[] { KEY_ID,
                        TABLE_SITES_FIELD_NAME },     // columns
                KEY_ID,                                     // columns WHERE
                new String[] { Integer.toString( id ) },         // values WHERE
                null,                                       // group by
                null,                                       // having
                null ) )                                     // order by
        {
            if ( cursorSites.moveToFirst() ) {
                site.setId( Integer.parseInt( cursorSites.getString( 0 ) ) );
                site.setName( cursorSites.getString( 1 ) );
            }
        }
        return site;
    }

    @Override
    public List<Site> getAllSites() {
        List<Site> siteList = new ArrayList<>();
        String siteListQuery = "SELECT * FROM " + TABLE_SITES;
        SQLiteDatabase db = this.getReadableDatabase();

        try ( Cursor cursorSites = db.rawQuery( siteListQuery, null ) ) {
            if ( cursorSites.moveToFirst() ) {
                do {
                    Site site = new Site();
                    site.setId( Integer.parseInt( cursorSites.getString( 0 ) ) );
                    site.setName( cursorSites.getString( 1 ) );
                    siteList.add( site );
                } while ( cursorSites.moveToNext() );
            }
        }

        return siteList;
    }

    @Override
    public int getSitesCount() {
        int count = 0;
        String countQuery = "SELECT * FROM " + TABLE_SITES;
        SQLiteDatabase db = this.getReadableDatabase();

        try ( Cursor cursorSites = db.rawQuery( countQuery, null ) ) {
            count = cursorSites.getCount();
        }
        return count;
    }

    @Override
    public int updateSite( Site site ) {
        int result = 0;
        try ( SQLiteDatabase db = this.getWritableDatabase() ) {
            ContentValues contentValues = new ContentValues();
            contentValues.put( TABLE_SITES_FIELD_NAME, site.getName() );

            result = db.update( TABLE_SITES,
                    contentValues,
                    KEY_ID + " = ?",
                    new String[] { String.valueOf( site.getId() ) } );
        }
        return result;
    }

    @Override
    public void deleteSite( Site site ) {
        try ( SQLiteDatabase db = this.getWritableDatabase() ) {
            db.delete( TABLE_SITES,
                    KEY_ID + " = ?",
                    new String[] { String.valueOf( site.getId() ) } );
        }
    }

    @Override
    public void deleteAllSites() {
        try ( SQLiteDatabase db = this.getWritableDatabase() ) {
            db.delete( TABLE_SITES, null, null );
        }
    }

    /**
     * Users
     */
    @Override
    public void addUser( User user ) {
        try ( SQLiteDatabase db = this.getWritableDatabase() ) {
            ContentValues contentValues = new ContentValues();
            contentValues.put( TABLE_USERS_FIELD_NICKNAME, user.getNickName() );
            contentValues.put( TABLE_USERS_FIELD_LOGIN, user.getLogin() );
            contentValues.put( TABLE_USERS_FIELD_PASSWORD, user.getPassword() );

            db.insert( TABLE_USERS, null, contentValues );
        }

    }

    @Override
    public User getUser( int id ) {
        User user = new User();
        SQLiteDatabase db = this.getReadableDatabase();

        try ( Cursor cursorUsers = db.query(
                TABLE_USERS,                              // table
                new String[] { KEY_ID,
                        TABLE_USERS_FIELD_NICKNAME,
                        TABLE_USERS_FIELD_LOGIN,
                        TABLE_USERS_FIELD_PASSWORD },     // columns
                KEY_ID,                                   // columns WHERE
                new String[] { Integer.toString( id ) },  // values WHERE
                null,                                     // group by
                null,                                     // having
                null ) )                                  // order by
        {
            if ( cursorUsers.moveToFirst() ) {
                user.setId( Integer.parseInt( cursorUsers.getString( 0 ) ) );
                user.setNickName( cursorUsers.getString( 1 ) );
                user.setLogin( cursorUsers.getString( 2 ) );
                user.setPassword( cursorUsers.getString( 3 ) );
            }
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> siteList = new ArrayList<>();
        String userListQuery = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();

        try ( Cursor cursorUsers = db.rawQuery( userListQuery, null ) ) {
            if ( cursorUsers.moveToFirst() ) {
                do {
                    User user = new User();
                    user.setId( Integer.parseInt( cursorUsers.getString( 0 ) ) );
                    user.setNickName( cursorUsers.getString( 1 ) );
                    user.setLogin( cursorUsers.getString( 2 ) );
                    user.setPassword( cursorUsers.getString( 3 ) );
                    siteList.add( user );
                } while ( cursorUsers.moveToNext() );
            }
        }

        return siteList;
    }

    @Override
    public int getUsersCount() {
        int count = 0;
        String countQuery = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();

        try ( Cursor cursorUsers = db.rawQuery( countQuery, null ) ) {
            count = cursorUsers.getCount();
        }
        return count;
    }

    @Override
    public int updateUser( User user ) {
        int result = 0;
        try ( SQLiteDatabase db = this.getWritableDatabase() ) {
            ContentValues contentValues = new ContentValues();
            contentValues.put( TABLE_USERS_FIELD_NICKNAME, user.getNickName() );
            contentValues.put( TABLE_USERS_FIELD_LOGIN, user.getLogin() );
            contentValues.put( TABLE_USERS_FIELD_PASSWORD, user.getPassword() );

            result = db.update( TABLE_USERS,
                    contentValues,
                    KEY_ID + " = ?",
                    new String[] { String.valueOf( user.getId() ) } );
        }
        return result;
    }

    @Override
    public void deleteUser( User user ) {

        SQLiteDatabase dbReadable = this.getReadableDatabase();
        System.out.println( "Table SQL: " + TABLE_USERS_FIELD_NICKNAME + " = ? and " +
                TABLE_USERS_FIELD_LOGIN + " = ? and " +
                TABLE_USERS_FIELD_PASSWORD + " = ?" );
        try ( Cursor cursorUsers = dbReadable.query(
                TABLE_USERS,                                        // table
                new String[] { KEY_ID },                            // columns
                TABLE_USERS_FIELD_NICKNAME + " = ? and " +
                        TABLE_USERS_FIELD_LOGIN + " = ? and " +
                        TABLE_USERS_FIELD_PASSWORD + " = ?",         // columns WHERE
                new String[] { user.getNickName(), user.getLogin(), user.getPassword() },  // values WHERE
                null,                                     // group by
                null,                                     // having
                null ) )                                  // order by
        {
            if ( cursorUsers.moveToFirst() ) {
                String[] keyId = new String[] { cursorUsers.getString( 0 ) };
                try ( SQLiteDatabase dbWritable = this.getWritableDatabase() ) {
                    dbWritable.delete( TABLE_USERS,
                            KEY_ID + " = ?",
                            keyId
                    );
                }
            }
        }
    }

    @Override
    public void deleteAllUsers() {
        try ( SQLiteDatabase db = this.getWritableDatabase() ) {
            db.delete( TABLE_USERS, null, null );
        }
    }

    /**
     * Admins
     */
    @Override
    public void addAdmin( Admin admin ) {
        try ( SQLiteDatabase db = this.getWritableDatabase() ) {
            ContentValues contentValues = new ContentValues();
            contentValues.put( TABLE_ADMINS_FIELD_LOGIN, admin.getLogin() );
            contentValues.put( TABLE_ADMINS_FIELD_PASSWORD, admin.getPassword() );

            db.insert( TABLE_ADMINS, null, contentValues );
        }
    }

    @Override
    public Admin getAdmin( int id ) {
        Admin admin = new Admin();
        SQLiteDatabase db = this.getReadableDatabase();

        try ( Cursor cursorSites = db.query(
                TABLE_ADMINS,                              // table
                new String[] { KEY_ID,
                        TABLE_ADMINS_FIELD_LOGIN,
                        TABLE_ADMINS_FIELD_PASSWORD },     // columns
                KEY_ID,                                     // columns WHERE
                new String[] { Integer.toString( id ) },         // values WHERE
                null,                                       // group by
                null,                                       // having
                null ) )                                     // order by
        {
            if ( cursorSites.moveToFirst() ) {
                admin.setId( Integer.parseInt( cursorSites.getString( 0 ) ) );
                admin.setLogin( cursorSites.getString( 1 ) );
                admin.setPassword( cursorSites.getString( 2 ) );
            }
        }
        return admin;
    }

    @Override
    public List<Admin> getAllAdmins() {
        List<Admin> adminList = new ArrayList<>();
        String adminListQuery = "SELECT * FROM " + TABLE_ADMINS;
        SQLiteDatabase db = this.getReadableDatabase();

        try ( Cursor cursorAdmins = db.rawQuery( adminListQuery, null ) ) {
            if ( cursorAdmins.moveToFirst() ) {
                do {
                    Admin admin = new Admin();
                    admin.setId( Integer.parseInt( cursorAdmins.getString( 0 ) ) );
                    admin.setLogin( cursorAdmins.getString( 1 ) );
                    admin.setPassword( cursorAdmins.getString( 2 ) );
                    adminList.add( admin );
                } while ( cursorAdmins.moveToNext() );
            }
        }

        return adminList;
    }

    @Override
    public int getAdminsCount() {
        int count = 0;
        String countQuery = "SELECT * FROM " + TABLE_ADMINS;
        SQLiteDatabase db = this.getReadableDatabase();

        try ( Cursor cursorAdmins = db.rawQuery( countQuery, null ) ) {
            count = cursorAdmins.getCount();
        }
        return count;
    }

    @Override
    public int updateAdmin( Admin admin ) {
        int result = 0;
        try ( SQLiteDatabase db = this.getWritableDatabase() ) {
            ContentValues contentValues = new ContentValues();
            contentValues.put( TABLE_ADMINS_FIELD_LOGIN, admin.getLogin() );
            contentValues.put( TABLE_ADMINS_FIELD_PASSWORD, admin.getPassword() );

            result = db.update( TABLE_ADMINS,
                    contentValues,
                    KEY_ID + " = ?",
                    new String[] { String.valueOf( admin.getId() ) } );
        }
        return result;
    }

    @Override
    public void deleteAdmin( Admin admin ) {
        try ( SQLiteDatabase db = this.getWritableDatabase() ) {
            db.delete( TABLE_ADMINS,
                    KEY_ID + " = ?",
                    new String[] { String.valueOf( admin.getId() ) } );
        }
    }

    @Override
    public void deleteAllAdmins() {
        try ( SQLiteDatabase db = this.getWritableDatabase() ) {
            db.delete( TABLE_ADMINS, null, null );
        }
    }

    /**
     * Заполнение БД фейковыми значениями
     */

    public void initialize() {

        deleteAllPersons();
        addPerson( new Person( "Путин" ) );
        addPerson( new Person( "Медведев" ) );
        addPerson( new Person( "Жириновский" ) );

        deleteAllKeywords();
        addKeyword( new Keyword( "Путину" ), getPersonByName( "Путин" ).getId() );
        addKeyword( new Keyword( "Путина" ), getPersonByName( "Путин" ).getId() );
        addKeyword( new Keyword( "Путиным" ), getPersonByName( "Путин" ).getId() );
        addKeyword( new Keyword( "Медведеву" ), getPersonByName( "Медведев" ).getId() );
        addKeyword( new Keyword( "Медведева" ), getPersonByName( "Медведев" ).getId() );
        addKeyword( new Keyword( "Медведевым" ), getPersonByName( "Медведев" ).getId() );
        addKeyword( new Keyword( "Жириновскому" ), getPersonByName( "Жириновский" ).getId() );
        addKeyword( new Keyword( "Жириновского" ), getPersonByName( "Жириновский" ).getId() );
        addKeyword( new Keyword( "Жириновским" ), getPersonByName( "Жириновский" ).getId() );

        deleteAllSites();
        addSite( new Site( "http://gazeta.ru" ) );
        addSite( new Site( "http://yandex.ru" ) );
        addSite( new Site( "http://rbc.ru" ) );

        deleteAllUsers();
        addUser( new User( "u1", "user1", "pass1" ) );
        addUser( new User( "u2", "user2", "pass2" ) );
        addUser( new User( "u3", "user3", "pass3" ) );

        deleteAllAdmins();
        addAdmin( new Admin( "admin1", "pass1" ) );
        addAdmin( new Admin( "admin2", "pass2" ) );

    }

    /**
     * Выводит содержимое базы данных в лог системы для проверки
     */
    public void showInfo() {

        System.out.println( "Table: " + TABLE_PERSONS + " содержит: " + getPersonsCount() + " записей" );
        System.out.println( "Table: " + TABLE_KEYWORDS + " содержит: " + getKeywordsCount() + " записей" );
        System.out.println( "Table: " + TABLE_SITES + " содержит: " + getSitesCount() + " записей" );
        System.out.println( "Table: " + TABLE_USERS + " содержит: " + getUsersCount() + " записей" );
        System.out.println( "Table: " + TABLE_ADMINS + " содержит: " + getAdminsCount() + " записей" );

        for ( Person o : getAllPersons() ) {
            System.out.println( "Table: " + TABLE_PERSONS + " : " + o.getId() + ", " + o.getName() );
        }

        for ( Keyword o : getAllKeywords() ) {
            Person person = getPersonById( o.getPersonId() );
            System.out.println( "Table: " +
                    TABLE_KEYWORDS + " : " +
                    o.getId() + ", " + o.getName() + ", " +
                    person.getId() + ", " + person.getName() );
        }

        for ( Site o : getAllSites() ) {
            System.out.println( "Table: " + TABLE_SITES + " : " + o.getId() + ", " + o.getName() );
        }

        for ( User o : getAllUsers() ) {
            System.out.println( "Table: " + TABLE_USERS + " : " +
                    o.getId() + ", " + o.getNickName() + ", " +
                    o.getLogin() + ", " + o.getPassword() );
        }

        for ( Admin o : getAllAdmins() ) {
            System.out.println( "Table: " + TABLE_ADMINS + " : " +
                    o.getId() + ", " + o.getLogin() + ", " + o.getPassword() );
        }

    }

}
