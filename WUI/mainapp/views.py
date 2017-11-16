from django.shortcuts import render
from .models import *
from .tables import GeneralStatisticsTable, DailyStatisticsTable
from django.core.mail import send_mail, BadHeaderError
from django.http import HttpResponse
from .forms import ContactForm


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


def contacts(request):
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
    return render(request, 'email/contacts.html', {'title': title, 'form': form})
