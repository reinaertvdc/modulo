import urllib2


class Downloader:
    __CERTIFICATE_PAGE_URL = \
        'http://www.ond.vlaanderen.be/curriculum/leren-en-werken/deeltijds-beroepssecundair-onderwijs/opleidingen/'

    def __init__(self):
        pass

    def get_certificate_page(self):
        return urllib2.urlopen(self.__CERTIFICATE_PAGE_URL).read()

    def get_certificate_pdf(self, download_path):
        return urllib2.urlopen(self.__CERTIFICATE_PAGE_URL + download_path).read()
