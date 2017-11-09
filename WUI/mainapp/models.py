from django.db import models

# Create your models here.
class Keywords(models.Model):
    id = models.IntegerField(verbose_name='Идентификатор ключевого слова', primary_key=True)
    name = models.CharField(verbose_name='Значение ключевого слова', max_length=2048)
    person_id = models.ForeignKey('Politic')


class Politic(models.Model):
    id = models.IntegerField(verbose_name='Идентификатор личности', primary_key=True)
    name = models.CharField(verbose_name='Имя',	max_length=2048)


class PersonPageRank(models.Model):
    person_id = models.ForeignKey('Politic')
    page_id = models.ForeignKey('Pages')
    rank = models.IntegerField(verbose_name='Количество упоминаний')


class Pages(models.Model):
    id = models.IntegerField(verbose_name='Идентификатор страницы', primary_key=True)
    url = models.CharField(verbose_name='Адрес страницы', max_length=2048)
    site_id = models.ForeignKey('Site')
    found_date_time = models.DateTimeField(verbose_name='Дата и время обнаружения страницы системой')
    last_scan_date = models.DateTimeField(verbose_name='Дата и время последней проверки на упоминания')


class Site(models.Model):
    id = models.IntegerField(verbose_name='Идентификатор сайта', primary_key=True)
    name = models.CharField(verbose_name='Наименование сайта',	max_length=2048)

