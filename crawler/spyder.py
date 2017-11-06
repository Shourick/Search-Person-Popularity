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
        self.main_links = list(self.link_filter())

    #Попытался написать рекурсионную функцию, но что-то она бесконечна.
    # def all_site_link(self):
    #     """метод собирает все внутренние ссылки на сайте,
    #     функция рекурсивная и требует много времени для работы, не своил я многопоточность,
    #     а как сделать этот процесс быстрее не придумал, ссылки по сайту она собирает и складывает в переменную pages"""
    #
    #     def cycles(links):
    #         links = set(links)
    #         links.difference_update(set(self.pages)) #Убираем посещенные странички.
    #         for link in links:
    #             if not link in self.pages: #Условие проверяет посещали мы эту страницу или нет.
    #                 self.pages.append(link)
    #                 soup = BeautifulSoup(self.get_content(link), "lxml")
    #                 new_link = self.link_filter(soup)
    #                 cycles(new_link)
    #
    #     main_links = list(self.link_filter())
    #     cycles(main_links)

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
    print(LENTA.main_links)
