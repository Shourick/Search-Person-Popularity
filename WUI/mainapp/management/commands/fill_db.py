from django.core.management.base import BaseCommand
from mainapp.models import *


class Command(BaseCommand):
    help = 'Fill DB fake data'

    def handle(self, *args, **options):
        sites = [
            {'id': '1', 'name': 'Lenta.ru'},
            {'id': '2', 'name': 'rbc.ru'},
            {'id': '3', 'name': 'bbc.ru'},
        ]

        politics = [
            {'id': '1', 'name': 'Путин'},
            {'id': '2', 'name': 'Медведев'},
            {'id': '3', 'name': 'Навальный'},
        ]


        Site.objects.all().delete()
        for site in sites:
            site = Site(**site)
            site.save()

        Politic.objects.all().delete()
        for politic in politics:
            politic = Politic(**politic)
            politic.save()
