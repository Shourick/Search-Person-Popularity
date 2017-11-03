package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Database.IRepository;

import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Database.Players.User;

/**
 * Данный интрефейс описывает контракт для работы с данными проекта Search Person Popularity
 * Все классы, которые будут работать с данными проекта должны подписать данный контракт
 *
 * Интрефейс содержит CRUD операции с данными таблицы 'users'
 *
 * Created by skubatko on 03/11/17.
 */

public interface IUserRepository {
    public void AddUser( User user );         // добавляем Поьзователя
    User getUser( int id );                   // получаем Поьзователя по его id
    List getAllUsers();                     // получаем список всех Поьзователей
    int getUsersCount();                    // получаем количество Поьзователей в БД
    int updateUser( User user );              // обновляем Поьзователя
    void deleteUser( User user );             // удаляем Поьзователя
    void deleteAllUsers();                  // удаляем всех Поьзователей
}
