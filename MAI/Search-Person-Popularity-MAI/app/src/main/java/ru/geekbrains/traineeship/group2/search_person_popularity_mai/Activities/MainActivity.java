package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Database.SQLite.SQLiteRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * databaseHandler применяем глобально во всех Activities для обмена данными с БД
     * при доступности Веб-сервиса поменять на класс, имплементирующий работу с Веб-сервисом
     */

    public static SQLiteRepository databaseHandler;

    private Button btnPersonsDirectory, btnKeywordsDirectory, btnSitesDirectory, btnUsersDirectory;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        btnPersonsDirectory = (Button) findViewById( R.id.btnPersonsDirectory );
        btnKeywordsDirectory = (Button) findViewById( R.id.btnKeywordsDirectory );
        btnSitesDirectory = (Button) findViewById( R.id.btnSitesDirectory );
        btnUsersDirectory = (Button) findViewById( R.id.btnUsersDirectory );

        btnPersonsDirectory.setOnClickListener( this );
        btnKeywordsDirectory.setOnClickListener( this );
        btnSitesDirectory.setOnClickListener( this );
        btnUsersDirectory.setOnClickListener( this );

        databaseHandler = new SQLiteRepository( this );
        databaseHandler.initializeDatabase();

        databaseHandler.showDatabaseInfo();

    }

    @Override
    public void onClick( View v ) {
        Intent intent;
        switch ( v.getId() ) {
            case R.id.btnPersonsDirectory:
                Toast.makeText( v.getContext(), "btnPersonsDirectory", Toast.LENGTH_SHORT ).show();
                intent = new Intent( this, PersonsDirectoryActivity.class );
                startActivityForResult( intent, 1 );
                break;
            case R.id.btnKeywordsDirectory:
                Toast.makeText( v.getContext(), "btnKeywordsDirectory", Toast.LENGTH_SHORT ).show();
                intent = new Intent( this, KeywordsDirectoryActivity.class );
                startActivityForResult( intent, 1 );
                break;
            case R.id.btnSitesDirectory:
                Toast.makeText( v.getContext(), "btnSitesDirectory", Toast.LENGTH_SHORT ).show();
                intent = new Intent( this, SitesDirectoryActivity.class );
                startActivityForResult( intent, 1 );
                break;
            case R.id.btnUsersDirectory:
                Toast.makeText( v.getContext(), "btnUsersDirectory", Toast.LENGTH_SHORT ).show();
                intent = new Intent( this, UsersDirectoryActivity.class );
                startActivityForResult( intent, 1 );
                break;
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        databaseHandler.close();
    }
}
