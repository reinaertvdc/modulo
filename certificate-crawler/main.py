from downloader import Downloader
from web_page_parser import WebPageParser

# titles of the different sections in the certificates web page
SECTION_MODULAR_PROF_CERTIFICATES_TITLE = 'Modulair georganiseerd op basis van een beroepskwalificatie'
SECTION_MODULAR_CERTIFICATES_TITLE = 'Modulair georganiseerd'
SECTION_LINEAR_CERTIFICATES_TITLE = 'Nog niet omgeschakelde rubrieken met bestaande lineaire opleidingen'

# get the certificates web page
downloader = Downloader()
web_page_parser = WebPageParser(downloader.get_certificate_web_page())

# list of certificates modularly organised based on professional qualification
modular_prof_certificate_urls = web_page_parser.get_certificate_pdf_urls(SECTION_MODULAR_PROF_CERTIFICATES_TITLE)

# list of certificates modularly organised
modular_certificate_urls = web_page_parser.get_certificate_pdf_urls(SECTION_MODULAR_CERTIFICATES_TITLE)

# list of yet unconverted certificates with existing linear courses
linear_certificate_urls = web_page_parser.get_certificate_pdf_urls(SECTION_LINEAR_CERTIFICATES_TITLE)

#print downloader.get_certificate_pdf(modular_prof_certificate_urls[0])
