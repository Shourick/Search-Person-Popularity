from django.core.management.base import BaseCommand
from mainapp.models import *


class Command(BaseCommand):
    help = 'Fill DB fake data'

    def handle(self, *args, **options):
        keywords = [
            {'id': '1', 'name': 'Путин', 'person_id': 1},
            {'id': '2', 'name': 'Путина', 'person_id': 1},
            {'id': '3', 'name': 'Путину', 'person_id': 1},
        ]

        politics = [
            {'id': '1', 'name': 'Путин'},
            {'id': '2', 'name': 'Медведев'},
            {'id': '3', 'name': 'Навальный'},
        ]

        person_page_ranks = [
            {'person_id': '1', 'page_id': '1', 'rank': 3},
            {'person_id': '2', 'page_id': '1', 'rank': 3},
            {'person_id': '3', 'page_id': '1', 'rank': 3},
        ]

        pages = [
            {'id': '1', 'url': 'Lenta.ru', 'site_id': '1', 'found_date_time': '10-10-2017 15:23', 'last_scan_date': '11-10-2017 15:23'},
            {'id': '2', 'url': 'rbc.ru', 'site_id': '2', 'found_date_time': '10-10-2017 15:23', 'last_scan_date': '11-10-2017 15:23'},
            {'id': '2', 'url': 'bbc.ru', 'site_id': '2', 'found_date_time': '10-10-2017 15:23', 'last_scan_date': '11-10-2017 15:23'},
        ]

        sites = [
            {'id': '1', 'name': 'Lenta.ru'},
            {'id': '2', 'name': 'rbc.ru'},
            {'id': '3', 'name': 'bbc.ru'},
        ]

        Keywords.objects.all().delete()
        for keyword in keywords:
            keyword = Keyword(**keyword)
            keyword.save()

        Politic.objects.all().delete()
        for politic in politics:
            politic = Politic(**politic)
            politic.save()

        PersonPageRank.objects.all().delete()
        for person_page_rank in person_page_ranks:
            person_page_rank = PersonPageRank(**person_page_rank)
            person_page_rank.save()

        Pages.objects.all().delete()
        for page in pages:
            page = Pages(**page)
            page.save()

        Site.objects.all().delete()
        for site in sites:
            site = Site(**site)
            site.save()

