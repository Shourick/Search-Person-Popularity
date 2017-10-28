from django.shortcuts import render
from mainapp.models import *
# Create your views here.

def index(request):
    title = 'Главная'
    return render(request,'index.html', {'title':title})

def general(request):
    title = 'Общая статистика'
    sites = Site.objects.all()
    return render(request,'general.html', {'title':title, 'sites': sites})

def daily(request):
    title = 'Ежедневная статистика'
    return render(request,'daily.html', {'title':title})
