package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.PlayerRepositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Players.IUserRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.User;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils.SQLiteRepository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.KEY_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_USERS;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_USERS_FIELD_LOGIN;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_USERS_FIELD_NICKNAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_USERS_FIELD_PASSWORD;

/**
 * Created by skubatko on 08/11/17
 */

public class SQLiteUserRepository implements IUserRepository {

    private SQLiteRepository repository;

    public SQLiteUserRepository( SQLiteRepository repository ) {
        this.repository = repository;
    }

    @Override
    public int addUser( User user ) {
        try ( SQLiteDatabase db = repository.getWritableDatabase() ) {
            ContentValues contentValues = new ContentValues();
            contentValues.put( TABLE_USERS_FIELD_NICKNAME, user.getNickName() );
            contentValues.put( TABLE_USERS_FIELD_LOGIN, user.getLogin() );
            contentValues.put( TABLE_USERS_FIELD_PASSWORD, user.getPassword() );

            db.insert( TABLE_USERS, null, contentValues );
        }
        return user.getId();
    }

    @Override
    public User getUser( int id ) {
        User user = new User();
        SQLiteDatabase db = repository.getReadableDatabase();

        try ( Cursor cursorUsers = db.query(
                TABLE_USERS,                              // table
                new String[] { KEY_ID,
                        TABLE_USERS_FIELD_NICKNAME,
                        TABLE_USERS_FIELD_LOGIN,
                        TABLE_USERS_FIELD_PASSWORD },     // columns
                KEY_ID + " = ?",                                   // columns WHERE
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
        SQLiteDatabase db = repository.getReadableDatabase();

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
        SQLiteDatabase db = repository.getReadableDatabase();

        try ( Cursor cursorUsers = db.rawQuery( countQuery, null ) ) {
            count = cursorUsers.getCount();
        }
        return count;
    }

    @Override
    public int updateUser( User user ) {
        int result = 0;
        try ( SQLiteDatabase db = repository.getWritableDatabase() ) {
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
        try ( SQLiteDatabase dbWritable = repository.getWritableDatabase() ) {
            dbWritable.delete( TABLE_USERS,
                    KEY_ID + " = ?",
                    new String[] { String.valueOf( user.getId() ) }
            );
        }
    }

    @Override
    public void deleteAllUsers() {
        try ( SQLiteDatabase db = repository.getWritableDatabase() ) {
            db.delete( TABLE_USERS, null, null );
        }
    }

}
