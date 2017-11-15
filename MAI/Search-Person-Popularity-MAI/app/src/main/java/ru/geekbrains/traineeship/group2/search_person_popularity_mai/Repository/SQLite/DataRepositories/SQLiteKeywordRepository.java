package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.DataRepositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Keyword;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Data.IKeywordRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils.SQLiteRepository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.KEY_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_KEYWORDS;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_KEYWORDS_FIELD_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_KEYWORDS_FIELD_PERSON_ID;

/**
 * Created by skubatko on 08/11/17
 */

public class SQLiteKeywordRepository implements IKeywordRepository {

    private SQLiteRepository repository;

    public SQLiteKeywordRepository( SQLiteRepository repository ) {
        this.repository = repository;
    }

    @Override
    public void addKeyword( Keyword keyword, int personId ) {
        try ( SQLiteDatabase db = repository.getWritableDatabase() ) {
            ContentValues contentValues = new ContentValues();
            contentValues.put( TABLE_KEYWORDS_FIELD_NAME, keyword.getName() );
            contentValues.put( TABLE_KEYWORDS_FIELD_PERSON_ID, personId );

            db.insert( TABLE_KEYWORDS, null, contentValues );
        }

    }

    @Override
    public Keyword getKeyword( int id ) {
        Keyword keyword = new Keyword();
        SQLiteDatabase db = repository.getReadableDatabase();

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

        SQLiteDatabase db = repository.getReadableDatabase();

        try ( Cursor cursorKeywords = db.query(
                TABLE_KEYWORDS,                                     // table
                new String[] { KEY_ID,
                        TABLE_KEYWORDS_FIELD_NAME },            // columns
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
                    keyword.setPersonId( personId );
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
        SQLiteDatabase db = repository.getReadableDatabase();

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
        SQLiteDatabase db = repository.getReadableDatabase();

        try ( Cursor cursorKeywords = db.rawQuery( countQuery, null ) ) {
            count = cursorKeywords.getCount();
        }
        return count;
    }

    @Override
    public int updateKeyword( Keyword keyword ) {
        int result = 0;
        try ( SQLiteDatabase db = repository.getWritableDatabase() ) {
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
        try ( SQLiteDatabase db = repository.getWritableDatabase() ) {
            db.delete( TABLE_KEYWORDS,
                    KEY_ID + " = ?",
                    new String[] { String.valueOf( keyword.getId() ) } );
        }
    }

    @Override
    public void deleteAllKeywords() {
        try ( SQLiteDatabase db = repository.getWritableDatabase() ) {
            db.delete( TABLE_KEYWORDS, null, null );
        }
    }


}
