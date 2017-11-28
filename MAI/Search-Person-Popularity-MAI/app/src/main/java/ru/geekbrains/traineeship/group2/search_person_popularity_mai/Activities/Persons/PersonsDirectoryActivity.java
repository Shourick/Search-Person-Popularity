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

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.R;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Person;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils.SQLiteRepository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.ITEM_NOT_SELECTED;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.MAIN_DATABASE_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.PERSON_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.PERSON_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.REQUEST_CODE_ADD_PERSON;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.REQUEST_CODE_EDIT_PERSON;

public class PersonsDirectoryActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvPersonName;

    private List<Person> mListAllPersons;
    private ArrayAdapter<Person> mListPersonAdapter;
    private SQLiteRepository mMainRepository;
    private int mSelectedPersonId;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_persons_directory );

        Button btnPersonAdd = (Button) findViewById( R.id.btnPersonAdd );
        Button btnPersonEdit = (Button) findViewById( R.id.btnPersonEdit );
        Button btnPersonDelete = (Button) findViewById( R.id.btnPersonDelete );
        Button btnBackPersonsDirectory = (Button) findViewById( R.id.btnBackPersonsDirectory );

        btnPersonAdd.setOnClickListener( this );
        btnPersonEdit.setOnClickListener( this );
        btnPersonDelete.setOnClickListener( this );
        btnBackPersonsDirectory.setOnClickListener( this );

        tvPersonName = (TextView) findViewById( R.id.tvPersonName );

        ListView lvPersonsList = (ListView) findViewById( R.id.lvPersonsList );
        mMainRepository = new SQLiteRepository( this, MAIN_DATABASE_NAME );
        mListAllPersons = mMainRepository.getPersonRepository().getAllPersons();
        mListPersonAdapter = new ArrayAdapter<>( this,
                android.R.layout.simple_list_item_1,
                mListAllPersons );
        lvPersonsList.setAdapter( mListPersonAdapter );

        mSelectedPersonId = ITEM_NOT_SELECTED;

        lvPersonsList.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> adapterView, View itemClicked, int position, long id ) {
                adapterView.requestFocusFromTouch();
                adapterView.setSelection( position );
            }
        } );

        lvPersonsList.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView<?> adapterView, View itemSelected, int position, long id ) {
                Person selectedPerson = (Person) adapterView.getSelectedItem();
                mSelectedPersonId = selectedPerson.getId();
                tvPersonName.setText( selectedPerson.getName() );
            }

            @Override
            public void onNothingSelected( AdapterView<?> parent ) {
            }
        } );
    }

    @Override
    public void onClick( View v ) {
        Intent intent;

        switch ( v.getId() ) {
            case R.id.btnPersonAdd:
                intent = new Intent( this, PersonsDirectoryAddPersonActivity.class );
                startActivityForResult( intent, REQUEST_CODE_ADD_PERSON );
                break;

            case R.id.btnPersonEdit:
                if ( isPersonSelected() ) {
                    intent = new Intent( this, PersonsDirectoryEditPersonActivity.class );
                    intent.putExtra( PERSON_ID, mSelectedPersonId );
                    intent.putExtra( PERSON_NAME, tvPersonName.getText().toString() );
                    startActivityForResult( intent, REQUEST_CODE_EDIT_PERSON );
                }
                break;

            case R.id.btnPersonDelete:
                if ( isPersonSelected() ) {
                    mMainRepository.getPersonRepository().deletePerson(
                            new Person( mSelectedPersonId, tvPersonName.getText().toString() ) );
                }
                initializeSelectedPerson();
                break;

            case R.id.btnBackPersonsDirectory:
                intent = new Intent();
                setResult( RESULT_OK, intent );
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
        switch ( requestCode ) {

            case REQUEST_CODE_ADD_PERSON:
                if ( resultCode == RESULT_OK ) {
                    initializeSelectedPerson();
                }
                break;

            case REQUEST_CODE_EDIT_PERSON:
                if ( resultCode == RESULT_OK ) {
                    initializeSelectedPerson();
                }
                break;

            default:
                break;
        }
    }

    private void initializeSelectedPerson() {
        mSelectedPersonId = ITEM_NOT_SELECTED;

        tvPersonName.setText( EMPTY_NAME );

        mListAllPersons.clear();
        mListAllPersons.addAll( mMainRepository.getPersonRepository().getAllPersons() );
        mListPersonAdapter.notifyDataSetChanged();
    }

    private boolean isPersonSelected() {
        return mSelectedPersonId != ITEM_NOT_SELECTED;
    }

}
