package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Database.Players.User;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.R;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.MainActivity.databaseHandler;

public class UsersDirectoryActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * databaseHandler применяем глобально во всех Activities для обмена данными с БД
     * при доступности Веб-сервиса поменять на класс, имплементирующий работу с Веб-сервисом
     */

    Button btnBackUsersDirectory, btnUserAdd, btnUserEdit, btnUserDelete;
    ListView lvUsersList;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_users );

        btnBackUsersDirectory = (Button) findViewById( R.id.btnBackUsersDirectory );
        btnUserAdd = (Button) findViewById( R.id.btnUserAdd );
        btnUserEdit = (Button) findViewById( R.id.btnUserEdit );
        btnUserDelete = (Button) findViewById( R.id.btnUserDelete );
        lvUsersList = (ListView) findViewById( R.id.lvUsersList );

        btnBackUsersDirectory.setOnClickListener( this );
        btnUserAdd.setOnClickListener( this );
        btnUserEdit.setOnClickListener( this );
        btnUserDelete.setOnClickListener( this );

        databaseHandler.showDatabaseInfo();

        ArrayAdapter<User> adapter = new ArrayAdapter<User>( this, android.R.layout.simple_list_item_1, databaseHandler.getAllUsers());
        lvUsersList.setAdapter( adapter );

    }

    @Override
    public void onClick( View v ) {
        switch ( v.getId() ) {
            case R.id.btnBackUsersDirectory:
                Intent intent = new Intent();
                setResult( RESULT_OK, intent );
                finish();
                break;
        }
    }

}
