package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.Admins.AdminLoginActivity;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.Keywords.KeywordsDirectoryActivity;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.Persons.PersonsDirectoryActivity;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.Sites.SitesDirectoryActivity;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.Users.UsersDirectoryActivity;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.AdminAuthorization;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.R;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.SQLiteRepository;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * repository применяем глобально во всех Activities для обмена данными с БД
     * при доступности Веб-сервиса поменять на класс, имплементирующий работу с Веб-сервисом
     */
    public static SQLiteRepository repository;

    private Button btnPersonsDirectory,
            btnKeywordsDirectory,
            btnSitesDirectory,
            btnUsersDirectory,
            btnAdminLogout;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        btnPersonsDirectory = (Button) findViewById( R.id.btnPersonsDirectory );
        btnKeywordsDirectory = (Button) findViewById( R.id.btnKeywordsDirectory );
        btnSitesDirectory = (Button) findViewById( R.id.btnSitesDirectory );
        btnUsersDirectory = (Button) findViewById( R.id.btnUsersDirectory );
        btnAdminLogout = (Button) findViewById( R.id.btnAdminLogout );

        btnPersonsDirectory.setOnClickListener( this );
        btnKeywordsDirectory.setOnClickListener( this );
        btnSitesDirectory.setOnClickListener( this );
        btnUsersDirectory.setOnClickListener( this );
        btnAdminLogout.setOnClickListener( this );

        repository = new SQLiteRepository( this );
        repository.getRepositoryUtils().initializeRepository(this);
        repository.getRepositoryUtils().showRepositoryInfo();


//		Checkpoint if AdminModel is authorized
//        if ( AdminAuthorization.isNotAuthorized( this ) ) {
//            onLogout();
//        }
    }

    @Override
    public void onClick( View v ) {
        Intent intent;
        switch ( v.getId() ) {
            case R.id.btnPersonsDirectory:
                Toast.makeText( v.getContext(), "btnPersonsDirectory", Toast.LENGTH_SHORT ).show();
                intent = new Intent( this, PersonsDirectoryActivity.class );
                startActivity( intent );
                break;

            case R.id.btnKeywordsDirectory:
                Toast.makeText( v.getContext(), "btnKeywordsDirectory", Toast.LENGTH_SHORT ).show();
                intent = new Intent( this, KeywordsDirectoryActivity.class );
                startActivity( intent );
                break;

            case R.id.btnSitesDirectory:
                Toast.makeText( v.getContext(), "btnSitesDirectory", Toast.LENGTH_SHORT ).show();
                intent = new Intent( this, SitesDirectoryActivity.class );
                startActivity( intent );
                break;

            case R.id.btnUsersDirectory:
                Toast.makeText( v.getContext(), "btnUsersDirectory", Toast.LENGTH_SHORT ).show();
                intent = new Intent( this, UsersDirectoryActivity.class );
                startActivity( intent );
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
//        AdminAuthorization.setNotAuthorized( this );
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        AdminAuthorization.setNotAuthorized( this );
    }

    @Override
    protected void onStop() {
        super.onStop();
        repository.close();
//        AdminAuthorization.setNotAuthorized( this );
    }

}
