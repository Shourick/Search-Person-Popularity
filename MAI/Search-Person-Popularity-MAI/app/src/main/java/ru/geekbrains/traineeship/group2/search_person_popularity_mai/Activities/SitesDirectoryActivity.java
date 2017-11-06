package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.R;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.MainActivity.repository;

public class SitesDirectoryActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * databaseHandler применяем глобально во всех Activities для обмена данными с БД
     * при доступности Веб-сервиса поменять на класс, имплементирующий работу с Веб-сервисом
     */

    Button btnBackSitesDirectory;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sites_directory );

        btnBackSitesDirectory = (Button) findViewById( R.id.btnBackSitesDirectory );
        btnBackSitesDirectory.setOnClickListener( this );

        repository.showInfo();

    }

    @Override
    public void onClick( View v ) {
        switch ( v.getId() ) {
            case R.id.btnBackSitesDirectory:
                Intent intent = new Intent();
                setResult( RESULT_OK, intent );
                finish();
                break;
        }

    }
}
