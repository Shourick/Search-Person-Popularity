package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.AdminAuthorization;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Constants;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.R;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.Admin;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.MainActivity.repository;

public class AdminLoginActivity extends AppCompatActivity implements View.OnClickListener
{

    private EditText etAdminLogin, etAdminPassword;
    private Button btnAdminLogin;

    private int numberOfAuthorizationTries;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_admin_login );

        etAdminLogin = (EditText) findViewById( R.id.etAdminLogin );
        etAdminPassword = (EditText) findViewById( R.id.etAdminPassword );
        btnAdminLogin = (Button) findViewById( R.id.btnAdminLogin );

        btnAdminLogin.setOnClickListener( this );

        numberOfAuthorizationTries = 0;
    }

    @Override
    public void onClick( View v )
    {
        numberOfAuthorizationTries++;

        switch ( v.getId() )
        {
            case R.id.btnAdminLogin:
                if ( isAuthorizationDataValid() )
                {
                    AdminAuthorization.setAuthorized( this );
                    Intent main = new Intent( this, MainActivity.class );
                    startActivity( main );
                    finish();
                } else
                {
                    if ( numberOfAuthorizationTries < Constants.MAX_OF_ADMIN_AUTHORIZATION_TRIES )
                    {
                        Toast.makeText( v.getContext(), "Неверные данные автораизации !!!", Toast.LENGTH_SHORT ).show();
                    } else
                    {
                        authorizationFails();
                    }
                }

                break;
        }

    }

    private boolean isAuthorizationDataValid()
    {
        List<Admin> adminList = repository.getAllAdmins();
        for ( Admin o : adminList )
        {
            if ( o.getLogin().equals( etAdminLogin.getText().toString() ) &&
                    o.getPassword().equals( etAdminPassword.getText().toString() ) )
            {
                return true;
            }
        }
        return false;
    }

    private void authorizationFails()
    {
        etAdminLogin.setEnabled( false );
        etAdminPassword.setEnabled( false );
        btnAdminLogin.setEnabled( false );
    }
}
