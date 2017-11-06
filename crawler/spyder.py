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
        # Относительные пути имеющиеся на главной странице,
        # удобно индексировать новое, так как новое часто появляется на главной,
        # и можно следить за изменениями, переодически обновляя

        self.main_links = list(self.link_filter())
        self.pages = set()  # Известные относительные пути страниц
        self.indexing_page = set()  #Страницы из которых уже извлекались ссылки и снова по ним ходить наверное не надо

    def link_indexing(self, paths):
        """Метод принимает список относительных путей, после его проходит по нему и ищет все относительные пути.
        Обновляя при этом аргументы pages и indexing_page"""
        paths = set(paths)
        paths.difference_update(self.indexing_page)
        for item in paths:
            soup = BeautifulSoup(self.get_content(item), "lxml")
            pages = self.link_filter(soup)
            self.pages.update(pages)
            self.indexing_page.add(item)

    def get_content(self, path='/'):
        """Метод возвращает контент в декодированном виде,
        аргумент path принимает относительные пути в случае если аргумент не задан
        будет возвращен контент главной страницы"""

        url = ''.join([self.domain, path])
        content = requests.get(url, self.HEADERS)

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

                if all(not link.startswith(prefix) for prefix in ['#', 'tel:', 'mailto:']):
                    if link.startswith('/') and not link.startswith('//'):
                        result.append(link)
            except KeyError:
                continue
        return set(result)


if __name__ == '__main__':
    LENTA = SiteSpyder('http://lenta.ru')
    # for itm in LENTA.pages:
    #     LENTA.link_indexing(itm)
    print("#" * 50)
    print(LENTA.main_links)
    print("#" * 50)
    LENTA.link_indexing(LENTA.main_links)
    print(LENTA.pages)
    print("#" * 50)
    print(LENTA.indexing_page)
    LENTA.link_indexing(LENTA.pages)
    print("#" * 50)
    print(LENTA.pages)
