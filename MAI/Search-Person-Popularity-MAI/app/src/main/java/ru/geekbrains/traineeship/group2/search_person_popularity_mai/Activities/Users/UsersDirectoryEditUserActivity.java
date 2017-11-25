package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.Users;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.R;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.User;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils.SQLiteRepository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.MAIN_DATABASE_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.USER_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.USER_LOGIN;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.USER_NICKNAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.USER_PASSWORD;

public class UsersDirectoryEditUserActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etEditUserNickname, etEditUserLogin, etEditUserPassword;

    private SQLiteRepository mMainRepository;
    private int mEditedUserId;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_users_directory_edit_user );

        etEditUserNickname = (EditText) findViewById( R.id.etEditUserNickname );
        etEditUserLogin = (EditText) findViewById( R.id.etEditUserLogin );
        etEditUserPassword = (EditText) findViewById( R.id.etEditUserPassword );

        Button btnEditUserOK = (Button) findViewById( R.id.btnEditUserOK );
        Button btnEditUserCancel = (Button) findViewById( R.id.btnEditUserCancel );

        etEditUserLogin.setEnabled( false );

        btnEditUserOK.setOnClickListener( this );
        btnEditUserCancel.setOnClickListener( this );

        mMainRepository = new SQLiteRepository( this, MAIN_DATABASE_NAME );

        Bundle extras = getIntent().getExtras();
        if ( extras != null ) {
            mEditedUserId = extras.getInt( USER_ID );
            etEditUserNickname.setText( extras.getString( USER_NICKNAME ) );
            etEditUserLogin.setText( extras.getString( USER_LOGIN ) );
            etEditUserPassword.setText( extras.getString( USER_PASSWORD ) );
        }
    }

    @Override
    public void onClick( View v ) {
        Intent intent;
        switch ( v.getId() ) {

            case R.id.btnEditUserOK:
                User editedUser = new User( etEditUserNickname.getText().toString(),
                        etEditUserLogin.getText().toString(),
                        etEditUserPassword.getText().toString() );
                editedUser.setId( mEditedUserId );

                mMainRepository.getUserRepository().updateUser( editedUser );

                intent = new Intent();
                setResult( RESULT_OK, intent );
                finish();
                break;

            case R.id.btnEditUserCancel:
                intent = new Intent();
                setResult( RESULT_CANCELED, intent );
                finish();
                break;

            default:
                break;
        }
    }
}
