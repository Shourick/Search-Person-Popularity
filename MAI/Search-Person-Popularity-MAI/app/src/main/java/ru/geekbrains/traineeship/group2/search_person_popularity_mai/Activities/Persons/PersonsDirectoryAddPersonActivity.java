package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.Persons;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.R;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Person;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.MainActivity.repository;

public class PersonsDirectoryAddPersonActivity extends AppCompatActivity implements View.OnClickListener
{
    EditText etAddPersonName;
    Button btnAddPersonOK, btnAddPersonCancel;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_persons_directory_add_person );

        etAddPersonName = (EditText) findViewById( R.id.etAddPersonName );

        btnAddPersonOK = (Button) findViewById( R.id.btnAddPersonOK );
        btnAddPersonCancel = (Button) findViewById( R.id.btnAddPersonCancel );

        btnAddPersonOK.setOnClickListener( this );
        btnAddPersonCancel.setOnClickListener( this );
    }

    @Override
    public void onClick( View v )
    {
        Intent intent;

        switch ( v.getId() )
        {

            case R.id.btnAddPersonOK:
                repository.getPersonRepository().addPerson( new Person( etAddPersonName.getText().toString() ) );
                intent = new Intent();
                setResult( RESULT_OK, intent );
                finish();
                break;

            case R.id.btnAddPersonCancel:
                intent = new Intent();
                setResult( RESULT_CANCELED, intent );
                finish();
                break;
        }
    }
}
