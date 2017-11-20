package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.DATABASE_VERSION;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.KEY_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_ADMINS;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_ADMINS_FIELD_LOGIN;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_ADMINS_FIELD_NICKNAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_ADMINS_FIELD_PASSWORD;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_KEYWORDS;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_KEYWORDS_FIELD_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_KEYWORDS_FIELD_PERSON_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_PERSONS;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_PERSONS_FIELD_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_SITES;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_SITES_FIELD_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_USERS;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_USERS_FIELD_LOGIN;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_USERS_FIELD_NICKNAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_USERS_FIELD_PASSWORD;

/**
 * Created by skubatko on 02/11/17.
 */

/**
 * РЕАЛИЗАЦИЯ ИНТЕРФЕЙСА SQLite
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    /**
     * Конструктор {@link SQLiteHelper}
     *
     * @param context Контекст приложения
     */
    public SQLiteHelper( Context context, String dbName ) {
        super( context, dbName, null, DATABASE_VERSION );
    }

    /**
     * Вызывается при создании базы данных
     */
    @Override
    public void onCreate( SQLiteDatabase db ) {
        createPersonsTable( db );
        createKeywordsTable( db );
        createSitesTable( db );
        createUsersTable( db );
        createAdminsTable( db );

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
     * Создание таблицы Users
     */
    private void createUsersTable( SQLiteDatabase db ) {
        /**
         * SQL запрос для создания таблицы Sites:
         *  CREATE TABLE users (
         *      _id     INTEGER     PRIMARY KEY AUTOINCREMENT,
         *      nickname    TEXT        NOT NULL,
         *      login    TEXT        NOT NULL,
         *      password    TEXT        NOT NULL
         *  );
         */
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_USERS_FIELD_NICKNAME + " TEXT NOT NULL, " +
                TABLE_USERS_FIELD_LOGIN + " TEXT NOT NULL, " +
                TABLE_USERS_FIELD_PASSWORD + " TEXT NOT NULL" +
                ");";

        db.execSQL( CREATE_USERS_TABLE );
    }

    /**
     * Создание таблицы Admins
     */
    private void createAdminsTable( SQLiteDatabase db ) {
        /**
         * SQL запрос для создания таблицы Sites:
         *  CREATE TABLE admins (
         *      _id     INTEGER     PRIMARY KEY AUTOINCREMENT,
         *      login    TEXT        NOT NULL,
         *      password    TEXT        NOT NULL
         *  );
         */
        String CREATE_ADMINS_TABLE = "CREATE TABLE " + TABLE_ADMINS + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_ADMINS_FIELD_NICKNAME + " TEXT NOT NULL, " +
                TABLE_ADMINS_FIELD_LOGIN + " TEXT NOT NULL, " +
                TABLE_ADMINS_FIELD_PASSWORD + " TEXT NOT NULL" +
                ");";

        db.execSQL( CREATE_ADMINS_TABLE );
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
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_USERS );
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_ADMINS );

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
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_USERS );
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_ADMINS );
        Log.d( "onDowngrade", "Таблицы базы данных удалены" );

        onCreate( db );
    }
}
