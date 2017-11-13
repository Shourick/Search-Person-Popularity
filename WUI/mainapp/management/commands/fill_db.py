from django.core.management.base import BaseCommand
from mainapp.models import *
from django.utils import timezone
from datetime import datetime


class Command(BaseCommand):
    help = 'Fill DB with fake data'

    def handle(self, *args, **options):

        politics = [
            {'id': '1', 'name': 'Путин'},
            {'id': '2', 'name': 'Медведев'},
            {'id': '3', 'name': 'Навальный'},
        ]

        sites = [
            {'id': '1', 'name': 'Lenta.ru'},
            {'id': '2', 'name': 'rbc.ru'},
            {'id': '3', 'name': 'bbc.ru'},
        ]

        keywords = [
            {'id': '1', 'name': 'Путин', 'person_id': Politic.objects.get(pk=1)},
            {'id': '2', 'name': 'Путина', 'person_id': Politic.objects.get(pk=1)},
            {'id': '3', 'name': 'Путину', 'person_id': Politic.objects.get(pk=1)},
        ]

        time_format = "%Y-%m-%d %H:%M"
        found_date_time = timezone.make_aware(datetime.strptime('2017-10-10 15:23', time_format))
        last_scan_date = timezone.make_aware(datetime.strptime('2017-11-10 15:23', time_format))

        pages = [
            {'id': '1', 'url': 'Lenta.ru', 'site_id': Site.objects.get(pk=1), 'found_date_time': found_date_time, 'last_scan_date': last_scan_date},
            {'id': '2', 'url': 'rbc.ru', 'site_id': Site.objects.get(pk=2), 'found_date_time': found_date_time, 'last_scan_date': last_scan_date},
            {'id': '3', 'url': 'bbc.ru', 'site_id': Site.objects.get(pk=3), 'found_date_time': found_date_time, 'last_scan_date': last_scan_date},
        ]

        person_page_ranks = [
            {'person_id': Politic.objects.get(pk=1), 'page_id': Pages.objects.get(pk=1), 'rank': 3},
            {'person_id': Politic.objects.get(pk=2), 'page_id': Pages.objects.get(pk=1), 'rank': 3},
            {'person_id': Politic.objects.get(pk=3), 'page_id': Pages.objects.get(pk=1), 'rank': 3},
        ]

        Politic.objects.all().delete()
        for politic in politics:
            politic = Politic(**politic)
            politic.save()

        Site.objects.all().delete()
        for site in sites:
            site = Site(**site)
            site.save()

        Keywords.objects.all().delete()
        for keyword in keywords:
            keyword = Keywords(**keyword)
            keyword.save()

        Pages.objects.all().delete()
        for page in pages:
            page = Pages(**page)
            page.save()

        PersonPageRank.objects.all().delete()
        for person_page_rank in person_page_ranks:
            person_page_rank = PersonPageRank(**person_page_rank)
            person_page_rank.save()


