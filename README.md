# Search-Person-Popularity
Подсчёт количества упоминаний какого-либо человека в новостях
<br>Ветка разработки веб-интерфейса пользователя

add to settings:

DEBUG = TRUE
INSTALLED_APPS = +[
    'spp',
    'mainapp',
    'userManagementApp'
]
TEMPLATES[
    {
        'DIRS': ['templates'],
}

STATIC_URL = '/static/'

STATICFILES_DIRS = (
    os.path.join(BASE_DIR, "static"),
)
