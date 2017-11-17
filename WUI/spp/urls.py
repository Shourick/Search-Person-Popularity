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
from django.contrib.auth.models import User
from rest_framework import serializers, viewsets, routers

from mainapp.models import *
from mainapp.views import *


class UserSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = User
        fields = ('url', 'username', 'email', 'is_staff')


# ViewSets define the view behavior.
class UserViewSet(viewsets.ModelViewSet):
    queryset = User.objects.all()
    serializer_class = UserSerializer


class PersonpagerankSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Personpagerank
        fields = ('ID', 'PersonID', 'PageID', 'Rank')


class PersonpagerankViewSet(viewsets.ModelViewSet):
    queryset = Personpagerank.objects.all()
    serializer_class = PersonpagerankSerializer


# Routers provide a way of automatically determining the URL conf.
router = routers.DefaultRouter()
router.register(r'users', UserViewSet)
router.register(r'personpageranks', PersonpagerankViewSet)


urlpatterns = [
    url(r'^$', index, name='index'),
    url(r'^general/$', general, name='general'),
    url(r'^daily/$', daily, name='daily'),
    url(r'^support/$', support, name='support'),

    url(r'^user/', include('userManagementApp.urls')),
    url(r'^api/', include(router.urls)),

]