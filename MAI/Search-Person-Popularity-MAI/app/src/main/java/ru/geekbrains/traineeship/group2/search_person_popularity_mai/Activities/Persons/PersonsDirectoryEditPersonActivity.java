package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.Persons;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Constants;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.R;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Person;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.MainActivity.repository;

public class PersonsDirectoryEditPersonActivity extends AppCompatActivity implements View.OnClickListener
{

    EditText etEditPersonName;
    Button btnEditPersonOK, btnEditPersonCancel;
    int editedPersonId;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_persons_directory_edit_person );

        etEditPersonName = (EditText) findViewById( R.id.etEditPersonName );

        btnEditPersonOK = (Button) findViewById( R.id.btnEditPersonOK );
        btnEditPersonCancel = (Button) findViewById( R.id.btnEditPersonCancel );

        btnEditPersonOK.setOnClickListener( this );
        btnEditPersonCancel.setOnClickListener( this );

        Bundle extras = getIntent().getExtras();
        if ( extras != null )
        {
            editedPersonId = extras.getInt( Constants.PERSON_ID );
            etEditPersonName.setText( extras.getString( Constants.PERSON_NAME ) );
        }
    }

    @Override
    public void onClick( View v )
    {
        Intent intent;
        switch ( v.getId() )
        {

            case R.id.btnEditPersonOK:
                Person editedPerson = new Person( etEditPersonName.getText().toString());
                editedPerson.setId( editedPersonId );

                repository.updatePerson( editedPerson );

                intent = new Intent();
                setResult( RESULT_OK, intent );
                finish();
                break;

            case R.id.btnEditPersonCancel:
                intent = new Intent();
                setResult( RESULT_CANCELED, intent );
                finish();
                break;
        }
    }
}
