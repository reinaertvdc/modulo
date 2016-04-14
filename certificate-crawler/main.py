from downloader import Downloader
from web_page_parser import WebPageParser

# titles of the different sections in the certificates web page
SECTION_MODULAR_PROF_CERTIFICATES_TITLE = 'Modulair georganiseerd op basis van een beroepskwalificatie'
SECTION_MODULAR_CERTIFICATES_TITLE = 'Modulair georganiseerd'
SECTION_LINEAR_CERTIFICATES_TITLE = 'Nog niet omgeschakelde rubrieken met bestaande lineaire opleidingen'

CERTIFICATES_DIR = 'certificates/'
MODULAR_PROF_CERTIFICATES_DIR = 'modular_prof/'
MODULAR_CERTIFICATES_DIR = 'modular/'
LINEAR_CERTIFICATES_DIR = 'linear/'

# get the certificates web page
downloader = Downloader()
web_page_parser = WebPageParser(downloader.get_certificate_web_page())

# list of certificates modularly organised based on professional qualification
downloader.save_certificate_pdfs(web_page_parser.get_certificate_pdf_urls(SECTION_MODULAR_PROF_CERTIFICATES_TITLE),
                                 CERTIFICATES_DIR + MODULAR_PROF_CERTIFICATES_DIR)

# list of certificates modularly organised
downloader.save_certificate_pdfs(web_page_parser.get_certificate_pdf_urls(SECTION_MODULAR_CERTIFICATES_TITLE),
                                 CERTIFICATES_DIR + MODULAR_CERTIFICATES_DIR)

# list of yet unconverted certificates with existing linear courses
downloader.save_certificate_pdfs(web_page_parser.get_certificate_pdf_urls(SECTION_LINEAR_CERTIFICATES_TITLE),
                                 CERTIFICATES_DIR + LINEAR_CERTIFICATES_DIR)
