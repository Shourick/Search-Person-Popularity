package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.Keywords;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Keyword;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.MainActivity.repository;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Constants.API_URL_BASE;

/**
 * Created by skubatko on 15/11/17
 */

public class RESTfulKeywordsLoader extends AsyncTask<Void, Void, List<Keyword>> {

    private ProgressDialog progress;
    private Context context;

    public RESTfulKeywordsLoader( Context context ) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog( context );
        progress.setMessage( "Loading ..." );
        progress.show();
    }

    @Override
    protected List<Keyword> doInBackground( Void... params ) {
        List<Keyword> keywords = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( API_URL_BASE )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        IKeywordRestAPI keywordRestAPI = retrofit.create( IKeywordRestAPI.class );
        try {
            keywords = keywordRestAPI.getAllKeywords().execute().body();
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        try {
            Thread.sleep( 1000 );
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }

        return keywords;
    }

    @Override
    protected void onPostExecute( List<Keyword> keywords ) {
        for ( Keyword o : keywords ) {
            repository.getKeywordRepository().addKeyword( o, o.getPersonId() );
        }
        progress.dismiss();
    }
}
