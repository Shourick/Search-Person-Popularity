package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.Sites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.R;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Site;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils.SQLiteRepository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.MAIN_DATABASE_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.SITE_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.SITE_NAME;

public class SitesDirectoryEditSiteActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etEditSiteName;

    private SQLiteRepository mMainRepository;
    private int mEditedSiteId;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sites_directory_edit_site );

        etEditSiteName = (EditText) findViewById( R.id.etEditSiteName );

        Button btnEditSiteOK = (Button) findViewById( R.id.btnEditSiteOK );
        Button btnEditSiteCancel = (Button) findViewById( R.id.btnEditSiteCancel );

        btnEditSiteOK.setOnClickListener( this );
        btnEditSiteCancel.setOnClickListener( this );

        mMainRepository = new SQLiteRepository( this, MAIN_DATABASE_NAME );

        Bundle extras = getIntent().getExtras();
        if ( extras != null ) {
            mEditedSiteId = extras.getInt( SITE_ID );
            etEditSiteName.setText( extras.getString( SITE_NAME ) );
        }
    }

    @Override
    public void onClick( View v ) {
        Intent intent;
        switch ( v.getId() ) {

            case R.id.btnEditSiteOK:
                Site editedSite = new Site( etEditSiteName.getText().toString() );
                editedSite.setId( mEditedSiteId );

                mMainRepository.getSiteRepository().updateSite( editedSite );

                intent = new Intent();
                setResult( RESULT_OK, intent );
                finish();
                break;

            case R.id.btnEditSiteCancel:
                intent = new Intent();
                setResult( RESULT_CANCELED, intent );
                finish();
                break;

            default:
                break;
        }
    }
}
