from django.shortcuts import render
from mainapp.models import *


# TODO: адекватный listView


def index(request):
    title = 'Главная'
    return render(request, 'index.html', {'title': title})


def general(request):
    title = 'Общая статистика'
    sites = Site.objects.all()
    politics = Politic.objects.all()
    return render(request, 'general.html', {'title': title, 'sites': sites, 'politics': politics})


def daily(request):
    title = 'Ежедневная статистика'
    sites = Site.objects.all()
    politics = Politic.objects.all()
    return render(request, 'daily.html', {'title': title, 'sites': sites, 'politics': politics})
