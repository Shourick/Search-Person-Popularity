package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.Keywords;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.R;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Keyword;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils.SQLiteRepository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.KEYWORD_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.KEYWORD_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.MAIN_DATABASE_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.PERSON_FOR_KEYWORD_ID;

public class KeywordsDirectoryEditKeywordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etEditKeywordName;

    private int mEditedKeywordId;
    private SQLiteRepository mMainRepository;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_keywords_directory_edit_keyword );

        etEditKeywordName = (EditText) findViewById( R.id.etEditKeywordName );

        Button btnEditKeywordOK = (Button) findViewById( R.id.btnEditKeywordOK );
        Button btnEditKeywordCancel = (Button) findViewById( R.id.btnEditKeywordCancel );

        btnEditKeywordOK.setOnClickListener( this );
        btnEditKeywordCancel.setOnClickListener( this );

        mMainRepository = new SQLiteRepository( this, MAIN_DATABASE_NAME );

        Bundle extras = getIntent().getExtras();
        if ( extras != null ) {
            mEditedKeywordId = extras.getInt( KEYWORD_ID );
            etEditKeywordName.setText( extras.getString( KEYWORD_NAME ) );
            int selectedPersonIdForKeywords = extras.getInt( PERSON_FOR_KEYWORD_ID );
            String mSelectedPerson = mMainRepository
                    .getPersonRepository()
                    .getPersonById( selectedPersonIdForKeywords )
                    .getName();
            TextView tvEditKeywordTitle = (TextView) findViewById( R.id.tvEditKeywordTitle );
            String mBaseTitle = getResources().getString( R.string.edit_keyword_title ) + " ";
            tvEditKeywordTitle.setText( mBaseTitle + mSelectedPerson );
        }
    }

    @Override
    public void onClick( View v ) {
        Intent intent;
        switch ( v.getId() ) {

            case R.id.btnEditKeywordOK:
                Keyword editedKeyword = new Keyword( etEditKeywordName.getText().toString() );
                editedKeyword.setId( mEditedKeywordId );

                mMainRepository.getKeywordRepository().updateKeyword( editedKeyword );

                intent = new Intent();
                setResult( RESULT_OK, intent );
                finish();
                break;

            case R.id.btnEditKeywordCancel:
                intent = new Intent();
                setResult( RESULT_CANCELED, intent );
                finish();
                break;

            default:
                break;
        }
    }
}
