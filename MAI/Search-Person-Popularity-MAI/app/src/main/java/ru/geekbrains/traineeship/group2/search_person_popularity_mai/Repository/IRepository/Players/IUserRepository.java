package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Players;

import java.io.IOException;
import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.User;

/**
 * Данный интрефейс описывает контракт для работы с данными проекта Search Person Popularity
 * Все классы, которые будут работать с данными проекта должны подписать данный контракт
 * <p>
 * Интрефейс содержит CRUD операции с данными таблицы 'users' Репозитория
 * <p>
 * Created by skubatko on 03/11/17.
 */

public interface IUserRepository {

    int addUser( User user ) throws IOException;         // добавляем Поьзователя

    User getUser( int id ) throws IOException;                   // получаем Поьзователя по его id

    List getAllUsers() throws IOException;                     // получаем список всех Поьзователей

    int getUsersCount() throws IOException;                    // получаем количество Поьзователей в Репозитории

    int updateUser( User user ) throws IOException;              // обновляем Поьзователя

    void deleteUser( User user ) throws IOException;             // удаляем Поьзователя

    void deleteAllUsers() throws IOException;                  // удаляем всех Пользователей
}
