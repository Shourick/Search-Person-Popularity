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
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.PERSON_FOR_KEYWORD_ID;

public class KeywordsDirectoryAddKeywordActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etAddKeywordName;
    Button btnAddKeywordOK, btnAddKeywordCancel;

    int selectedPersonIdForKeywords;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_keywords_directory_add_keyword );

        etAddKeywordName = (EditText) findViewById( R.id.etAddKeywordName );

        btnAddKeywordOK = (Button) findViewById( R.id.btnAddKeywordOK );
        btnAddKeywordCancel = (Button) findViewById( R.id.btnAddKeywordCancel );

        btnAddKeywordOK.setOnClickListener( this );
        btnAddKeywordCancel.setOnClickListener( this );
        Bundle extras = getIntent().getExtras();

        if ( extras != null ) {
            selectedPersonIdForKeywords = extras.getInt( PERSON_FOR_KEYWORD_ID );
        }
    }

    @Override
    public void onClick( View v ) {
        Intent intent;

        switch ( v.getId() ) {

            case R.id.btnAddKeywordOK:
                repository.getKeywordRepository().addKeyword(
                        new Keyword( etAddKeywordName.getText().toString() ),
                        selectedPersonIdForKeywords );
                intent = new Intent();
                setResult( RESULT_OK, intent );
                finish();
                break;

            case R.id.btnAddKeywordCancel:
                intent = new Intent();
                setResult( RESULT_CANCELED, intent );
                finish();
                break;
        }
    }

}
