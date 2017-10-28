from django.contrib import auth
from django.shortcuts import render, HttpResponseRedirect
from django.http import Http404
from userManagementApp.forms import MyRegistrationForm


def login(request):
    if request.method == "POST":
        print("POST data =", request.POST)
        username = request.POST.get('login')
        password = request.POST.get('password')
        user = auth.authenticate(username=username, password=password)
        if user:
            auth.login(request, user)
            return HttpResponseRedirect("/")
        else:
            return render(request, 'index.html', {'username': username, 'errors': True})
    else:
        raise Http404


def logout(request):
    auth.logout(request)
    return HttpResponseRedirect("/")


def registration(request):
    if request.method == "POST":
        form = MyRegistrationForm(request.POST)
        if form.is_valid():
            form.save()
            return HttpResponseRedirect("/user/success_reg")
        context = {'form': form}
        return render(request, 'registration.html', context)
    context = {'form': MyRegistrationForm()}
    return render(request, 'registration.html', context)


def success_reg(request):
    title = 'Регистрация завершена'
    return render(request,'success_reg.html', {'title':title})