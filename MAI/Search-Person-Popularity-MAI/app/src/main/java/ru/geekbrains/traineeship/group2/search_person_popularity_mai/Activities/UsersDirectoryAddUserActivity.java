package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.R;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.User;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.MainActivity.repository;

public class UsersDirectoryAddUserActivity extends AppCompatActivity implements View.OnClickListener
{

    EditText etAddUserNickname, etAddUserLogin, etAddUserPassword;
    Button btnAddUserOK, btnAddUserCancel;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_users_directory_add_user );

        etAddUserNickname = (EditText) findViewById( R.id.etAddUserNickname );
        etAddUserLogin = (EditText) findViewById( R.id.etAddUserLogin );
        etAddUserPassword = (EditText) findViewById( R.id.etAddUserPassword );

        btnAddUserOK = (Button) findViewById( R.id.btnAddUserOK );
        btnAddUserCancel = (Button) findViewById( R.id.btnAddUserCancel );

        btnAddUserOK.setOnClickListener( this );
        btnAddUserCancel.setOnClickListener( this );
    }

    @Override
    public void onClick( View v )
    {
        Intent intent;
        switch ( v.getId() )
        {

            case R.id.btnAddUserOK:
                repository.addUser(
                        new User( etAddUserNickname.getText().toString(),
                                etAddUserLogin.getText().toString(),
                                etAddUserPassword.getText().toString() ) );
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
