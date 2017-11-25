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
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Utils.SQLiteRepository;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.ITEM_NOT_SELECTED;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.KEYWORD_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.KEYWORD_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.MAIN_DATABASE_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.PERSON_FOR_KEYWORD_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.REQUEST_CODE_ADD_KEYWORD;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.REQUEST_CODE_EDIT_KEYWORD;

public class KeywordsDirectoryActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvKeywordName;

    private List<Keyword> mListKeywordsByPerson;
    private ArrayAdapter<Keyword> mListKeywordsByPersonAdapter;
    private int mSelectedPersonForKeywordsId;
    private int mSelectedKeywordId;
    private SQLiteRepository mMainRepository;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_keywords_directory );

        Button btnKeywordAdd = (Button) findViewById( R.id.btnKeywordAdd );
        Button btnKeywordEdit = (Button) findViewById( R.id.btnKeywordEdit );
        Button btnKeywordDelete = (Button) findViewById( R.id.btnKeywordDelete );
        Button btnBackKeywordsDirectory = (Button) findViewById( R.id.btnBackKeywordsDirectory );

        btnKeywordAdd.setOnClickListener( this );
        btnKeywordEdit.setOnClickListener( this );
        btnKeywordDelete.setOnClickListener( this );
        btnBackKeywordsDirectory.setOnClickListener( this );

        mMainRepository = new SQLiteRepository( this, MAIN_DATABASE_NAME );

        tvKeywordName = (TextView) findViewById( R.id.tvKeywordName );
        Spinner spPersonForKeywords = (Spinner) findViewById( R.id.spPersonForKeywords );
        ListView lvKeywordsByPersonList = (ListView) findViewById( R.id.lvKeywordsByPersonList );

        List<Person> listAllPersonsForKeywords = mMainRepository.getPersonRepository().getAllPersons();
        ArrayAdapter<Person> listPersonForKeywordsAdapter = new ArrayAdapter<>( this,
                android.R.layout.simple_spinner_item,
                listAllPersonsForKeywords );
        listPersonForKeywordsAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spPersonForKeywords.setAdapter( listPersonForKeywordsAdapter );
        mSelectedPersonForKeywordsId = listAllPersonsForKeywords.get( 0 ).getId();

        spPersonForKeywords.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView<?> adapterView, View view, int position, long id ) {
                Person selectedPerson = (Person) adapterView.getSelectedItem();
                mSelectedPersonForKeywordsId = selectedPerson.getId();
                initializeSelectedKeyword();
            }

            @Override
            public void onNothingSelected( AdapterView<?> parent ) {
            }
        } );

        mListKeywordsByPerson = mMainRepository
                .getKeywordRepository()
                .getPersonKeywords( mSelectedPersonForKeywordsId );
        mListKeywordsByPersonAdapter = new ArrayAdapter<Keyword>( this,
                android.R.layout.simple_list_item_1,
                mListKeywordsByPerson );
        lvKeywordsByPersonList.setAdapter( mListKeywordsByPersonAdapter );

        mSelectedKeywordId = ITEM_NOT_SELECTED;

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
                mSelectedKeywordId = selectedKeyword.getId();
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
                intent.putExtra( PERSON_FOR_KEYWORD_ID, mSelectedPersonForKeywordsId );
                startActivityForResult( intent, REQUEST_CODE_ADD_KEYWORD );
                break;

            case R.id.btnKeywordEdit:
                if ( isKeywordSelected() ) {
                    intent = new Intent( this, KeywordsDirectoryEditKeywordActivity.class );
                    intent.putExtra( KEYWORD_ID, mSelectedKeywordId );
                    intent.putExtra( KEYWORD_NAME, tvKeywordName.getText().toString() );
                    intent.putExtra( PERSON_FOR_KEYWORD_ID, mSelectedPersonForKeywordsId );
                    startActivityForResult( intent, REQUEST_CODE_EDIT_KEYWORD );
                }
                break;

            case R.id.btnKeywordDelete:
                if ( isKeywordSelected() ) {
                    mMainRepository.getKeywordRepository().deleteKeyword(
                            new Keyword( mSelectedKeywordId,
                                    tvKeywordName.getText().toString(),
                                    mSelectedPersonForKeywordsId ) );
                }
                initializeSelectedKeyword();
                break;

            case R.id.btnBackKeywordsDirectory:
                intent = new Intent();
                setResult( RESULT_OK, intent );
                finish();
                break;

            default:
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

            default:
                break;
        }
    }

    private void initializeSelectedKeyword() {
        mSelectedKeywordId = ITEM_NOT_SELECTED;

        tvKeywordName.setText( EMPTY_NAME );

        mListKeywordsByPerson.clear();
        mListKeywordsByPerson.addAll( mMainRepository
                .getKeywordRepository()
                .getPersonKeywords( mSelectedPersonForKeywordsId ) );
        mListKeywordsByPersonAdapter.notifyDataSetChanged();
    }

    private boolean isKeywordSelected() {
        return mSelectedKeywordId != ITEM_NOT_SELECTED;
    }

}
