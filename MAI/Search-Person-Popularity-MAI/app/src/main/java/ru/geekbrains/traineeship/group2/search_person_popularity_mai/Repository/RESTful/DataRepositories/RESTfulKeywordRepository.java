package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.DataRepositories;

import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Keyword;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Data.IKeywordRepository;

/**
 * Created by skubatko on 13/11/17
 */

public class RESTfulKeywordRepository implements IKeywordRepository{
    @Override
    public void addKeyword( Keyword keyword, int personId ) {

    }

    @Override
    public Keyword getKeyword( int id ) {
        return null;
    }

    @Override
    public List getPersonKeywords( int personId ) {
        return null;
    }

    @Override
    public List getAllKeywords() {
        return null;
    }

    @Override
    public int getKeywordsCount() {
        return 0;
    }

    @Override
    public int updateKeyword( Keyword keyword ) {
        return 0;
    }

    @Override
    public void deleteKeyword( Keyword keyword ) {

    }

    @Override
    public void deleteAllKeywords() {

    }
}
