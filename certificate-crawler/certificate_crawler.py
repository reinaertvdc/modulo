import os

from downloader import Downloader
from modular_certificate_parser import ModularCertificateParser
from web_page_parser import WebPageParser


class CertificateCrawler:
    __SECTION_MODULAR_PROF_CERTIFICATES_TITLE = 'Modulair georganiseerd op basis van een beroepskwalificatie'
    __SECTION_MODULAR_CERTIFICATES_TITLE = 'Modulair georganiseerd'
    __SECTION_LINEAR_CERTIFICATES_TITLE = 'Nog niet omgeschakelde rubrieken met bestaande lineaire opleidingen'
    __CERTIFICATES_DIR = 'certificates/'
    __MODULAR_PROF_CERTIFICATES_DIR = __CERTIFICATES_DIR + 'modular_prof/'
    __MODULAR_CERTIFICATES_DIR = __CERTIFICATES_DIR + 'modular/'
    __LINEAR_CERTIFICATES_DIR = __CERTIFICATES_DIR + 'linear/'

    def __init__(self):
        pass

    def download_certificates(self):
        downloader = Downloader()
        web_page_parser = WebPageParser(downloader.get_certificate_web_page())
        downloader.save_certificate_files(
            web_page_parser.get_certificate_file_urls(self.__SECTION_MODULAR_PROF_CERTIFICATES_TITLE),
            self.__MODULAR_PROF_CERTIFICATES_DIR)
        downloader.save_certificate_files(
            web_page_parser.get_certificate_file_urls(self.__SECTION_MODULAR_CERTIFICATES_TITLE),
            self.__MODULAR_CERTIFICATES_DIR)
        downloader.save_certificate_files(
            web_page_parser.get_certificate_file_urls(self.__SECTION_LINEAR_CERTIFICATES_TITLE),
            self.__LINEAR_CERTIFICATES_DIR)

    def parse_certificates(self):
        modular_certificate_parser = ModularCertificateParser()

        # modular_certificate_parser.parse(
        #    self.__MODULAR_CERTIFICATES_DIR + os.listdir(self.__MODULAR_CERTIFICATES_DIR)[0])

        for pdf in os.listdir(self.__MODULAR_CERTIFICATES_DIR):
            modular_certificate_parser.parse(self.__MODULAR_CERTIFICATES_DIR + pdf)
