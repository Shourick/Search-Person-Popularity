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
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Constants.SITE_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Constants.SITE_NAME;

public class SitesDirectoryEditSiteActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etEditSiteName;
    Button btnEditSiteOK, btnEditSiteCancel;
    int editedSiteId;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sites_directory_edit_site );

        etEditSiteName = (EditText) findViewById( R.id.etEditSiteName );

        btnEditSiteOK = (Button) findViewById( R.id.btnEditSiteOK );
        btnEditSiteCancel = (Button) findViewById( R.id.btnEditSiteCancel );

        btnEditSiteOK.setOnClickListener( this );
        btnEditSiteCancel.setOnClickListener( this );

        Bundle extras = getIntent().getExtras();
        if ( extras != null ) {
            editedSiteId = extras.getInt( SITE_ID );
            etEditSiteName.setText( extras.getString( SITE_NAME ) );
        }
    }

    @Override
    public void onClick( View v ) {
        Intent intent;
        switch ( v.getId() ) {

            case R.id.btnEditSiteOK:
                Site editedSite = new Site( etEditSiteName.getText().toString() );
                editedSite.setId( editedSiteId );

                repository.getSiteRepository().updateSite( editedSite );

                intent = new Intent();
                setResult( RESULT_OK, intent );
                finish();
                break;

            case R.id.btnEditSiteCancel:
                intent = new Intent();
                setResult( RESULT_CANCELED, intent );
                finish();
                break;
        }
    }
}
