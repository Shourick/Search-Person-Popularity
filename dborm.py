from sqlalchemy import Column, Integer, ForeignKey, String, DateTime
from sqlalchemy import create_engine
from sqlalchemy.orm import relationship
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker

import datetime

BASE = declarative_base()


class Person(BASE):
    """Модель таблицы Персоны, принимает значение Имени"""
    __tablename__ = 'person'
    id = Column(Integer, primary_key=True, autoincrement=True)
    name = Column(String)
    tags = relationship('Tags')

    def __init__(self, name):
        self.name = name

    # ToDo Описать метод возвращаеющий рейтинг по диапозону даты или на конкретную дату, на конкретном сайте
    # ToDo Выборка по диапозону дат??? как это сделать поще
    def site_date_rank(self, dates, site_id):
        """Метод принимает дату в виде кортежа и id сайта и возвращает рейтинг за эту дату"""
        site = session.query(Sites).filter(Sites.id == site_id).all()
        result = site #Todo Описать логику выборки по диапозону даты

    # ToDo Описать метод возвращающий актуальный рейтинг персоны на сайте
    def site_rank(self, site):
        pass

    # ToDo Описать метод возвращающий актуальный рейтинг персоны по всем сайтам
    def rank_all_site(self):
        pass

    # ToDo Описать метод добавляющий персоне вариации имени в таблицу Tags
    def add_person_tag(self):
        pass


class Tags(BASE):
    __tablename__ = 'tags'
    id = Column(Integer, primary_key=True, autoincrement=True)
    person_id = Column(Integer, ForeignKey('person.id'))
    name_variable = Column(String)

    def __init__(self, name_var, person_id):
        self.name_var = name_var
        self.person_id = person_id


class Sites(BASE):
    __tablename__ = 'sites'
    id = Column(Integer, primary_key=True, autoincrement=True)
    name = Column(String)
    url = Column(String)
    site_map_url = (String)
    start_date = Column(DateTime, default=datetime.datetime.now())
    pages = relationship('Pages')

    def __init__(self, name, url, site_map=None):
        self.name = name
        self.url = url
        self.site_map_url = site_map

    # ToDo Описать метод добавляющий данные о странице в таблицу
    # ToDo описать метод возвращающий тецущий рейтинг персоны на сайте
    def site_person_rating(self, person_id):
        pass


class Pages(BASE):
    __tablename__ = 'pages'
    id = Column(Integer, primary_key=True, autoincrement=True)
    site_id = Column(Integer, ForeignKey('Sites.id'))
    path = Column(String)
    start_date_update = Column(DateTime)

    def __init__(self, url, site_id, path):
        self.path = url
        self.site_id = site_id
        self.path = path


class PagePersonRank(BASE):
    __tablename__ = 'page_person_rank'
    id = Column(Integer, primary_key=True, autoincrement=True)
    person_id = Column(Integer, ForeignKey('person.id'))
    page_id = Column(Integer, ForeignKey('pages.id'))
    person_rank = Column(Integer)
    date_rank = Column(DateTime)

    def __init__(self, page, person):
        self.page_id = page
        self.person_id = person


# Todo класс пользователя
class Users(BASE):
    __tablename__ = 'users'
    id = Column(Integer, primary_key=True, autoincrement=True)
    user_name = Column(String, unique=True)
    email = Column(String, unique=True)
    group = relationship('GroupUsers')


# Todo класс группы пользователей
class GroupUsers(BASE):
    __tablename__ = 'group_users'
    id = Column(Integer, primary_key=True, autoincrement=True)
    group_name = Column(String, unique=True)
    user = Column(Integer, ForeignKey('Users.id'))
    access = relationship('GroupAccess')


# Todo класс прав группы пользователей
class GroupAccess(BASE):
    __tablename__ = 'group_access'
    id = Column(Integer, primary_key=True, autoincrement=True)
    group_id = Column(Integer, ForeignKey('group_users.id'))
    access = Column(String)


if __name__ == '__main__':
    engine = create_engine('sqlite:///testrating.sqlite')
    BASE.metadata.create_all(engine)
    DBSession = sessionmaker(bind=engine)
    DBSession.configure(bind=engine)
    session = DBSession()
