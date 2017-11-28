package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.Sites;

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
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Site;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils.SQLiteRepository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.ITEM_NOT_SELECTED;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.MAIN_DATABASE_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.REQUEST_CODE_ADD_SITE;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.REQUEST_CODE_EDIT_SITE;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.SITE_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.SITE_NAME;

public class SitesDirectoryActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvSiteName;

    private List<Site> mListAllSites;
    private ArrayAdapter<Site> mListSiteAdapter;
    private SQLiteRepository mMainRepository;
    private int mSelectedSiteId;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sites_directory );

        Button btnSiteAdd = (Button) findViewById( R.id.btnSiteAdd );
        Button btnSiteEdit = (Button) findViewById( R.id.btnSiteEdit );
        Button btnSiteDelete = (Button) findViewById( R.id.btnSiteDelete );
        Button btnBackSitesDirectory = (Button) findViewById( R.id.btnBackSitesDirectory );

        btnSiteAdd.setOnClickListener( this );
        btnSiteEdit.setOnClickListener( this );
        btnSiteDelete.setOnClickListener( this );
        btnBackSitesDirectory.setOnClickListener( this );

        tvSiteName = (TextView) findViewById( R.id.tvSiteName );

        ListView lvSitesList = (ListView) findViewById( R.id.lvSitesList );
        mMainRepository = new SQLiteRepository( this, MAIN_DATABASE_NAME );
        mListAllSites = mMainRepository.getSiteRepository().getAllSites();
        mListSiteAdapter = new ArrayAdapter<>( this,
                android.R.layout.simple_list_item_1,
                mListAllSites );
        lvSitesList.setAdapter( mListSiteAdapter );

        mSelectedSiteId = ITEM_NOT_SELECTED;

        lvSitesList.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> adapterView, View itemClicked, int position, long id ) {
                adapterView.requestFocusFromTouch();
                adapterView.setSelection( position );
            }
        } );

        lvSitesList.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView<?> adapterView, View itemSelected, int position, long id ) {
                Site selectedSite = (Site) adapterView.getSelectedItem();
                mSelectedSiteId = selectedSite.getId();
                tvSiteName.setText( selectedSite.getName() );
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
            case R.id.btnSiteAdd:
                intent = new Intent( this, SitesDirectoryAddSiteActivity.class );
                startActivityForResult( intent, REQUEST_CODE_ADD_SITE );
                break;

            case R.id.btnSiteEdit:
                if ( isSiteSelected() ) {
                    intent = new Intent( this, SitesDirectoryEditSiteActivity.class );
                    intent.putExtra( SITE_ID, mSelectedSiteId );
                    intent.putExtra( SITE_NAME, tvSiteName.getText().toString() );
                    startActivityForResult( intent, REQUEST_CODE_EDIT_SITE );
                }
                break;

            case R.id.btnSiteDelete:
                if ( isSiteSelected() ) {
                    mMainRepository.getSiteRepository().deleteSite(
                            new Site( mSelectedSiteId, tvSiteName.getText().toString() ) );
                }
                initializeSelectedSite();
                break;

            case R.id.btnBackSitesDirectory:
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

            case REQUEST_CODE_ADD_SITE:
                if ( resultCode == RESULT_OK ) {
                    initializeSelectedSite();
                }
                break;

            case REQUEST_CODE_EDIT_SITE:
                if ( resultCode == RESULT_OK ) {
                    initializeSelectedSite();
                }
                break;

            default:
                break;
        }
    }

    private void initializeSelectedSite() {
        mSelectedSiteId = ITEM_NOT_SELECTED;

        tvSiteName.setText( EMPTY_NAME );

        mListAllSites.clear();
        mListAllSites.addAll( mMainRepository.getSiteRepository().getAllSites() );
        mListSiteAdapter.notifyDataSetChanged();
    }

    private boolean isSiteSelected() {
        return mSelectedSiteId != ITEM_NOT_SELECTED;
    }

}
