import urllib2


class Downloader:
    __CERTIFICATE_WEB_PAGE_URL = \
        'http://www.ond.vlaanderen.be/curriculum/leren-en-werken/deeltijds-beroepssecundair-onderwijs/opleidingen/'
    __ABSOLUTE_URL_START_TAG = 'http://'

    def __init__(self):
        pass

    def get_certificate_web_page(self):
        return urllib2.urlopen(self.__CERTIFICATE_WEB_PAGE_URL).read()

    def get_certificate_pdf(self, url):
        if url.startswith(self.__ABSOLUTE_URL_START_TAG):
            return urllib2.urlopen(url).read()
        else:
            return urllib2.urlopen(self.__CERTIFICATE_WEB_PAGE_URL + url).read()
