package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.Admins;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.R;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.Admin;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils.SQLiteRepository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.MAIN_DATABASE_NAME;

public class AdminsDirectoryAddAdminActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etAddAdminNickname, etAddAdminLogin, etAddAdminPassword;

    private SQLiteRepository mMainRepository;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_admins_directory_add_admin );

        etAddAdminNickname = (EditText) findViewById( R.id.etAddAdminNickname );
        etAddAdminLogin = (EditText) findViewById( R.id.etAddAdminLogin );
        etAddAdminPassword = (EditText) findViewById( R.id.etAddAdminPassword );

        Button btnAddAdminOK = (Button) findViewById( R.id.btnAddAdminOK );
        Button btnAddAdminCancel = (Button) findViewById( R.id.btnAddAdminCancel );

        btnAddAdminOK.setOnClickListener( this );
        btnAddAdminCancel.setOnClickListener( this );

        mMainRepository = new SQLiteRepository( this, MAIN_DATABASE_NAME );
    }

    @Override
    public void onClick( View v ) {
        Intent intent;

        switch ( v.getId() ) {

            case R.id.btnAddAdminOK:
                mMainRepository.getAdminRepository().addAdmin(
                        new Admin( etAddAdminNickname.getText().toString(),
                                etAddAdminLogin.getText().toString(),
                                etAddAdminPassword.getText().toString() ) );
                intent = new Intent();
                setResult( RESULT_OK, intent );
                finish();
                break;

            case R.id.btnAddAdminCancel:
                intent = new Intent();
                setResult( RESULT_CANCELED, intent );
                finish();
                break;

            default:
                break;
        }
    }
}
