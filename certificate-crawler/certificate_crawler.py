from downloader import Downloader
from web_page_parser import WebPageParser


class CertificateCrawler:
    SECTION_MODULAR_PROF_CERTIFICATES_TITLE = 'Modulair georganiseerd op basis van een beroepskwalificatie'
    SECTION_MODULAR_CERTIFICATES_TITLE = 'Modulair georganiseerd'
    SECTION_LINEAR_CERTIFICATES_TITLE = 'Nog niet omgeschakelde rubrieken met bestaande lineaire opleidingen'
    CERTIFICATES_DIR = 'certificates/'
    MODULAR_PROF_CERTIFICATES_DIR = CERTIFICATES_DIR + 'modular_prof/'
    MODULAR_CERTIFICATES_DIR = CERTIFICATES_DIR + 'modular/'
    LINEAR_CERTIFICATES_DIR = CERTIFICATES_DIR + 'linear/'

    def __init__(self):
        self.downloader = Downloader()

    def run(self):
        web_page_parser = WebPageParser(self.downloader.get_certificate_web_page())
        self.downloader.save_certificate_files(
            web_page_parser.get_certificate_file_urls(self.SECTION_MODULAR_PROF_CERTIFICATES_TITLE),
            self.MODULAR_PROF_CERTIFICATES_DIR)
        self.downloader.save_certificate_files(
            web_page_parser.get_certificate_file_urls(self.SECTION_MODULAR_CERTIFICATES_TITLE),
            self.MODULAR_CERTIFICATES_DIR)
        self.downloader.save_certificate_files(
            web_page_parser.get_certificate_file_urls(self.SECTION_LINEAR_CERTIFICATES_TITLE),
            self.LINEAR_CERTIFICATES_DIR)
