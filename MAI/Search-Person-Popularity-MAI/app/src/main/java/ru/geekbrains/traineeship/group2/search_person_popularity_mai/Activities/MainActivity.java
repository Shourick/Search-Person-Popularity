package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Database.SQLiteDatabaseHandler;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * databaseHandler применяем глобально во всех Activities для обмена данными с БД
     * при доступности Веб-сервиса поменять на класс, имплементирующий работу с Веб-сервисом
     */

    public static SQLiteDatabaseHandler databaseHandler;

    private Button btnPersonsDirectory, btnKeywordsDirectory, btnSitesDirectory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPersonsDirectory = (Button) findViewById(R.id.btnPersonsDirectory);
        btnKeywordsDirectory = (Button) findViewById(R.id.btnKeywordsDirectory);
        btnSitesDirectory = (Button) findViewById(R.id.btnSitesDirectory);

        btnPersonsDirectory.setOnClickListener(this);
        btnKeywordsDirectory.setOnClickListener(this);
        btnSitesDirectory.setOnClickListener(this);

        databaseHandler = new SQLiteDatabaseHandler(this);
        databaseHandler.initializeDatabase();

        databaseHandler.showDatabaseInfo();

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch ( v.getId() ) {
            case R.id.btnPersonsDirectory:
                Toast.makeText(v.getContext(), "btnPersonsDirectory", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, PersonsDirectoryActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.btnKeywordsDirectory:
                Toast.makeText(v.getContext(), "btnKeywordsDirectory", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, KeywordsDirectoryActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.btnSitesDirectory:
                Toast.makeText(v.getContext(), "btnSitesDirectory", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, SitesDirectoryActivity.class);
                startActivityForResult(intent, 1);
                break;
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        databaseHandler.close();
    }
}
