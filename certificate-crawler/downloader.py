import os
import urllib
import urllib2


class Downloader:
    __CERTIFICATE_WEB_PAGE_URL = \
        'http://www.ond.vlaanderen.be/curriculum/leren-en-werken/deeltijds-beroepssecundair-onderwijs/opleidingen/'
    __ABSOLUTE_URL_START_TAG = 'http://'
    __CERTIFICATE_POSTFIX = '.pdf'

    def __init__(self):
        pass

    def get_certificate_web_page(self):
        return urllib2.urlopen(self.__CERTIFICATE_WEB_PAGE_URL).read()

    def __save_certificate_pdf(self, url, save_path):
        if not os.path.exists(os.path.dirname(save_path)):
            os.makedirs(os.path.dirname(save_path))
        try:
            testfile = urllib.URLopener()
            if url.startswith(self.__ABSOLUTE_URL_START_TAG):
                testfile.retrieve(url, save_path)
            else:
                testfile.retrieve(self.__CERTIFICATE_WEB_PAGE_URL + url, save_path)
        except:
            pass

    def save_certificate_pdfs(self, urls, save_path):
        index = 1
        for url in urls:
            self.__save_certificate_pdf(url, save_path + str(index) + self.__CERTIFICATE_POSTFIX)
            index += 1
