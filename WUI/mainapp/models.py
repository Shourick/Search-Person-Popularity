from django.db import models

# Create your models here.
class Site(models.Model):
    id = models.IntegerField(verbose_name='Идентификатор сайта', primary_key=True)
    name = models.CharField(verbose_name='Наименование сайта',	max_length=2048)

class Politic(models.Model):
    id = models.IntegerField(verbose_name='Идентификатор личности', primary_key=True)
    name = models.CharField(verbose_name='Наименование личности',	max_length=2048)


