from django.shortcuts import render
from django.core.mail import send_mail, BadHeaderError
from django.http import HttpResponse
from django.contrib.auth.decorators import login_required
from requests.auth import HTTPBasicAuth
import requests
from .forms import ContactSupportForm
from spp import settings


def index(request):
    title = 'Главная'
    sites = requests.get(settings.API_HOST + "sites/", auth=HTTPBasicAuth('root', 'root_password')).json()
    persons = requests.get(settings.API_HOST + "persons/", auth=HTTPBasicAuth('root', 'root_password')).json()
    return render(request, 'index.html', {'title': title, 'sites': sites, 'persons': persons})


def keywords(request):
    title = 'Ключевые слова'
    persons = requests.get(settings.API_HOST + "persons/", auth=HTTPBasicAuth('root', 'root_password')).json()
    keywords = requests.get(settings.API_HOST + "keywords/", auth=HTTPBasicAuth('root', 'root_password')).json()
    return render(request, 'keywords.html', {'title': title, 'persons': persons, 'keywords': keywords})


def support(request):
    title = 'Контакты'
    if request.method == 'POST':
        form = ContactSupportForm(request.POST)
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
            return render(request, 'email/thanks.html', {'title': 'Спасибо'})
    else:
        form = ContactSupportForm()
        return render(request, 'email/support.html', {'title': title, 'form': form})


def general(request):
    title = 'Общая статистика'
    if request.method == 'POST':
        person_id = request.POST.get('person_id')
        url = f"{settings.API_HOST}rank/{person_id}"
        ranks = requests.get(url, auth=HTTPBasicAuth('root', 'root_password')).json()
        sites = requests.get(settings.API_HOST + "sites/", auth=HTTPBasicAuth('root', 'root_password')).json()
        return render(request, 'rank.html', {'title': title, 'ranks': ranks, 'sites': sites})
    else:
        persons = requests.get(settings.API_HOST + "persons/", auth=HTTPBasicAuth('root', 'root_password')).json()
        return render(request, 'general.html', {'title': title, 'persons': persons})


def rank(request):
    return render(request, 'rank.html')


@login_required
def daily(request):
    title = 'Ежедневная статистика'
    persons = requests.get(settings.API_HOST + "persons/", auth=HTTPBasicAuth('root', 'root_password')).json()
    sites = requests.get(settings.API_HOST +"sites/", auth=HTTPBasicAuth('root', 'root_password')).json()
    if request.method == 'POST':
        person_id = request.POST.get('person_id')
        site_id = request.POST.get('site_id')
        start_date = request.POST.get('start_date')
        end_date = request.POST.get('end_date')
        url = f"{settings.API_HOST}rank/{person_id}/{site_id}/{start_date}&{end_date}"
        rank = requests.get(url, auth=HTTPBasicAuth('root', 'root_password')).json()
        return render(request, 'rank_daily.html', {'title': title, 'ranks': rank})
    else:
        return render(request, 'daily.html', {'title': title, 'sites': sites, 'persons': persons})
