import os
import urllib
import urllib2


class Downloader:
    __CERTIFICATE_WEB_PAGE_URL = \
        'http://www.ond.vlaanderen.be/curriculum/leren-en-werken/deeltijds-beroepssecundair-onderwijs/opleidingen/'
    __ABSOLUTE_URL_START_TAG = 'http://'
    __DIRECTORY_SYMBOL = '/'

    def __init__(self):
        pass

    def get_certificate_web_page(self):
        return urllib2.urlopen(self.__CERTIFICATE_WEB_PAGE_URL).read()

    def __save_certificate_file(self, url, save_path):
        if os.path.isfile(save_path):
            return
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

    def save_certificate_files(self, urls, save_path):
        for url in urls:
            file_path_end_index = url.rfind(self.__DIRECTORY_SYMBOL)
            self.__save_certificate_file(url, save_path + url[file_path_end_index + 1:])
