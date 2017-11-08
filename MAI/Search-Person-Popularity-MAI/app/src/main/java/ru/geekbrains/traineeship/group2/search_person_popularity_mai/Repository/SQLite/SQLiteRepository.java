package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite;

import android.content.Context;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Data.IKeywordRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Data.IPersonRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Data.ISiteRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.IRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.IRepositoryUtils;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Players.IAdminRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Players.IUserRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Data.SQLiteKeywordRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Data.SQLitePersonRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Data.SQLiteSiteRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Players.SQLiteAdminRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.SQLite.Players.SQLiteUserRepository;

/**
 * Класс для работы с базой SQLite для возможности тестирования бизнес-логики
 * пока создается Веб-сервис
 * <p>
 * Created by skubatko on 27/10/17.
 */
public class SQLiteRepository extends SQLiteHelper implements IRepository

{
    private SQLitePersonRepository personRepository;
    private SQLiteKeywordRepository keywordRepository;
    private SQLiteSiteRepository siteRepository;
    private SQLiteUserRepository userRepository;
    private SQLiteAdminRepository adminRepository;
    private SQLiteRepositoryUtils repositoryUtils;


    /**
     * Конструктор {@link SQLiteRepository}.
     *
     * @param context Контекст приложения
     */
    public SQLiteRepository( Context context )
    {
        super( context );
        this.personRepository = new SQLitePersonRepository( this );
        this.keywordRepository = new SQLiteKeywordRepository( this );
        this.siteRepository = new SQLiteSiteRepository( this );
        this.userRepository = new SQLiteUserRepository( this );
        this.adminRepository = new SQLiteAdminRepository( this );
        this.repositoryUtils = new SQLiteRepositoryUtils( this );
    }

    @Override
    public SQLitePersonRepository getPersonRepository()
    {
        return personRepository;
    }

    @Override
    public SQLiteKeywordRepository getKeywordRepository()
    {
        return keywordRepository;
    }

    @Override
    public SQLiteSiteRepository getSiteRepository()
    {
        return siteRepository;
    }

    @Override
    public SQLiteUserRepository getUserRepository()
    {
        return userRepository;
    }

    @Override
    public SQLiteAdminRepository getAdminRepository()
    {
        return adminRepository;
    }

    @Override
    public SQLiteRepositoryUtils getRepositoryUtils()
    {
        return repositoryUtils;
    }
}
