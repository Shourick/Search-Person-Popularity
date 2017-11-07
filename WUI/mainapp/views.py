from django.shortcuts import render
from .models import *
from .tables import GeneralStatisticsTable, DailyStatisticsTable


def index(request):
    title = 'Главная'
    return render(request, 'index.html', {'title': title})


def general(request):
    title = 'Общая статистика'
    sites = Site.objects.all()
    politics = Politic.objects.all()
    person_page_rank = PersonPageRank.objects.all()
    gs_table = GeneralStatisticsTable(person_page_rank)
    return render(request, 'general.html', {'title': title, 'sites': sites, 'politics': politics, 'gs_table': gs_table})


def daily(request):
    title = 'Ежедневная статистика'
    sites = Site.objects.all()
    politics = Politic.objects.all()
    ds_table = DailyStatisticsTable(politics)
    return render(request, 'daily.html', {'title': title, 'sites': sites, 'politics': politics, 'ds_table': ds_table})
