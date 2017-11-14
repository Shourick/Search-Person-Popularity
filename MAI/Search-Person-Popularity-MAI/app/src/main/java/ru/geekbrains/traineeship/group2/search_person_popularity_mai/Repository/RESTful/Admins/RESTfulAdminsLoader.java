package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.Admins;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.Admin;

/**
 * Created by skubatko on 15/11/17
 */

public class RESTfulAdminsLoader extends AsyncTask<Void, Void, List<Admin>>{

    private ProgressDialog progress;
    private Context context;

    public RESTfulAdminsLoader( Context context ) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog( context );
        progress.setMessage( "Loading ..." );
        progress.show();
    }

    @Override
    protected List<Admin> doInBackground( Void... params ) {
        return null;
    }
}
