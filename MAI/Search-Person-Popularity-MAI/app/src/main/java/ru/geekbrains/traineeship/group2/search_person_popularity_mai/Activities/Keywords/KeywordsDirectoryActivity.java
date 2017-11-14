package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.Keywords;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.R;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Keyword;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Person;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Activities.MainActivity.repository;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Constants.KEYWORD_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Constants.KEYWORD_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Constants.PERSON_FOR_KEYWORD_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Constants.REQUEST_CODE_ADD_KEYWORD;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Constants.REQUEST_CODE_EDIT_KEYWORD;

public class KeywordsDirectoryActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * repository применяем глобально во всех Activities для обмена данными с БД
     * при доступности Веб-сервиса поменять на класс, имплементирующий работу с Веб-сервисом
     */
    Button btnBackKeywordsDirectory, btnKeywordAdd, btnKeywordEdit, btnKeywordDelete;
    TextView tvKeywordName;

    Spinner spPersonForKeywords;
    List<Person> listAllPersonsForKeywords;
    ArrayAdapter<Person> listPersonForKeywordsAdapter;

    ListView lvKeywordsByPersonList;
    List<Keyword> listKeywordsByPerson;
    ArrayAdapter<Keyword> listKeywordsByPersonAdapter;

    int selectedPersonForKeywordsId;
    int selectedKeywordId;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_keywords_directory );

        btnBackKeywordsDirectory = (Button) findViewById( R.id.btnBackKeywordsDirectory );
        btnBackKeywordsDirectory.setOnClickListener( this );

        btnKeywordAdd = (Button) findViewById( R.id.btnKeywordAdd );
        btnKeywordEdit = (Button) findViewById( R.id.btnKeywordEdit );
        btnKeywordDelete = (Button) findViewById( R.id.btnKeywordDelete );

        tvKeywordName = (TextView) findViewById( R.id.tvKeywordName );

        spPersonForKeywords = (Spinner) findViewById( R.id.spPersonForKeywords );

        lvKeywordsByPersonList = (ListView) findViewById( R.id.lvKeywordsByPersonList );

        btnBackKeywordsDirectory.setOnClickListener( this );
        btnKeywordAdd.setOnClickListener( this );
        btnKeywordEdit.setOnClickListener( this );
        btnKeywordDelete.setOnClickListener( this );

        repository.getRepositoryUtils().showRepositoryInfo();

        listAllPersonsForKeywords = repository.getPersonRepository().getAllPersons();
        listPersonForKeywordsAdapter = new ArrayAdapter<Person>( this,
                android.R.layout.simple_spinner_item,
                listAllPersonsForKeywords );
        listPersonForKeywordsAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spPersonForKeywords.setAdapter( listPersonForKeywordsAdapter );
        selectedPersonForKeywordsId = listAllPersonsForKeywords.get( 0 ).getId();

        spPersonForKeywords.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView<?> adapterView, View view, int position, long id ) {
                Person selectedPerson = (Person) adapterView.getSelectedItem();
                selectedPersonForKeywordsId = selectedPerson.getId();
                initializeSelectedKeyword();
            }

            @Override
            public void onNothingSelected( AdapterView<?> parent ) {
            }
        } );

        listKeywordsByPerson = repository.getKeywordRepository().getPersonKeywords( selectedPersonForKeywordsId );
        listKeywordsByPersonAdapter = new ArrayAdapter<Keyword>( this,
                android.R.layout.simple_list_item_1,
                listKeywordsByPerson );
        lvKeywordsByPersonList.setAdapter( listKeywordsByPersonAdapter );
        selectedKeywordId = -1;

        lvKeywordsByPersonList.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> adapterView, View itemClicked, int position, long id ) {
                adapterView.requestFocusFromTouch();
                adapterView.setSelection( position );
            }
        } );

        lvKeywordsByPersonList.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView<?> adapterView, View itemSelected, int position, long id ) {
                Keyword selectedKeyword = (Keyword) adapterView.getSelectedItem();
                selectedKeywordId = selectedKeyword.getId();
                tvKeywordName.setText( selectedKeyword.getName() );
            }

            @Override
            public void onNothingSelected( AdapterView<?> parent ) {
            }
        } );
    }

    @Override
    public void onClick( View v ) {
        Intent intent;

        switch ( v.getId() ) {
            case R.id.btnKeywordAdd:
                intent = new Intent( this, KeywordsDirectoryAddKeywordActivity.class );
                intent.putExtra( PERSON_FOR_KEYWORD_ID, selectedPersonForKeywordsId );
                startActivityForResult( intent, REQUEST_CODE_ADD_KEYWORD );
                break;

            case R.id.btnKeywordEdit:
                if ( isKeywordSelected() ) {
                    intent = new Intent( this, KeywordsDirectoryEditKeywordActivity.class );
                    intent.putExtra( KEYWORD_ID, selectedKeywordId );
                    intent.putExtra( KEYWORD_NAME, tvKeywordName.getText().toString() );
                    intent.putExtra( PERSON_FOR_KEYWORD_ID, selectedPersonForKeywordsId );
                    startActivityForResult( intent, REQUEST_CODE_EDIT_KEYWORD );
                }
                break;

            case R.id.btnKeywordDelete:
                if ( isKeywordSelected() ) {
                    repository.getKeywordRepository().deleteKeyword(
                            new Keyword( selectedKeywordId, tvKeywordName.getText().toString(), selectedPersonForKeywordsId ) );
                }
                initializeSelectedKeyword();
                break;

            case R.id.btnBackKeywordsDirectory:
                intent = new Intent();
                setResult( RESULT_OK, intent );
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
        switch ( requestCode ) {

            case REQUEST_CODE_ADD_KEYWORD:
                if ( resultCode == RESULT_OK ) {
                    initializeSelectedKeyword();
                }
                break;

            case REQUEST_CODE_EDIT_KEYWORD:
                if ( resultCode == RESULT_OK ) {
                    initializeSelectedKeyword();
                }
                break;
        }
    }

    private void initializeSelectedKeyword() {
        selectedKeywordId = -1;

        tvKeywordName.setText( "" );

        listKeywordsByPerson.clear();
        listKeywordsByPerson.addAll( repository.getKeywordRepository().getPersonKeywords( selectedPersonForKeywordsId ) );
        listKeywordsByPersonAdapter.notifyDataSetChanged();
    }

    private boolean isKeywordSelected() {
        return selectedKeywordId != -1;
    }


}
