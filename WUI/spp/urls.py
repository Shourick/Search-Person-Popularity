"""stt URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.11/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import url, include
from mainapp.views import *
from rest_framework import routers


# Routers provide a way of automatically determining the URL conf.
router = routers.DefaultRouter()
router.register(r'keywords', KeywordsViewSet)
router.register(r'personpageranks', PersonpagerankViewSet)
router.register(r'users', UserViewSet)


urlpatterns = [
    url(r'^$', index, name='index'),
    url(r'^general/$', general, name='general'),
    url(r'^daily/$', daily, name='daily'),
    url(r'^support/$', support, name='support'),
    url(r'^keywords/$', KeywordsViewSet, name='keywords'),

    url(r'^user/', include('userManagementApp.urls')),
    url(r'^api/', include(router.urls)),  # REST

]
