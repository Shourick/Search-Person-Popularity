package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.DataRepositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Site;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Data.ISiteRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.SQLiteRepository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Constants.KEY_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Constants.TABLE_SITES;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Constants.TABLE_SITES_FIELD_NAME;

/**
 * Created by skubatko on 08/11/17
 */

public class SQLiteSiteRepository implements ISiteRepository {

    private SQLiteRepository repository;

    public SQLiteSiteRepository( SQLiteRepository repository ) {
        this.repository = repository;
    }

    @Override
    public void addSite( Site site ) {
        try ( SQLiteDatabase db = repository.getWritableDatabase() ) {
            ContentValues contentValues = new ContentValues();
            contentValues.put( TABLE_SITES_FIELD_NAME, site.getName() );

            db.insert( TABLE_SITES, null, contentValues );
        }

    }

    @Override
    public Site getSite( int id ) {
        Site site = new Site();
        SQLiteDatabase db = repository.getReadableDatabase();

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
        SQLiteDatabase db = repository.getReadableDatabase();

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
        SQLiteDatabase db = repository.getReadableDatabase();

        try ( Cursor cursorSites = db.rawQuery( countQuery, null ) ) {
            count = cursorSites.getCount();
        }
        return count;
    }

    @Override
    public int updateSite( Site site ) {
        int result = 0;
        try ( SQLiteDatabase db = repository.getWritableDatabase() ) {
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
        try ( SQLiteDatabase dbWritable = repository.getWritableDatabase() ) {
            dbWritable.delete( TABLE_SITES,
                    KEY_ID + " = ?",
                    new String[] { String.valueOf( site.getId() ) }
            );
        }
    }

    @Override
    public void deleteAllSites() {
        try ( SQLiteDatabase db = repository.getWritableDatabase() ) {
            db.delete( TABLE_SITES, null, null );
        }
    }
}
