package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.DataRepositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Person;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Data.IPersonRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils.SQLiteRepository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.KEY_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_PERSONS;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.TABLE_PERSONS_FIELD_NAME;

/**
 * Created by skubatko on 08/11/17
 */

public class SQLitePersonRepository implements IPersonRepository {

    private SQLiteRepository repository;

    public SQLitePersonRepository( SQLiteRepository repository ) {
        this.repository = repository;
    }

    @Override
    public int addPerson( Person person ) {
        try ( SQLiteDatabase db = repository.getWritableDatabase() ) {
            ContentValues contentValues = new ContentValues();
            if ( person.getId() != 0 ) {
                contentValues.put( KEY_ID, person.getId() );
            }
            contentValues.put( TABLE_PERSONS_FIELD_NAME, person.getName() );

            db.insert( TABLE_PERSONS, null, contentValues );
        }
        return person.getId();
    }

    @Override
    public Person getPersonById( int id ) {
        Person person = new Person( EMPTY_ID, EMPTY_NAME );
        SQLiteDatabase db = repository.getReadableDatabase();

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
        Person person = new Person( EMPTY_ID, EMPTY_NAME );
        SQLiteDatabase db = repository.getReadableDatabase();

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
        SQLiteDatabase db = repository.getReadableDatabase();

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
        SQLiteDatabase db = repository.getReadableDatabase();

        try ( Cursor cursorPersons = db.rawQuery( countQuery, null ) ) {
            count = cursorPersons.getCount();
        }
        return count;
    }

    @Override
    public int updatePerson( Person person ) {
        int result = 0;
        try ( SQLiteDatabase db = repository.getWritableDatabase() ) {
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
        try ( SQLiteDatabase db = repository.getWritableDatabase() ) {
            db.delete( TABLE_PERSONS,
                    KEY_ID + " = ?",
                    new String[] { String.valueOf( person.getId() ) } );
        }
    }

    @Override
    public void deleteAllPersons() {
        try ( SQLiteDatabase db = repository.getWritableDatabase() ) {
            db.delete( TABLE_PERSONS, null, null );
        }
    }

}
