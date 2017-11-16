package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.Sites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.R;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Site;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.MainActivity.repository;

public class SitesDirectoryAddSiteActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etAddSiteName;
    Button btnAddSiteOK, btnAddSiteCancel;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sites_directory_add_site );

        etAddSiteName = (EditText) findViewById( R.id.etAddSiteName );

        btnAddSiteOK = (Button) findViewById( R.id.btnAddSiteOK );
        btnAddSiteCancel = (Button) findViewById( R.id.btnAddSiteCancel );

        btnAddSiteOK.setOnClickListener( this );
        btnAddSiteCancel.setOnClickListener( this );
    }

    @Override
    public void onClick( View v ) {
        Intent intent;

        switch ( v.getId() ) {

            case R.id.btnAddSiteOK:
                repository.getSiteRepository().addSite( new Site( etAddSiteName.getText().toString() ) );
                intent = new Intent();
                setResult( RESULT_OK, intent );
                finish();
                break;

            case R.id.btnAddSiteCancel:
                intent = new Intent();
                setResult( RESULT_CANCELED, intent );
                finish();
                break;
        }
    }
}
