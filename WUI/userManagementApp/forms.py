from django.contrib.auth.forms import UserCreationForm
from django.contrib.auth.models import User
from django import forms


class MyRegistrationForm(UserCreationForm):
    # image = forms.ImageField(help_text='Загрузить фото')

    class Meta:
        model = User
        fields = ('username', 'email', 'password1', 'password2')  # image


class UserChangeForm(forms.ModelForm):
    class Meta:
        model = User
        fields = ['username', 'email']
