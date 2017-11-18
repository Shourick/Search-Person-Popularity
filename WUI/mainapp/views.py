from django.shortcuts import render
from .tables import GeneralStatisticsTable, DailyStatisticsTable
from django.core.mail import send_mail, BadHeaderError
from django.http import HttpResponse
from .forms import ContactForm
from django.contrib.auth.decorators import login_required
from rest_framework import viewsets
import requests
from spp.models import *
from requests.auth import HTTPBasicAuth


def index(request):
    title = 'Главная'
    return render(request, 'index.html', {'title': title})


# @login_required
def general(request):
    title = 'Общая статистика'
    sites = requests.get("http://94.130.27.143/sites", auth=HTTPBasicAuth('root', 'root_password')).json()
    persons = requests.get("http://94.130.27.143/persons", auth=HTTPBasicAuth('root', 'root_password'))
    gs_table = GeneralStatisticsTable(persons.json())
    return render(request, 'general.html', {'title': title, 'sites': sites, 'persons': persons, 'gs_table': gs_table})


# @login_required
def daily(request):
    title = 'Ежедневная статистика'
    sites = requests.get("http://94.130.27.143/sites", auth=HTTPBasicAuth('root', 'root_password')).json()
    persons = requests.get("http://94.130.27.143/persons", auth=HTTPBasicAuth('root', 'root_password')).json()
    ds_table = DailyStatisticsTable(persons)
    return render(request, 'daily.html', {'title': title, 'sites': sites, 'persons': persons, 'ds_table': ds_table})


def support(request):
    title = 'Контакты'
    if request.method == 'POST':
        form = ContactForm(request.POST)
        # Если форма заполнена корректно, сохраняем все введённые пользователем значения
        if form.is_valid():
            subject = form.cleaned_data['subject']
            sender = form.cleaned_data['sender']
            message = form.cleaned_data['message']
            copy = form.cleaned_data['copy']
            recipients = ['crowd.scoring@yandex.ru']
            # Если пользователь захотел получить копию себе, добавляем его в список получателей
            if copy:
                recipients.append(sender)
            try:
                send_mail(subject, message, 'crowd.scoring@yandex.ru', recipients)
            except BadHeaderError:  # Защита от уязвимости
                return HttpResponse('Invalid header found')
                # Переходим на другую страницу, если сообщение отправлено
            return render(request, 'email/thanks.html', {'title': 'Спасибо'})
    else:
        # Заполняем форму
        form = ContactForm()
        # Отправляем форму на страницу
    return render(request, 'email/support.html', {'title': title, 'form': form})


# ViewSets define the view behavior. REST
class PersonpagerankViewSet(viewsets.ModelViewSet):
    queryset = Personpagerank.objects.all()
    serializer_class = PersonpagerankSerializer


class KeywordsViewSet(viewsets.ModelViewSet):
    queryset = Keywords.objects.all()
    serializer_class = KeywordsSerializer


class UserViewSet(viewsets.ModelViewSet):
    queryset = User.objects.all()
    serializer_class = UserSerializer
