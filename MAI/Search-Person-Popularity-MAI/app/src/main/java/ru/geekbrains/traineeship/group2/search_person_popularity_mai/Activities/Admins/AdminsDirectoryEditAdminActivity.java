package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.Admins;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.R;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.Admin;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.MainActivity.repository;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.ADMIN_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.ADMIN_LOGIN;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.ADMIN_NICKNAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.ADMIN_PASSWORD;

public class AdminsDirectoryEditAdminActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etEditAdminNickname, etEditAdminLogin, etEditAdminPassword;
    Button btnEditAdminOK, btnEditAdminCancel;
    int editedAdminId;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_admins_directory_edit_admin );

        etEditAdminNickname = (EditText) findViewById( R.id.etEditAdminNickname );
        etEditAdminLogin = (EditText) findViewById( R.id.etEditAdminLogin );
        etEditAdminPassword = (EditText) findViewById( R.id.etEditAdminPassword );

        btnEditAdminOK = (Button) findViewById( R.id.btnEditAdminOK );
        btnEditAdminCancel = (Button) findViewById( R.id.btnEditAdminCancel );

//        убрать при реализации обновления в API
        etEditAdminLogin.setEnabled( false );

        btnEditAdminOK.setOnClickListener( this );
        btnEditAdminCancel.setOnClickListener( this );

        Bundle extras = getIntent().getExtras();
        if ( extras != null ) {
            editedAdminId = extras.getInt( ADMIN_ID );
            etEditAdminNickname.setText( extras.getString( ADMIN_NICKNAME ) );
            etEditAdminLogin.setText( extras.getString( ADMIN_LOGIN ) );
            etEditAdminPassword.setText( extras.getString( ADMIN_PASSWORD ) );
        }
    }

    @Override
    public void onClick( View v ) {
        Intent intent;
        switch ( v.getId() ) {

            case R.id.btnEditAdminOK:
                Admin editedAdmin = new Admin( etEditAdminNickname.getText().toString(),
                        etEditAdminLogin.getText().toString(),
                        etEditAdminPassword.getText().toString() );
                editedAdmin.setId( editedAdminId );

                repository.getAdminRepository().updateAdmin( editedAdmin );

                intent = new Intent();
                setResult( RESULT_OK, intent );
                finish();
                break;

            case R.id.btnEditAdminCancel:
                intent = new Intent();
                setResult( RESULT_CANCELED, intent );
                finish();
                break;
        }
    }
}