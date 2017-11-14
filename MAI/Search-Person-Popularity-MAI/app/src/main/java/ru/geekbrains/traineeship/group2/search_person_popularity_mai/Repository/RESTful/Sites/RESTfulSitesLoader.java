package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.Sites;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Site;

/**
 * Created by skubatko on 15/11/17
 */

public class RESTfulSitesLoader extends AsyncTask<Void, Void, List<Site>>{

    private ProgressDialog progress;
    private Context context;

    public RESTfulSitesLoader( Context context ) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog( context );
        progress.setMessage( "Loading ..." );
        progress.show();
    }

    @Override
    protected List<Site> doInBackground( Void... params ) {
        return null;
    }
}
