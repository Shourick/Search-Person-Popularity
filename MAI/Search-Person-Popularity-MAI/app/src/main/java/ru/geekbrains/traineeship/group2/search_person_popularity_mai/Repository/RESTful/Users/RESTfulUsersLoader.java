package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.Users;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.UserHandle;

import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.User;

/**
 * Created by skubatko on 15/11/17
 */

public class RESTfulUsersLoader extends AsyncTask<Void, Void, List<User>>{

    private ProgressDialog progress;
    private Context context;

    public RESTfulUsersLoader( Context context ) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog( context );
        progress.setMessage( "Loading ..." );
        progress.show();
    }

    @Override
    protected List<User> doInBackground( Void... params ) {
        return null;
    }
}
