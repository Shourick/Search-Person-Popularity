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

public class UsersDirectoryAddUserActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etAddUserNickname, etAddUserLogin, etAddUserPassword;

    private SQLiteRepository mMainRepository;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_users_directory_add_user );

        etAddUserNickname = (EditText) findViewById( R.id.etAddUserNickname );
        etAddUserLogin = (EditText) findViewById( R.id.etAddUserLogin );
        etAddUserPassword = (EditText) findViewById( R.id.etAddUserPassword );

        Button btnAddUserOK = (Button) findViewById( R.id.btnAddUserOK );
        Button btnAddUserCancel = (Button) findViewById( R.id.btnAddUserCancel );

        btnAddUserOK.setOnClickListener( this );
        btnAddUserCancel.setOnClickListener( this );

        mMainRepository = new SQLiteRepository( this, MAIN_DATABASE_NAME );
    }

    @Override
    public void onClick( View v ) {
        Intent intent;

        switch ( v.getId() ) {

            case R.id.btnAddUserOK:
                mMainRepository.getUserRepository().addUser(
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

            default:
                break;
        }
    }
}
