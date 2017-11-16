from django.shortcuts import render
from spp.models import *
from .tables import GeneralStatisticsTable, DailyStatisticsTable
from django.core.mail import send_mail, BadHeaderError
from django.http import HttpResponse
from .forms import ContactForm
from django.contrib.auth.decorators import login_required
import requests


def index(request):
    title = 'Главная'
    return render(request, 'index.html', {'title': title})


# @login_required
def general(request):
    title = 'Общая статистика'
    sites = requests.get("http://94.130.27.143/sites").json()
    persons = requests.get("http://94.130.27.143/persons")
    gs_table = GeneralStatisticsTable(persons.json())
    return render(request, 'general.html', {'title': title, 'sites': sites, 'persons': persons, 'gs_table': gs_table})


# @login_required
def daily(request):
    title = 'Ежедневная статистика'
    sites = Sites.objects.all()
    sites = requests.get("http://94.130.27.143/sites").json()
    # politics = Politic.objects.all()
    politics = requests.get("http://94.130.27.143/persons").json()
    ds_table = DailyStatisticsTable(politics)
    return render(request, 'daily.html', {'title': title, 'sites': sites, 'politics': politics, 'ds_table': ds_table})


def support(request):
    title = 'Контакты'
    if request.method == 'POST':
        form = ContactForm(request.POST)
        # Если форма заполнена корректно, сохраняем все введённые   пользователем значения
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
