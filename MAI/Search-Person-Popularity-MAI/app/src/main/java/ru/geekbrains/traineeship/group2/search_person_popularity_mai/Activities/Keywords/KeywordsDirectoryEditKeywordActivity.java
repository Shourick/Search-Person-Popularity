package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.Keywords;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.R;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Keyword;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.MainActivity.repository;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Constants.KEYWORD_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Constants.KEYWORD_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Constants.PERSON_FOR_KEYWORD_ID;

public class KeywordsDirectoryEditKeywordActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etEditKeywordName;
    Button btnEditKeywordOK, btnEditKeywordCancel;
    int editedKeywordId, selectedPersonIdForKeywords;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_keywords_directory_edit_keyword );

        etEditKeywordName = (EditText) findViewById( R.id.etEditKeywordName );

        btnEditKeywordOK = (Button) findViewById( R.id.btnEditKeywordOK );
        btnEditKeywordCancel = (Button) findViewById( R.id.btnEditKeywordCancel );

        btnEditKeywordOK.setOnClickListener( this );
        btnEditKeywordCancel.setOnClickListener( this );

        Bundle extras = getIntent().getExtras();
        if ( extras != null ) {
            editedKeywordId = extras.getInt( KEYWORD_ID );
            etEditKeywordName.setText( extras.getString( KEYWORD_NAME ) );
            selectedPersonIdForKeywords = extras.getInt( PERSON_FOR_KEYWORD_ID );
        }
    }

    @Override
    public void onClick( View v ) {
        Intent intent;
        switch ( v.getId() ) {

            case R.id.btnEditKeywordOK:
                Keyword editedKeyword = new Keyword( etEditKeywordName.getText().toString() );
                editedKeyword.setId( editedKeywordId );

                repository.getKeywordRepository().updateKeyword( editedKeyword );

                intent = new Intent();
                setResult( RESULT_OK, intent );
                finish();
                break;

            case R.id.btnEditKeywordCancel:
                intent = new Intent();
                setResult( RESULT_CANCELED, intent );
                finish();
                break;
        }
    }
}
