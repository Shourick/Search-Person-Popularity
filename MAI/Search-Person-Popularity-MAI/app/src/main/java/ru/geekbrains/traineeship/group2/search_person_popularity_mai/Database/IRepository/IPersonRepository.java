package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Database.IRepository;

import java.util.List;

import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Database.Data.Person;

/**
 * Данный интрефейс описывает контракт для работы с данными проекта Search Person Popularity
 * Все классы, которые будут работать с данными проекта должны подписать данный контракт
 *
 * Интрефейс содержит CRUD операции с данными таблицы 'persons'
 *
 * Created by skubatko on 03/11/17.
 */

public interface IPersonRepository {
    public void AddPerson( Person person );       // добавляем Личность
    Person getPersonById( int id );               // получаем Личность по id
    Person getPersonByName( String name );        // получаем Личность по Имени
    List getAllPersons();                       // получаем список всех Личностей
    int getPersonsCount();                      // получаем количество Личностей в БД
    int updatePerson( Person person );            // обновляем данные по Личности
    void deletePerson( Person person );           // удаляем Личность
    void deleteAllPersons();                    // удаляем все Личности
}
