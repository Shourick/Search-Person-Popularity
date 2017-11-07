package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Constants;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.R;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.User;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.MainActivity.repository;

public class UsersDirectoryActivity extends AppCompatActivity implements View.OnClickListener
{

    /**
     * repository применяем глобально во всех Activities для обмена данными с БД
     * при доступности Веб-сервиса поменять на класс, имплементирующий работу с Веб-сервисом
     */

    Button btnBackUsersDirectory, btnUserAdd, btnUserEdit, btnUserDelete;
    TextView tvNickname, tvLogin, tvPassword;

    ListView lvUsersList;
    List<User> listAllUsers;
    ArrayAdapter<User> listUserAdapter;

    int selectedUserId;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_users );

        btnBackUsersDirectory = (Button) findViewById( R.id.btnBackUsersDirectory );
        btnUserAdd = (Button) findViewById( R.id.btnUserAdd );
        btnUserEdit = (Button) findViewById( R.id.btnUserEdit );
        btnUserDelete = (Button) findViewById( R.id.btnUserDelete );

        tvNickname = (TextView) findViewById( R.id.tvNickname );
        tvLogin = (TextView) findViewById( R.id.tvLogin );
        tvPassword = (TextView) findViewById( R.id.tvPassword );

        lvUsersList = (ListView) findViewById( R.id.lvUsersList );

        btnBackUsersDirectory.setOnClickListener( this );
        btnUserAdd.setOnClickListener( this );
        btnUserEdit.setOnClickListener( this );
        btnUserDelete.setOnClickListener( this );

        repository.showRepositoryInfo();

        listAllUsers = repository.getAllUsers();
        listUserAdapter = new ArrayAdapter<User>( this, android.R.layout.simple_list_item_1, listAllUsers );
        lvUsersList.setAdapter( listUserAdapter );
        selectedUserId = -1;

        lvUsersList.setOnItemClickListener( new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick( AdapterView<?> adapterView, View itemClicked, int position, long id )
            {
                adapterView.requestFocusFromTouch();
                adapterView.setSelection( position );
            }
        } );

        lvUsersList.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected( AdapterView<?> adapterView, View itemSelected, int position, long id )
            {
                User selectedUser = (User) adapterView.getSelectedItem();
                selectedUserId = selectedUser.getId();
                tvNickname.setText( selectedUser.getNickName() );
                tvLogin.setText( selectedUser.getLogin() );
                tvPassword.setText( selectedUser.getPassword() );
            }

            @Override
            public void onNothingSelected( AdapterView<?> parent )
            {
            }
        } );

    }

    @Override
    public void onClick( View v )
    {
        Intent intent;
        switch ( v.getId() )
        {

            case R.id.btnUserAdd:
                intent = new Intent( this, UsersDirectoryAddUserActivity.class );
                startActivityForResult( intent, Constants.REQUEST_CODE_ADD_USER );
                break;

            case R.id.btnUserEdit:
                if ( isUserSelected() )
                {
                    intent = new Intent( this, UsersDirectoryEditUserActivity.class );
                    intent.putExtra( Constants.USER_ID, selectedUserId );
                    intent.putExtra( Constants.USER_NICKNAME, tvNickname.getText().toString() );
                    intent.putExtra( Constants.USER_LOGIN, tvLogin.getText().toString() );
                    intent.putExtra( Constants.USER_PASSWORD, tvPassword.getText().toString() );
                    startActivityForResult( intent, Constants.REQUEST_CODE_EDIT_USER );
                }
                break;

            case R.id.btnUserDelete:
                if ( isUserSelected() )
                {
                    repository.deleteUser( new User( tvNickname.getText().toString(),
                            tvLogin.getText().toString(),
                            tvPassword.getText().toString() ) );
                }
                initializeSelectedUser();
                break;

            case R.id.btnBackUsersDirectory:
                intent = new Intent();
                setResult( RESULT_OK, intent );
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data )
    {
        switch ( requestCode )
        {

            case Constants.REQUEST_CODE_ADD_USER:
                if ( resultCode == RESULT_OK )
                {
                    initializeSelectedUser();
                }
                break;

            case Constants.REQUEST_CODE_EDIT_USER:
                if ( resultCode == RESULT_OK )
                {
                    initializeSelectedUser();
                }
                break;
        }
    }

    private void initializeSelectedUser()
    {
        selectedUserId = -1;

        tvNickname.setText( "" );
        tvLogin.setText( "" );
        tvPassword.setText( "" );

        listAllUsers.clear();
        listAllUsers.addAll( repository.getAllUsers() );
        listUserAdapter.notifyDataSetChanged();
    }

    private boolean isUserSelected()
    {
        return selectedUserId != -1;
    }

}
