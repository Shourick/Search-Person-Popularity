from sqlalchemy import *
from sqlalchemy.orm import *
from sqlalchemy.ext.declarative import declarative_base

BASE = declarative_base()

class Person(BASE):
    __tablename__ = 'person'
    id = Column(Integer, primary_key=True, autoincrement=True)
    name = Column(String)
    tags = relationship('Tags')


    def __init__(self, name):
        self.name = name

class Tags(BASE):
    __tablename__ = 'tags'
    id = Column(Integer, primary_key=True, autoincrement=True)
    person_id = Column(Integer, ForeignKey('person.id'))
    name_variable = Column(String)


class Sites(BASE):
    __tablename__ = 'sites'
    id = Column(Integer, primary_key=True, autoincrement=True)
    name = Column(String)
    url = Column(String)
    site_map_url = (String)
    start_date = Column(Date)
    pages = relationship('Pages')

    def __init__(self, name, url, site_map = None):
        self.name = name
        self.url = url
        self.site_map_url = site_map




class Pages(BASE):
    __tablename__ = 'pages'
    id = Column(Integer, primary_key=True, autoincrement=True)
    site_id = Column(Integer, ForeignKey('Sites.id'))
    path = Column(String)
    start_date_update = Column(Date)

    def __init__(self, url):
        self.path = url



class PagePersonRank(BASE):
    __tablename__ = 'page_person_rank'
    id = Column(Integer, primary_key=True, autoincrement=True)
    person_id = Column(Integer, ForeignKey('person.id'))
    page_id = Column(Integer, ForeignKey('pages.id'))