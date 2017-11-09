from django import forms


class ContactForm(forms.Form):
    subject = forms.CharField(max_length=100, label='Тема')
    sender = forms.EmailField(label='Ваш e-mail')
    message = forms.CharField(widget=forms.Textarea(attrs={'class': 'form-control'}), label='Сообщение')
    copy = forms.BooleanField(required=False, label='Поставьте галочку, если хотите получить копию письма')
