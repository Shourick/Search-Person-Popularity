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

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.MainActivity.repository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Constants.*;

public class SitesDirectoryActivity extends AppCompatActivity implements View.OnClickListener
{

    /**
     * databaseHandler применяем глобально во всех Activities для обмена данными с БД
     * при доступности Веб-сервиса поменять на класс, имплементирующий работу с Веб-сервисом
     */

    Button btnBackSitesDirectory, btnSiteAdd, btnSiteEdit, btnSiteDelete;
    TextView tvSiteName;

    ListView lvSitesList;
    List<Site> listAllSites;
    ArrayAdapter<Site> listSiteAdapter;

    int selectedSiteId;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sites_directory );

        btnBackSitesDirectory = (Button) findViewById( R.id.btnBackSitesDirectory );
        btnSiteAdd = (Button) findViewById( R.id.btnSiteAdd );
        btnSiteEdit = (Button) findViewById( R.id.btnSiteEdit );
        btnSiteDelete = (Button) findViewById( R.id.btnSiteDelete );

        tvSiteName = (TextView) findViewById( R.id.tvSiteName );

        lvSitesList = (ListView) findViewById( R.id.lvSitesList );

        btnBackSitesDirectory.setOnClickListener( this );
        btnSiteAdd.setOnClickListener( this );
        btnSiteEdit.setOnClickListener( this );
        btnSiteDelete.setOnClickListener( this );

        repository.getRepositoryUtils().showRepositoryInfo();

        listAllSites = repository.getSiteRepository().getAllSites();
        listSiteAdapter = new ArrayAdapter<Site>( this, android.R.layout.simple_list_item_1, listAllSites );
        lvSitesList.setAdapter( listSiteAdapter );
        selectedSiteId = -1;

        lvSitesList.setOnItemClickListener( new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick( AdapterView<?> adapterView, View itemClicked, int position, long id )
            {
                adapterView.requestFocusFromTouch();
                adapterView.setSelection( position );
            }
        } );

        lvSitesList.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected( AdapterView<?> adapterView, View itemSelected, int position, long id )
            {
                Site selectedSite = (Site) adapterView.getSelectedItem();
                selectedSiteId = selectedSite.getId();
                tvSiteName.setText( selectedSite.getName() );
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
            case R.id.btnSiteAdd:
                intent = new Intent( this, SitesDirectoryAddSiteActivity.class );
                startActivityForResult( intent, REQUEST_CODE_ADD_SITE );
                break;

            case R.id.btnSiteEdit:
                if ( isSiteSelected() )
                {
                    intent = new Intent( this, SitesDirectoryEditSiteActivity.class );
                    intent.putExtra( SITE_ID, selectedSiteId );
                    intent.putExtra( SITE_NAME, tvSiteName.getText().toString() );
                    startActivityForResult( intent, REQUEST_CODE_EDIT_SITE );
                }
                break;

            case R.id.btnSiteDelete:
                if ( isSiteSelected() )
                {
                    repository.getSiteRepository().deleteSite(
                            new Site( selectedSiteId, tvSiteName.getText().toString() ) );
                }
                initializeSelectedSite();
                break;

            case R.id.btnBackSitesDirectory:
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

            case REQUEST_CODE_ADD_SITE:
                if ( resultCode == RESULT_OK )
                {
                    initializeSelectedSite();
                }
                break;

            case REQUEST_CODE_EDIT_SITE:
                if ( resultCode == RESULT_OK )
                {
                    initializeSelectedSite();
                }
                break;
        }
    }

    private void initializeSelectedSite()
    {
        selectedSiteId = -1;

        tvSiteName.setText( "" );

        listAllSites.clear();
        listAllSites.addAll( repository.getSiteRepository().getAllSites() );
        listSiteAdapter.notifyDataSetChanged();
    }

    private boolean isSiteSelected()
    {
        return selectedSiteId != -1;
    }

}
