package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с базой SQLite для возможности тестирования бизнес-логики
 * пока создается Веб-сервис
 * <p>
 * Created by skubatko on 27/10/17.
 */

public class SQLiteDatabaseHandler extends SQLiteOpenHelper implements IDatabaseHandler {

    /**
     * Имя файла базы данных
     */
    private static final String DATABASE_NAME = "searchPersonPopularity.db";

    /**
     * Версия базы данных. При изменении схемы увеличить на единицу
     */
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_PERSONS = "persons";
    private static final String TABLE_KEYWORDS = "keywords";
    private static final String TABLE_SITES = "sites";

    private static final String KEY_ID = "_id";

    private static final String TABLE_PERSONS_FIELD_NAME = "name";

    private static final String TABLE_KEYWORDS_FIELD_NAME = "name";
    private static final String TABLE_KEYWORDS_FIELD_PERSON_ID = "person_id";

    private static final String TABLE_SITES_FIELD_NAME = "name";

    /**
     * -----------------------------
     * РЕАЛИЗАЦИЯ ИНТЕРФЕЙСА SQLite
     * -----------------------------
     */

    /**
     * Конструктор {@link SQLiteDatabaseHandler}.
     *
     * @param context Контекст приложения
     */
    public SQLiteDatabaseHandler( Context context ) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    /**
     * Вызывается при создании базы данных
     */
    @Override
    public void onCreate( SQLiteDatabase db ) {
        createPersonsTable( db );
        createKeywordsTable( db );
        createSitesTable( db );

        Log.d( "onCreate", "Таблицы базы данных созданы" );
    }

    /**
     * Создание таблицы Persons
     */
    private void createPersonsTable( SQLiteDatabase db ) {
        /**
         * SQL запрос для создания таблицы Persons:
         *  CREATE TABLE persons (
         *      _id     INTEGER     PRIMARY KEY AUTOINCREMENT,
         *      name    TEXT        NOT NULL
         *  );
         */
        String CREATE_PERSONS_TABLE = "CREATE TABLE " + TABLE_PERSONS + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_PERSONS_FIELD_NAME + " TEXT NOT NULL" +
                ");";
        db.execSQL( CREATE_PERSONS_TABLE );
    }

    /**
     * Создание таблицы Keywords
     */
    private void createKeywordsTable( SQLiteDatabase db ) {
        /**
         * SQL запрос для создания таблицы Keywords:
         *  CREATE TABLE keywords (
         *      _id     INTEGER     PRIMARY KEY AUTOINCREMENT,
         *      name    TEXT        NOT NULL,
         *      person_id   INTEGER     NOT NULL,
         *      FOREIGN KEY(person_id) REFERENCES persons(_id)
         *  );
         */
        String CREATE_KEYWORDS_TABLE = "CREATE TABLE " + TABLE_KEYWORDS + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_KEYWORDS_FIELD_NAME + " TEXT NOT NULL, " +
                TABLE_KEYWORDS_FIELD_PERSON_ID + " INTEGER NOT NULL, " +
                "FOREIGN KEY(" + TABLE_KEYWORDS_FIELD_PERSON_ID + ") REFERENCES " + TABLE_PERSONS + "(" + KEY_ID + ")" +
                ");";

        db.execSQL( CREATE_KEYWORDS_TABLE );
    }

    /**
     * Создание таблицы Sites
     */
    private void createSitesTable( SQLiteDatabase db ) {
        /**
         * SQL запрос для создания таблицы Sites:
         *  CREATE TABLE sites (
         *      _id     INTEGER     PRIMARY KEY AUTOINCREMENT,
         *      name    TEXT        NOT NULL
         *  );
         */
        String CREATE_SITES_TABLE = "CREATE TABLE " + TABLE_SITES + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_SITES_FIELD_NAME + " TEXT NOT NULL" +
                ");";

        db.execSQL( CREATE_SITES_TABLE );
    }

    /**
     * Вызывается при обновлении схемы базы данных
     * Увеличение версии схемы
     */
    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_PERSONS );
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_KEYWORDS );
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_SITES );

        onCreate( db );
    }

    /**
     * Вызывается при обновлении схемы базы данных
     * Уменьшение версии схемы
     */
    @Override
    public void onDowngrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_PERSONS );
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_KEYWORDS );
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_SITES );
        Log.d( "onDowngrade", "Таблицы базы данных удалены" );

        onCreate( db );
    }

    /**
     * Заполнение БД фейковыми значениями
     */
    public void initializeDatabase() {

        deleteAllPersons();
        AddPerson( new Person( "Путин" ) );
        AddPerson( new Person( "Медведев" ) );
        AddPerson( new Person( "Жириновский" ) );

        deleteAllKeywords();
        AddKeyword( new Keyword( "Путину" ), getPersonByName( "Путин" ).getId() );
        AddKeyword( new Keyword( "Путина" ), getPersonByName( "Путин" ).getId() );
        AddKeyword( new Keyword( "Путиным" ), getPersonByName( "Путин" ).getId() );
        AddKeyword( new Keyword( "Медведеву" ), getPersonByName( "Медведев" ).getId() );
        AddKeyword( new Keyword( "Медведева" ), getPersonByName( "Медведев" ).getId() );
        AddKeyword( new Keyword( "Медведевым" ), getPersonByName( "Медведев" ).getId() );
        AddKeyword( new Keyword( "Жириновскому" ), getPersonByName( "Жириновский" ).getId() );
        AddKeyword( new Keyword( "Жириновского" ), getPersonByName( "Жириновский" ).getId() );
        AddKeyword( new Keyword( "Жириновским" ), getPersonByName( "Жириновский" ).getId() );

        deleteAllSites();
        AddSite( new Site( "http://gazeta.ru" ) );
        AddSite( new Site( "http://yandex.ru" ) );
        AddSite( new Site( "http://rbc.ru" ) );

    }

    /**
     * Выводит содержимое базы данных в лог системы для проверки
     */
    public void showDatabaseInfo() {

        System.out.println( "Table: " + TABLE_PERSONS + " содержит: " + getPersonsCount() + " записей" );
        System.out.println( "Table: " + TABLE_KEYWORDS + " содержит: " + getKeywordsCount() + " записей" );
        System.out.println( "Table: " + TABLE_SITES + " содержит: " + getSitesCount() + " записей" );

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

    }

    /**
     * -------------------------------------------------------------------
     * РЕАЛИЗАЦИЯ КОНТРАКТА IDatabaseHandler / приципов CRUD для данных БД
     * -------------------------------------------------------------------
     */
    @Override
    public void AddPerson( Person person ) {
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

    @Override
    public void AddKeyword( Keyword keyword, int personId ) {
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
                        TABLE_KEYWORDS_FIELD_PERSON_ID,
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

    @Override
    public void AddSite( Site site ) {
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
                new String[] { TABLE_SITES_FIELD_NAME },     // columns
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

}
