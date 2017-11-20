from django.db import models
# from django.db import connection
from django.contrib.auth.models import User

""" 
Классы, написаные с REST/Drop_and_Create_tables.sql
создают schema 'spp' 
"""


class Persons(models.Model):
    ID = models.IntegerField(verbose_name='Идентификатор личности', primary_key=True)
    name = models.CharField(verbose_name='Имя', max_length=2048)


class Keywords(models.Model):
    ID = models.IntegerField(verbose_name='Идентификатор ключевого слова', primary_key=True)
    Name = models.CharField(verbose_name='Значение ключевого слова', max_length=2048)
    PersonID = models.ForeignKey('Persons', on_delete=models.CASCADE)


class Sites(models.Model):
    ID = models.IntegerField(verbose_name='Идентификатор сайта', primary_key=True)
    Name = models.CharField(verbose_name='Наименование сайта', max_length=256)


class Pages(models.Model):
    ID = models.IntegerField(verbose_name='Идентификатор страницы', primary_key=True)
    Url = models.CharField(verbose_name='Адрес страницы', max_length=2048)
    FoundDateTime = models.DateTimeField(verbose_name='Дата и время обнаружения страницы системой', blank=True)
    lastScanDate = models.DateTimeField(verbose_name='Дата и время последней проверки на упоминания', blank=True)
    SiteID = models.ForeignKey('Sites', on_delete=models.CASCADE)


class Personpagerank(models.Model):
    ID = models.IntegerField(verbose_name='Идентификатор', primary_key=True)
    PersonID = models.ForeignKey('Persons', verbose_name='Идентификатор личности', on_delete=models.CASCADE)
    PageID = models.ForeignKey('Pages', on_delete=models.CASCADE)
    Rank = models.PositiveIntegerField(verbose_name='Количество упоминаний', default=0)





