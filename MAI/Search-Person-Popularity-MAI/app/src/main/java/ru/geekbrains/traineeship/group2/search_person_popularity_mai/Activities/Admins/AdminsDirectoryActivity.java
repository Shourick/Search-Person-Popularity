package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.Admins;

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
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.Admin;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.MainActivity.repository;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.ADMIN_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.ADMIN_LOGIN;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.ADMIN_NICKNAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.ADMIN_PASSWORD;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.REQUEST_CODE_ADD_ADMIN;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.REQUEST_CODE_EDIT_ADMIN;

public class AdminsDirectoryActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * repository применяем глобально во всех Activities для обмена данными с БД
     * при доступности Веб-сервиса поменять на класс, имплементирующий работу с Веб-сервисом
     */

    Button btnBackAdminsDirectory, btnAdminAdd, btnAdminEdit, btnAdminDelete;
    TextView tvNickname, tvLogin, tvPassword;

    ListView lvAdminsList;
    List<Admin> listAllAdmins;
    ArrayAdapter<Admin> listAdminAdapter;

    int selectedAdminId;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_admins_directory );

        btnBackAdminsDirectory = (Button) findViewById( R.id.btnBackAdminsDirectory );
        btnAdminAdd = (Button) findViewById( R.id.btnAdminAdd );
        btnAdminEdit = (Button) findViewById( R.id.btnAdminEdit );
        btnAdminDelete = (Button) findViewById( R.id.btnAdminDelete );

        tvNickname = (TextView) findViewById( R.id.tvNickname );
        tvLogin = (TextView) findViewById( R.id.tvLogin );
        tvPassword = (TextView) findViewById( R.id.tvPassword );

        lvAdminsList = (ListView) findViewById( R.id.lvAdminsList );

        btnBackAdminsDirectory.setOnClickListener( this );
        btnAdminAdd.setOnClickListener( this );
        btnAdminEdit.setOnClickListener( this );
        btnAdminDelete.setOnClickListener( this );

        repository.showRepositoryInfo();

        listAllAdmins = repository.getAdminRepository().getAllAdmins();
        listAdminAdapter = new ArrayAdapter<Admin>( this, android.R.layout.simple_list_item_1, listAllAdmins );
        lvAdminsList.setAdapter( listAdminAdapter );
        selectedAdminId = -1;

        lvAdminsList.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> adapterView, View itemClicked, int position, long id ) {
                adapterView.requestFocusFromTouch();
                adapterView.setSelection( position );
            }
        } );

        lvAdminsList.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView<?> adapterView, View itemSelected, int position, long id ) {
                Admin selectedAdmin = (Admin) adapterView.getSelectedItem();
                selectedAdminId = selectedAdmin.getId();
                tvNickname.setText( selectedAdmin.getNickName() );
                tvLogin.setText( selectedAdmin.getLogin() );
                tvPassword.setText( selectedAdmin.getPassword() );
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
            case R.id.btnAdminAdd:
                intent = new Intent( this, AdminsDirectoryAddAdminActivity.class );
                startActivityForResult( intent, REQUEST_CODE_ADD_ADMIN );
                break;

            case R.id.btnAdminEdit:
                if ( isAdminSelected() ) {
                    intent = new Intent( this, AdminsDirectoryEditAdminActivity.class );
                    intent.putExtra( ADMIN_ID, selectedAdminId );
                    intent.putExtra( ADMIN_NICKNAME, tvNickname.getText().toString() );
                    intent.putExtra( ADMIN_LOGIN, tvLogin.getText().toString() );
                    intent.putExtra( ADMIN_PASSWORD, tvPassword.getText().toString() );
                    startActivityForResult( intent, REQUEST_CODE_EDIT_ADMIN );
                }
                break;

            case R.id.btnAdminDelete:
                if ( isAdminSelected() ) {
                    repository.getAdminRepository().deleteAdmin( new Admin( selectedAdminId,
                            tvNickname.getText().toString(),
                            tvLogin.getText().toString(),
                            tvPassword.getText().toString() ) );
                }
                initializeSelectedAdmin();
                break;

            case R.id.btnBackAdminsDirectory:
                intent = new Intent();
                setResult( RESULT_OK, intent );
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
        switch ( requestCode ) {

            case REQUEST_CODE_ADD_ADMIN:
                if ( resultCode == RESULT_OK ) {
                    initializeSelectedAdmin();
                }
                break;

            case REQUEST_CODE_EDIT_ADMIN:
                if ( resultCode == RESULT_OK ) {
                    initializeSelectedAdmin();
                }
                break;
        }
    }

    private void initializeSelectedAdmin() {
        selectedAdminId = -1;

        tvNickname.setText( "" );
        tvLogin.setText( "" );
        tvPassword.setText( "" );

        listAllAdmins.clear();
        listAllAdmins.addAll( repository.getAdminRepository().getAllAdmins() );
        listAdminAdapter.notifyDataSetChanged();
    }

    private boolean isAdminSelected() {
        return selectedAdminId != -1;
    }
}
