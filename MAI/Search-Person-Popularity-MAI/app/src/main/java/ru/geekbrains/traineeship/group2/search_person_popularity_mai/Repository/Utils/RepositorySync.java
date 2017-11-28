package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.RESTfulRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils.SQLiteRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Utils.Data.KeywordsSync;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Utils.Data.PersonsSync;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Utils.Data.SitesSync;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Utils.Players.AdminsSync;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Utils.Players.UsersSync;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.MAIN_DATABASE_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.MESSAGE_SYNCRONIZING;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.SYNCHRONIZED_DATABASE_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.SYNC_FAILED_MSG;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.SYNC_OK_MSG;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.SYNC_RESULT_FALSE;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.SYNC_RESULT_OK;

/**
 * Created by skubatko on 15/11/17
 */

public class RepositorySync extends AsyncTask<Void, Void, Boolean> {

    private ProgressDialog mProgress;
    private Context mSyncContext;
    private SQLiteRepository mMainRepository;
    private SQLiteRepository mSynchronizedRepository;
    private RESTfulRepository mApiRepository;

    public RepositorySync( Context context ) {
        this.mSyncContext = context;
        mMainRepository = new SQLiteRepository( context, MAIN_DATABASE_NAME );
        mSynchronizedRepository = new SQLiteRepository( context, SYNCHRONIZED_DATABASE_NAME );
        mApiRepository = new RESTfulRepository();
    }

    @Override
    protected void onPreExecute() {
        mProgress = new ProgressDialog( mSyncContext );
        mProgress.setMessage( MESSAGE_SYNCRONIZING );
        mProgress.show();
    }

    @Override
    protected Boolean doInBackground( Void... params ) {

        try {

            new PersonsSync( mMainRepository, mSynchronizedRepository, mApiRepository ).execute();
            new KeywordsSync( mMainRepository, mSynchronizedRepository, mApiRepository ).execute();
            new SitesSync( mMainRepository, mSynchronizedRepository, mApiRepository ).execute();
            new UsersSync( mMainRepository, mSynchronizedRepository, mApiRepository ).execute();
            new AdminsSync( mMainRepository, mSynchronizedRepository, mApiRepository ).execute();

        } catch ( IOException e ) {
            e.printStackTrace();
            return SYNC_RESULT_FALSE;
        }

        return SYNC_RESULT_OK;
    }

    @Override
    protected void onPostExecute( Boolean result ) {
        mProgress.dismiss();
        mMainRepository.close();
        mSynchronizedRepository.close();
        if ( result == SYNC_RESULT_OK ) {
            Toast.makeText( mSyncContext, SYNC_OK_MSG, Toast.LENGTH_SHORT ).show();
        } else {
            Toast.makeText( mSyncContext, SYNC_FAILED_MSG, Toast.LENGTH_SHORT ).show();
        }
    }

}
