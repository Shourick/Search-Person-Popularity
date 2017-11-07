package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository;

/**
 * Created by skubatko on 07/11/17
 */

public interface IRepositoryUtils
{
    /**
     * Заполнение Repository фейковыми значениями
     */
    public void initializeRepository();

    /**
     * Выводит содержимое Repository в лог системы для проверки
     */
    public void showRepositoryInfo();
}
