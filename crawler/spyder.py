from bs4 import BeautifulSoup
import requests

"""Прошу прощения за задержку, работа не отпускала до последнего"""

class SiteSpyder(object):
    """Класс паука, в конструктор необходимо подать доменное имя вида: 'http://site.ru' """

    # Заголовок запроса, не все сайты отдают свой контент без него
    HEADERS = {
        'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:45.0) Gecko/20100101 Firefox/45.0'
    }

    def __init__(self, domain):
        self.domain = domain
        self.pages = []

    def all_site_link(self):
        """метод собирает все внутренние ссылки на сайте"""
        pass


    def get_content(self, path='/'):
        """Метод возвращает контент в декодированном виде,
        аргумент path принимает относительные пути в случае если аргумент не задан
        будет возвращен контент главной страницы"""


        url = ''.join([self.domain, path])
        content = requests.get(url, self.HEADERS)
        # print(content.text)
        return content.content.decode()

    def link_filter(self, soup=None):
        """Метод возвращает относительные ссылки находящиеся на странице,
        аргумент soup принимает объект BeautifulSoup
        той странички из которой извлекается список ссылок"""

        if not soup:
            soup = BeautifulSoup(self.get_content(), "lxml")
        result = []

        for link_a in soup.html.body.find_all('a'):
            # Обработка ошибки на случай если сслыки будут onclic или просто пустые теги
            try:
                link = link_a['href']
                # print(link)
                if all(not link.startswith(prefix) for prefix in ['#', 'tel:', 'mailto:']):
                    if link.startswith('/') and not link.startswith('//'):
                        result.append(link)
            except KeyError:
                continue
        return set(result)


if __name__ == '__main__':
    LENTA = SiteSpyder('http://lenta.ru')
    # soup = BeautifulSoup(LENTA.get_content())
    print(LENTA.link_filter())
