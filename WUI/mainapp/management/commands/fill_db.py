from datetime import datetime

from django.core.management.base import BaseCommand
from django.utils import timezone

from mainapp.models import *


class Command(BaseCommand):
    help = 'Fill DB with fake data'

    def handle(self, *args, **options):

        persons = [
            {"id": 1, "name": "\u041f\u0443\u0442\u0438\u043d"},
            {"id": 2, "name": "\u0421\u043e\u0431\u0447\u0430\u043a"},
            {"id": 3, "name": "\u041d\u0430\u0432\u0430\u043b\u044c\u043d\u044b\u0439"},
            {"id": 4, "name": "\u0428\u043e\u0439\u0433\u0443"},
        ]

        sites = [
            {"id": 1, "name": "lenta.ru"},
            {"id": 2, "name": "rbc.ru"},
            {"id": 3, "name": "ria.ru"},
            {"id": 4, "name": "interfax.ru"},
        ]

        keywords = [
            {
                "id": 1,
                "name": "президент",
                "person_id": 1
            },
            {
                "id": 9,
                "name": "ВВП",
                "person_id": 1
            },
            {
                "id": 3,
                "name": "Ксюша",
                "person_id": 2
            },
            {
                "id": 4,
                "name": "Ксения",
                "person_id": 2
            },
            {
                "id": 5,
                "name": "Алексей",
                "person_id": 3
            },
            {
                "id": 6,
                "name": "блогер",
                "person_id": 3
            },
            {
                "id": 7,
                "name": "министр обороны",
                "person_id": 4
            },
            {
                "id": 8,
                "name": "Сергей Кужугетович",
                "person_id": 4
            }
        ]

        time_format = "%Y-%m-%d %H:%M"
        found_date_time = timezone.make_aware(datetime.strptime('2017-10-10 15:23', time_format))
        last_scan_date = timezone.make_aware(datetime.strptime('2017-11-10 15:23', time_format))

        pages = [
            {'id': '1', 'url': 'Lenta.ru', 'site_id': Sites.objects.get(pk=1), 'found_date_time': found_date_time,
             'last_scan_date': last_scan_date},
            {'id': '2', 'url': 'rbc.ru', 'site_id': Sites.objects.get(pk=2), 'found_date_time': found_date_time,
             'last_scan_date': last_scan_date},
            {'id': '3', 'url': 'bbc.ru', 'site_id': Sites.objects.get(pk=3), 'found_date_time': found_date_time,
             'last_scan_date': last_scan_date},
        ]

        personpageranks = [
            {'person_id': Persons.objects.get(pk=1), 'page_id': Pages.objects.get(pk=1), 'rank': 3},
            {'person_id': Persons.objects.get(pk=2), 'page_id': Pages.objects.get(pk=1), 'rank': 3},
            {'person_id': Persons.objects.get(pk=3), 'page_id': Pages.objects.get(pk=1), 'rank': 3},
        ]

        Persons.objects.all().delete()
        for person in persons:
            person = Persons(**person)
            person.save()

        Sites.objects.all().delete()
        for site in sites:
            site = Sites(**site)
            site.save()

        Keywords.objects.all().delete()
        for keyword in keywords:
            keyword = Keywords(**keyword)
            keyword.save()

        Pages.objects.all().delete()
        for page in pages:
            page = Pages(**page)
            page.save()

        Personpagerank.objects.all().delete()
        for personpagerank in personpageranks:
            personpagerank = Personpagerank(**personpagerank)
            personpagerank.save()
