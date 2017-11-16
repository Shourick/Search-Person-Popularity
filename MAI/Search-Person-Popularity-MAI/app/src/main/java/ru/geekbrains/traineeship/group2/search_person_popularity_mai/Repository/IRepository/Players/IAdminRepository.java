package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Players;

import java.io.IOException;
import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.Admin;

/**
 * Данный интрефейс описывает контракт для работы с данными проекта Search Person Popularity
 * Все классы, которые будут работать с данными проекта должны подписать данный контракт
 * <p>
 * Интрефейс содержит CRUD операции с данными таблицы 'admins' Репозитория
 * <p>
 * Created by skubatko on 03/11/17.
 */

public interface IAdminRepository {

    int addAdmin( Admin admin ) throws IOException;         // добавляем Администратора

    Admin getAdmin( int id ) throws IOException;                   // получаем Администратора по его id

    List getAllAdmins() throws IOException;                     // получаем список всех Администраторов

    int getAdminsCount() throws IOException;                    // получаем количество Администраторов в Репозитории

    int updateAdmin( Admin admin ) throws IOException;              // обновляем Администратора

    void deleteAdmin( Admin admin ) throws IOException;             // удаляем Администратора

    void deleteAllAdmins() throws IOException;                  // удаляем всех Администраторов
}
