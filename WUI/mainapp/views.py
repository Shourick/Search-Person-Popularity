from django.shortcuts import render
from django.core.mail import send_mail, BadHeaderError
from django.http import HttpResponse,  HttpResponseRedirect
from django.contrib.auth.decorators import user_passes_test, login_required
from requests.auth import HTTPBasicAuth
import requests
from .forms import ContactForm, GetDataForDailyStat
from .tables import DailyStatisticsTable



def index(request):
    title = 'Главная'
    sites = requests.get("http://94.130.27.143/sites/", auth=HTTPBasicAuth('root', 'root_password')).json()
    persons = requests.get("http://94.130.27.143/persons/", auth=HTTPBasicAuth('root', 'root_password')).json()
    return render(request, 'index.html', {'title': title, 'sites': sites, 'persons': persons})



@login_required
def daily(request):
    title = 'Ежедневная статистика'
    sites = requests.get("http://94.130.27.143/sites", auth=HTTPBasicAuth('root', 'root_password')).json()
    persons = requests.get("http://94.130.27.143/persons", auth=HTTPBasicAuth('root', 'root_password')).json()
    ds_table = DailyStatisticsTable(persons)
    return render(request, 'daily.html', {'title': title, 'sites': sites, 'persons': persons,
                                          'ds_table': ds_table})


def keywords(request):
    title = 'Ключевые слова'
    persons = requests.get("http://94.130.27.143/persons", auth=HTTPBasicAuth('root', 'root_password')).json()
    keywords = requests.get("http://94.130.27.143/keywords", auth=HTTPBasicAuth('root', 'root_password')).json()
    return render(request, 'keywords.html', {'title': title,  'persons': persons, 'keywords': keywords})


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


# @user_passes_test(lambda u: u.is_superuser)
# @login_required
def general(request):
    title = 'Общая статистика'
    if request.method == 'POST':
        person_id = request.POST.get('person_id')
        url = "http://94.130.27.143/rank/" + str(person_id)
        ranks = requests.get(url, auth=HTTPBasicAuth('root', 'root_password')).json()
        sites = requests.get("http://94.130.27.143/sites/", auth=HTTPBasicAuth('root', 'root_password')).json()
        return render(request, 'rank.html', {'title': title, 'ranks': ranks, 'sites': sites})
    else:
        persons = requests.get("http://94.130.27.143/persons/", auth=HTTPBasicAuth('root', 'root_password')).json()
        return render(request, 'general.html', {'title': title, 'persons': persons})


def rank(request):
    return render(request, 'rank.html')