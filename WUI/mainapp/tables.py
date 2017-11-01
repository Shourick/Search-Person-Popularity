import django_tables2 as tables
from .models import Politic

class GeneralStatisticsTable(tables.Table):
    class Meta:
        model = Politic