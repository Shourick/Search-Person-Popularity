package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.Admins.AdminLoginActivity;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.Admins.AdminsDirectoryActivity;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.Keywords.KeywordsDirectoryActivity;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.Persons.PersonsDirectoryActivity;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.Sites.SitesDirectoryActivity;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.Users.UsersDirectoryActivity;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.R;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils.SQLiteRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Utils.RepositorySync;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.AdminAuthorization;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.MAIN_DATABASE_NAME;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * repository применяем глобально во всех Activities для обмена данными с БД
     * при доступности Веб-сервиса поменять на класс, имплементирующий работу с Веб-сервисом
     */
    public static SQLiteRepository repository;


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        Button btnPersonsDirectory,
                btnKeywordsDirectory,
                btnSitesDirectory,
                btnUsersDirectory,
                btnAdminsDirectory,
                btnSync,
                btnAdminLogout;

        btnPersonsDirectory = (Button) findViewById( R.id.btnPersonsDirectory );
        btnKeywordsDirectory = (Button) findViewById( R.id.btnKeywordsDirectory );
        btnSitesDirectory = (Button) findViewById( R.id.btnSitesDirectory );
        btnUsersDirectory = (Button) findViewById( R.id.btnUsersDirectory );
        btnAdminsDirectory = (Button) findViewById( R.id.btnAdminsDirectory );
        btnSync = (Button) findViewById( R.id.btnSync );
        btnAdminLogout = (Button) findViewById( R.id.btnAdminLogout );

        btnPersonsDirectory.setOnClickListener( this );
        btnKeywordsDirectory.setOnClickListener( this );
        btnSitesDirectory.setOnClickListener( this );
        btnUsersDirectory.setOnClickListener( this );
        btnAdminsDirectory.setOnClickListener( this );
        btnSync.setOnClickListener( this );
        btnAdminLogout.setOnClickListener( this );

        repository = new SQLiteRepository( this, MAIN_DATABASE_NAME );

        if ( AdminAuthorization.isNotAuthorized( this ) ) {
            onLogout();
        }

    }

    @Override
    public void onClick( View v ) {
        Intent intent;
        switch ( v.getId() ) {
            case R.id.btnPersonsDirectory:
                intent = new Intent( this, PersonsDirectoryActivity.class );
                startActivity( intent );
                break;

            case R.id.btnKeywordsDirectory:
                intent = new Intent( this, KeywordsDirectoryActivity.class );
                startActivity( intent );
                break;

            case R.id.btnSitesDirectory:
                intent = new Intent( this, SitesDirectoryActivity.class );
                startActivity( intent );
                break;

            case R.id.btnUsersDirectory:
                intent = new Intent( this, UsersDirectoryActivity.class );
                startActivity( intent );
                break;

            case R.id.btnAdminsDirectory:
                intent = new Intent( this, AdminsDirectoryActivity.class );
                startActivity( intent );
                break;

            case R.id.btnSync:
                new RepositorySync( this ).execute();
                break;

            case R.id.btnAdminLogout:
                AdminAuthorization.setNotAuthorized( this );
                onLogout();
                break;
        }

    }

    private void onLogout() {
        Intent adminAuthorization = new Intent( this, AdminLoginActivity.class );
        adminAuthorization.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity( adminAuthorization );
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        AdminAuthorization.setNotAuthorized( this );
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        AdminAuthorization.setNotAuthorized( this );
    }

    @Override
    protected void onStop() {
        super.onStop();
        repository.close();
        AdminAuthorization.setNotAuthorized( this );
    }

}
