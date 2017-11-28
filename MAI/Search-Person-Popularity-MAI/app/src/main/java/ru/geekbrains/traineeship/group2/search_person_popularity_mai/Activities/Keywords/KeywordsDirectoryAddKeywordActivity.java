package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.Keywords;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.R;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Keyword;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils.SQLiteRepository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.MAIN_DATABASE_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.PERSON_FOR_KEYWORD_ID;

public class KeywordsDirectoryAddKeywordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etAddKeywordName;

    private int mSelectedPersonIdForKeywords;
    private SQLiteRepository mMainRepository;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_keywords_directory_add_keyword );

        Button btnAddKeywordOK = (Button) findViewById( R.id.btnAddKeywordOK );
        Button btnAddKeywordCancel = (Button) findViewById( R.id.btnAddKeywordCancel );

        btnAddKeywordOK.setOnClickListener( this );
        btnAddKeywordCancel.setOnClickListener( this );

        etAddKeywordName = (EditText) findViewById( R.id.etAddKeywordName );

        mMainRepository = new SQLiteRepository( this, MAIN_DATABASE_NAME );

        Bundle extras = getIntent().getExtras();
        if ( extras != null ) {
            mSelectedPersonIdForKeywords = extras.getInt( PERSON_FOR_KEYWORD_ID );
        }
    }

    @Override
    public void onClick( View v ) {
        Intent intent;

        switch ( v.getId() ) {

            case R.id.btnAddKeywordOK:
                mMainRepository.getKeywordRepository().addKeyword(
                        new Keyword( etAddKeywordName.getText().toString() ),
                        mSelectedPersonIdForKeywords );
                intent = new Intent();
                setResult( RESULT_OK, intent );
                finish();
                break;

            case R.id.btnAddKeywordCancel:
                intent = new Intent();
                setResult( RESULT_CANCELED, intent );
                finish();
                break;

            default:
                break;
        }
    }

}
