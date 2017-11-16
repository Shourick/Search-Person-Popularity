package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Data;

import java.io.IOException;
import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Data.Person;

/**
 * Данный интрефейс описывает контракт для работы с данными проекта Search Person Popularity
 * Все классы, которые будут работать с данными проекта должны подписать данный контракт
 * <p>
 * Интрефейс содержит CRUD операции с данными таблицы 'persons' Репозитория
 * <p>
 * Created by skubatko on 03/11/17.
 */

public interface IPersonRepository {

    public void addPerson( Person person ) throws IOException;       // добавляем Личность

    Person getPersonById( int id ) throws IOException;               // получаем Личность по id

    Person getPersonByName( String name ) throws IOException;        // получаем Личность по Имени

    List getAllPersons() throws IOException;                       // получаем список всех Личностей

    int getPersonsCount() throws IOException;                      // получаем количество Личностей в Репозитории

    int updatePerson( Person person ) throws IOException;            // обновляем данные по Личности

    void deletePerson( Person person ) throws IOException;           // удаляем Личность

    void deleteAllPersons() throws IOException;                    // удаляем все Личности
}
