package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.Persons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.R;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Person;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils.SQLiteRepository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.MAIN_DATABASE_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.PERSON_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.PERSON_NAME;

public class PersonsDirectoryEditPersonActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etEditPersonName;

    private SQLiteRepository mMainRepository;
    private int mEditedPersonId;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_persons_directory_edit_person );

        etEditPersonName = (EditText) findViewById( R.id.etEditPersonName );

        Button btnEditPersonOK = (Button) findViewById( R.id.btnEditPersonOK );
        Button btnEditPersonCancel = (Button) findViewById( R.id.btnEditPersonCancel );

        btnEditPersonOK.setOnClickListener( this );
        btnEditPersonCancel.setOnClickListener( this );

        mMainRepository = new SQLiteRepository( this, MAIN_DATABASE_NAME );

        Bundle extras = getIntent().getExtras();
        if ( extras != null ) {
            mEditedPersonId = extras.getInt( PERSON_ID );
            etEditPersonName.setText( extras.getString( PERSON_NAME ) );
        }
    }

    @Override
    public void onClick( View v ) {
        Intent intent;
        switch ( v.getId() ) {

            case R.id.btnEditPersonOK:
                Person editedPerson = new Person( etEditPersonName.getText().toString() );
                editedPerson.setId( mEditedPersonId );

                mMainRepository.getPersonRepository().updatePerson( editedPerson );

                intent = new Intent();
                setResult( RESULT_OK, intent );
                finish();
                break;

            case R.id.btnEditPersonCancel:
                intent = new Intent();
                setResult( RESULT_CANCELED, intent );
                finish();
                break;

            default:
                break;
        }
    }
}
