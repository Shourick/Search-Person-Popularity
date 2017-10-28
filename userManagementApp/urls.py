from django.conf.urls import url
from userManagementApp.views import *

urlpatterns = [
    url(r'^login/$', login, name='login'),
    url(r'^logout/$', logout, name='logout'),
    url(r'^registration/$', registration,  name='registration'),
    url(r'^success_reg/$', success_reg, name='success_reg'),
]