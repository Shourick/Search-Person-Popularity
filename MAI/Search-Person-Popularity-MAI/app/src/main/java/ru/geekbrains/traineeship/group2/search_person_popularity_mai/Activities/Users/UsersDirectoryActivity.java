package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.Users;

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

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.R;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.User;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils.SQLiteRepository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.ITEM_NOT_SELECTED;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.MAIN_DATABASE_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.REQUEST_CODE_ADD_USER;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.REQUEST_CODE_EDIT_USER;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.USER_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.USER_LOGIN;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.USER_NICKNAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.USER_PASSWORD;

public class UsersDirectoryActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvNickname, tvLogin, tvPassword;

    private List<User> mListAllUsers;
    private ArrayAdapter<User> mListUserAdapter;
    private SQLiteRepository mMainRepository;
    private int mSelectedUserId;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_users );

        Button btnUserAdd = (Button) findViewById( R.id.btnUserAdd );
        Button btnUserEdit = (Button) findViewById( R.id.btnUserEdit );
        Button btnUserDelete = (Button) findViewById( R.id.btnUserDelete );
        Button btnBackUsersDirectory = (Button) findViewById( R.id.btnBackUsersDirectory );

        btnBackUsersDirectory.setOnClickListener( this );
        btnUserAdd.setOnClickListener( this );
        btnUserEdit.setOnClickListener( this );
        btnUserDelete.setOnClickListener( this );

        tvNickname = (TextView) findViewById( R.id.tvNickname );
        tvLogin = (TextView) findViewById( R.id.tvLogin );
        tvPassword = (TextView) findViewById( R.id.tvPassword );

        ListView lvUsersList = (ListView) findViewById( R.id.lvUsersList );

        mMainRepository = new SQLiteRepository( this, MAIN_DATABASE_NAME );
        mListAllUsers = mMainRepository.getUserRepository().getAllUsers();
        mListUserAdapter = new ArrayAdapter<>( this,
                android.R.layout.simple_list_item_1,
                mListAllUsers );
        lvUsersList.setAdapter( mListUserAdapter );

        mSelectedUserId = ITEM_NOT_SELECTED;

        lvUsersList.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> adapterView, View itemClicked, int position, long id ) {
                adapterView.requestFocusFromTouch();
                adapterView.setSelection( position );
            }
        } );

        lvUsersList.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView<?> adapterView, View itemSelected, int position, long id ) {
                User selectedUser = (User) adapterView.getSelectedItem();
                mSelectedUserId = selectedUser.getId();
                tvNickname.setText( selectedUser.getNickName() );
                tvLogin.setText( selectedUser.getLogin() );
                tvPassword.setText( selectedUser.getPassword() );
            }

            @Override
            public void onNothingSelected( AdapterView<?> parent ) {
            }
        } );

    }

    @Override
    public void onClick( View v ) {
        Intent intent;
        switch ( v.getId() ) {
            case R.id.btnUserAdd:
                intent = new Intent( this, UsersDirectoryAddUserActivity.class );
                startActivityForResult( intent, REQUEST_CODE_ADD_USER );
                break;

            case R.id.btnUserEdit:
                if ( isUserSelected() ) {
                    intent = new Intent( this, UsersDirectoryEditUserActivity.class );
                    intent.putExtra( USER_ID, mSelectedUserId );
                    intent.putExtra( USER_NICKNAME, tvNickname.getText().toString() );
                    intent.putExtra( USER_LOGIN, tvLogin.getText().toString() );
                    intent.putExtra( USER_PASSWORD, tvPassword.getText().toString() );
                    startActivityForResult( intent, REQUEST_CODE_EDIT_USER );
                }
                break;

            case R.id.btnUserDelete:
                if ( isUserSelected() ) {
                    mMainRepository.getUserRepository().deleteUser( new User( mSelectedUserId,
                            tvNickname.getText().toString(),
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

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
        switch ( requestCode ) {

            case REQUEST_CODE_ADD_USER:
                if ( resultCode == RESULT_OK ) {
                    initializeSelectedUser();
                }
                break;

            case REQUEST_CODE_EDIT_USER:
                if ( resultCode == RESULT_OK ) {
                    initializeSelectedUser();
                }
                break;

            default:
                break;
        }
    }

    private void initializeSelectedUser() {
        mSelectedUserId = ITEM_NOT_SELECTED;

        tvNickname.setText( EMPTY_NAME );
        tvLogin.setText( EMPTY_NAME );
        tvPassword.setText( EMPTY_NAME );

        mListAllUsers.clear();
        mListAllUsers.addAll( mMainRepository.getUserRepository().getAllUsers() );
        mListUserAdapter.notifyDataSetChanged();
    }

    private boolean isUserSelected() {
        return mSelectedUserId != ITEM_NOT_SELECTED;
    }

}
