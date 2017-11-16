package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.RESTfulRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils.SQLiteRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Utils.Data.KeywordsSync;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Utils.Data.PersonsSync;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Utils.Data.SitesSync;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Utils.Players.AdminsSync;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Utils.Players.UsersSync;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.MESSAGE_SYNCRONIZING;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.SYNCHRONIZED_DATABASE_NAME;

/**
 * Created by skubatko on 15/11/17
 */

public class RepositorySync extends AsyncTask<Void, Void, Void> {

    private ProgressDialog progress;
    private Context context;
    private SQLiteRepository synchronizedRepository;
    private RESTfulRepository apiRepository;

    public RepositorySync( Context context ) {
        this.context = context;
        synchronizedRepository = new SQLiteRepository( context, SYNCHRONIZED_DATABASE_NAME );
        apiRepository = new RESTfulRepository();
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog( context );
        progress.setMessage( MESSAGE_SYNCRONIZING );
        progress.show();
    }

    @Override
    protected Void doInBackground( Void... params ) {

        try {

            new PersonsSync( synchronizedRepository, apiRepository ).execute();
            new KeywordsSync( synchronizedRepository, apiRepository ).execute();
            new SitesSync( synchronizedRepository, apiRepository ).execute();
            new UsersSync( synchronizedRepository,apiRepository ).execute();
            new AdminsSync( synchronizedRepository,apiRepository ).execute();

        } catch ( IOException e ) {
            e.printStackTrace();
        }

        try {
            Thread.sleep( 1000 );
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute( Void aVoid ) {
        progress.dismiss();
        synchronizedRepository.close();
    }

}
