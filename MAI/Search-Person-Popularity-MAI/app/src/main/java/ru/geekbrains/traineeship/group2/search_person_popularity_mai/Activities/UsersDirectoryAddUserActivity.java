package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.User;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.R;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.MainActivity.repository;

public class UsersDirectoryAddUserActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etNickname, etLogin, etPassword;
    Button btnAddUserOK, btnAddUserCancel;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_users_directory_add_user );

        etNickname = (EditText) findViewById( R.id.etNickname );
        etLogin = (EditText) findViewById( R.id.etLogin );
        etPassword = (EditText) findViewById( R.id.etPassword );

        btnAddUserOK = (Button) findViewById( R.id.btnAddUserOK );
        btnAddUserCancel = (Button) findViewById( R.id.btnAddUserOK );

        btnAddUserOK.setOnClickListener( this );
        btnAddUserCancel.setOnClickListener( this );
    }

    @Override
    public void onClick( View v ) {
        Intent intent;
        switch ( v.getId() ) {
            case R.id.btnAddUserOK:
                repository.addUser(
                        new User( etNickname.getText().toString(),
                                etLogin.getText().toString(),
                                etPassword.getText().toString() ) );
                intent = new Intent();
                setResult( RESULT_OK, intent );
                finish();
                break;
            case R.id.btnAddUserCancel:
                intent = new Intent();
                setResult( RESULT_CANCELED, intent );
                finish();
                break;
        }
    }
}
