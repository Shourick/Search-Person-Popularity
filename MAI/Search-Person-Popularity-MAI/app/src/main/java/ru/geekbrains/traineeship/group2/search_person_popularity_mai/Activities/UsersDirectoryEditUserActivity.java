package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Constants;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.R;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.User;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.MainActivity.repository;

public class UsersDirectoryEditUserActivity extends AppCompatActivity implements View.OnClickListener
{

    EditText etEditUserNickname, etEditUserLogin, etEditUserPassword;
    Button btnEditUserOK, btnEditUserCancel;
    int editedUserId;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_users_directory_edit_user );

        etEditUserNickname = (EditText) findViewById( R.id.etEditUserNickname );
        etEditUserLogin = (EditText) findViewById( R.id.etEditUserLogin );
        etEditUserPassword = (EditText) findViewById( R.id.etEditUserPassword );

        btnEditUserOK = (Button) findViewById( R.id.btnEditUserOK );
        btnEditUserCancel = (Button) findViewById( R.id.btnEditUserCancel );

        btnEditUserOK.setOnClickListener( this );
        btnEditUserCancel.setOnClickListener( this );

        Bundle extras = getIntent().getExtras();
        if ( extras != null )
        {
            editedUserId = extras.getInt( Constants.USER_ID );
            etEditUserNickname.setText( extras.getString( Constants.USER_NICKNAME ) );
            etEditUserLogin.setText( extras.getString( Constants.USER_LOGIN ) );
            etEditUserPassword.setText( extras.getString( Constants.USER_PASSWORD ) );
        }
    }

    @Override
    public void onClick( View v )
    {
        Intent intent;
        switch ( v.getId() )
        {

            case R.id.btnEditUserOK:
                User editedUser = new User( etEditUserNickname.getText().toString(),
                        etEditUserLogin.getText().toString(),
                        etEditUserPassword.getText().toString() );
                editedUser.setId( editedUserId );

                repository.updateUser( editedUser );

                intent = new Intent();
                setResult( RESULT_OK, intent );
                finish();
                break;

            case R.id.btnEditUserCancel:
                intent = new Intent();
                setResult( RESULT_CANCELED, intent );
                finish();
                break;
        }
    }
}
