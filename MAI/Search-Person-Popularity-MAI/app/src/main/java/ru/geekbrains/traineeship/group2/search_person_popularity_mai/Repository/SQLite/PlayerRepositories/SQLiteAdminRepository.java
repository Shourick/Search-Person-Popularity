package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.PlayerRepositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Players.IAdminRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.Admin;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils.SQLiteRepository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.KEY_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_ADMINS;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_ADMINS_FIELD_LOGIN;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_ADMINS_FIELD_PASSWORD;

/**
 * Created by skubatko on 08/11/17
 */

public class SQLiteAdminRepository implements IAdminRepository {

    private SQLiteRepository repository;

    public SQLiteAdminRepository( SQLiteRepository repository ) {
        this.repository = repository;
    }

    @Override
    public void addAdmin( Admin admin ) {
        try ( SQLiteDatabase db = repository.getWritableDatabase() ) {
            ContentValues contentValues = new ContentValues();
            contentValues.put( TABLE_ADMINS_FIELD_LOGIN, admin.getLogin() );
            contentValues.put( TABLE_ADMINS_FIELD_PASSWORD, admin.getPassword() );

            db.insert( TABLE_ADMINS, null, contentValues );
        }
    }

    @Override
    public Admin getAdmin( int id ) {
        Admin admin = new Admin();
        SQLiteDatabase db = repository.getReadableDatabase();

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
        SQLiteDatabase db = repository.getReadableDatabase();

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
        SQLiteDatabase db = repository.getReadableDatabase();

        try ( Cursor cursorAdmins = db.rawQuery( countQuery, null ) ) {
            count = cursorAdmins.getCount();
        }
        return count;
    }

    @Override
    public int updateAdmin( Admin admin ) {
        int result = 0;
        try ( SQLiteDatabase db = repository.getWritableDatabase() ) {
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
        try ( SQLiteDatabase db = repository.getWritableDatabase() ) {
            db.delete( TABLE_ADMINS,
                    KEY_ID + " = ?",
                    new String[] { String.valueOf( admin.getId() ) } );
        }
    }

    @Override
    public void deleteAllAdmins() {
        try ( SQLiteDatabase db = repository.getWritableDatabase() ) {
            db.delete( TABLE_ADMINS, null, null );
        }
    }
}
