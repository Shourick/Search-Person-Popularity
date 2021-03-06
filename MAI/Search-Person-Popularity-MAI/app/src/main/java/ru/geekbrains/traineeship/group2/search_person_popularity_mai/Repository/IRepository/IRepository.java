package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Data.IKeywordRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Data.IPersonRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Data.ISiteRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Players.IAdminRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Players.IUserRepository;

/**
 * Created by skubatko on 08/11/17
 */

public interface IRepository {

    IPersonRepository getPersonRepository();

    IKeywordRepository getKeywordRepository();

    ISiteRepository getSiteRepository();

    IUserRepository getUserRepository();

    IAdminRepository getAdminRepository();

}
