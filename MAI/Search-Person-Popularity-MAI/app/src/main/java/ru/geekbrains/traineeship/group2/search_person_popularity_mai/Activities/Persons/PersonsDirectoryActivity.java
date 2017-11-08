package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.Persons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.Persons.PersonsDirectoryAddPersonActivity;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.Persons.PersonsDirectoryEditPersonActivity;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.R;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Person;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.MainActivity.repository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Constants.*;

public class PersonsDirectoryActivity extends AppCompatActivity implements View.OnClickListener
{

    /**
     * databaseHandler применяем глобально во всех Activities для обмена данными с БД
     * при доступности Веб-сервиса поменять на класс, имплементирующий работу с Веб-сервисом
     */

    Button btnBackPersonsDirectory, btnPersonAdd, btnPersonEdit, btnPersonDelete;
    TextView tvPersonName;

    ListView lvPersonsList;
    List<Person> listAllPersons;
    ArrayAdapter<Person> listPersonAdapter;

    int selectedPersonId;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_persons_directory );

        btnBackPersonsDirectory = (Button) findViewById( R.id.btnBackPersonsDirectory );
        btnBackPersonsDirectory.setOnClickListener( this );

        btnPersonAdd = (Button) findViewById( R.id.btnPersonAdd );
        btnPersonEdit = (Button) findViewById( R.id.btnPersonEdit );
        btnPersonDelete = (Button) findViewById( R.id.btnPersonDelete );

        tvPersonName = (TextView) findViewById( R.id.tvPersonName );

        lvPersonsList = (ListView) findViewById( R.id.lvPersonsList );

        btnBackPersonsDirectory.setOnClickListener( this );
        btnPersonAdd.setOnClickListener( this );
        btnPersonEdit.setOnClickListener( this );
        btnPersonDelete.setOnClickListener( this );

        repository.getRepositoryUtils().showRepositoryInfo();

        listAllPersons = repository.getPersonRepository().getAllPersons();
        listPersonAdapter = new ArrayAdapter<Person>( this, android.R.layout.simple_list_item_1, listAllPersons );
        lvPersonsList.setAdapter( listPersonAdapter );
        selectedPersonId = -1;

        lvPersonsList.setOnItemClickListener( new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick( AdapterView<?> adapterView, View itemClicked, int position, long id )
            {
                adapterView.requestFocusFromTouch();
                adapterView.setSelection( position );
            }
        } );

        lvPersonsList.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected( AdapterView<?> adapterView, View itemSelected, int position, long id )
            {
                Person selectedPerson = (Person) adapterView.getSelectedItem();
                selectedPersonId = selectedPerson.getId();
                tvPersonName.setText( selectedPerson.getName() );
            }

            @Override
            public void onNothingSelected( AdapterView<?> parent )
            {
            }
        } );
    }

    @Override
    public void onClick( View v )
    {
        Intent intent;

        switch ( v.getId() )
        {
            case R.id.btnPersonAdd:
                intent = new Intent( this, PersonsDirectoryAddPersonActivity.class );
                startActivityForResult( intent, REQUEST_CODE_ADD_PERSON );
                break;

            case R.id.btnPersonEdit:
                if ( isPersonSelected() )
                {
                    intent = new Intent( this, PersonsDirectoryEditPersonActivity.class );
                    intent.putExtra( PERSON_ID, selectedPersonId );
                    intent.putExtra( PERSON_NAME, tvPersonName.getText().toString() );
                    startActivityForResult( intent, REQUEST_CODE_EDIT_PERSON );
                }
                break;

            case R.id.btnPersonDelete:
                if ( isPersonSelected() )
                {
                    repository.getPersonRepository().deletePerson(
                            new Person( selectedPersonId, tvPersonName.getText().toString() ) );
                }
                initializeSelectedPerson();
                break;

            case R.id.btnBackPersonsDirectory:
                intent = new Intent();
                setResult( RESULT_OK, intent );
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data )
    {
        switch ( requestCode )
        {

            case REQUEST_CODE_ADD_PERSON:
                if ( resultCode == RESULT_OK )
                {
                    initializeSelectedPerson();
                }
                break;

            case REQUEST_CODE_EDIT_PERSON:
                if ( resultCode == RESULT_OK )
                {
                    initializeSelectedPerson();
                }
                break;
        }
    }

    private void initializeSelectedPerson()
    {
        selectedPersonId = -1;

        tvPersonName.setText( "" );

        listAllPersons.clear();
        listAllPersons.addAll( repository.getPersonRepository().getAllPersons() );
        listPersonAdapter.notifyDataSetChanged();
    }

    private boolean isPersonSelected()
    {
        return selectedPersonId != -1;
    }


}
