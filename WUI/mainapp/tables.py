import django_tables2 as tables
from .models import Politic

class GeneralStatisticsTable(tables.Table):
    # class Meta:
    #     model = Politic
    name = tables.Column(accessor='person_id.name')
    rank = tables.Column()


class DailyStatisticsTable(tables.Table):

    date = tables.DateColumn()
    new_pages_amount = tables.Column()