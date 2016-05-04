import os
import sys

from downloader import Downloader
from js_generator import JsGenerator
from modular_certificate_parser import ModularCertificateParser
from mysql_generator import MySqlGenerator
from postgresql_generator import PostGreSqlGenerator
from web_page_parser import WebPageParser


class CertificateCrawler:
    __SECTION_MODULAR_PROF_CERTIFICATES_TITLE = 'Modulair georganiseerd op basis van een beroepskwalificatie'
    __SECTION_MODULAR_CERTIFICATES_TITLE = 'Modulair georganiseerd'
    __SECTION_LINEAR_CERTIFICATES_TITLE = 'Nog niet omgeschakelde rubrieken met bestaande lineaire opleidingen'
    __CERTIFICATES_DIR = 'certificates/'
    __MODULAR_PROF_CERTIFICATES_DIR = __CERTIFICATES_DIR + 'modular_prof/'
    __MODULAR_CERTIFICATES_DIR = __CERTIFICATES_DIR + 'modular/'
    __LINEAR_CERTIFICATES_DIR = __CERTIFICATES_DIR + 'linear/'
    __OUTPUT_DIR = 'output/'
    __MYSQL_OUTPUT_FILE_NAME = 'mysql.sql'
    __POSTGRESQL_OUTPUT_FILE_NAME = 'postgresql.sql'
    __JS_OUTPUT_FILE_NAME = 'javascript.js'

    def __init__(self):
        sys.stdout.write("\nCertificate crawler started!\n\n")

    def download_certificates(self):
        downloader = Downloader()
        sys.stdout.write("Downloading certificates web page...")
        try:
            web_page_parser = WebPageParser(downloader.get_certificate_web_page())
            sys.stdout.write(" Done.\n")

            sys.stdout.write("Scanning page for modular certificates based on professional qualification...")
            modular_prof_certificate_file_urls = web_page_parser.get_certificate_file_urls(
                self.__SECTION_MODULAR_PROF_CERTIFICATES_TITLE)
            sys.stdout.write(" Found " + str(len(modular_prof_certificate_file_urls)) + ".\n")

            sys.stdout.write("Scanning page for modular certificates...")
            modular_certificate_file_urls = web_page_parser.get_certificate_file_urls(
                self.__SECTION_MODULAR_CERTIFICATES_TITLE)
            sys.stdout.write(" Found " + str(len(modular_certificate_file_urls)) + ".\n")

            sys.stdout.write("Scanning page for linear certificates...")
            linear_certificate_file_urls = web_page_parser.get_certificate_file_urls(
                self.__SECTION_LINEAR_CERTIFICATES_TITLE)
            sys.stdout.write(" Found " + str(len(linear_certificate_file_urls)) + ".\n\n")

            sys.stdout.write(
                "Downloading missing certificates to '" + self.__CERTIFICATES_DIR + "', this may take a while...")
            downloader.save_certificate_files(modular_prof_certificate_file_urls, self.__MODULAR_PROF_CERTIFICATES_DIR)
            downloader.save_certificate_files(modular_certificate_file_urls, self.__MODULAR_CERTIFICATES_DIR)
            downloader.save_certificate_files(modular_certificate_file_urls, self.__LINEAR_CERTIFICATES_DIR)
            sys.stdout.write(" Done.\n\n")
        except:
            sys.stdout.write(" Error.\n\n")
            sys.stdout.write("WARNING: Some certificates failed to download, parsing only downloaded certificates.\n\n")

    def parse_certificates(self):
        sys.stdout.write("Starting parser.\n")
        modular_certificate_parser = ModularCertificateParser()
        sys.stdout.write("MySQL written to '" + self.__OUTPUT_DIR + self.__MYSQL_OUTPUT_FILE_NAME + "'.\n")
        mysql_generator = MySqlGenerator(self.__OUTPUT_DIR, self.__MYSQL_OUTPUT_FILE_NAME)
        sys.stdout.write("PostGreSQL written to '" + self.__OUTPUT_DIR + self.__POSTGRESQL_OUTPUT_FILE_NAME + "'.\n")
        postgresql_generator = PostGreSqlGenerator(self.__OUTPUT_DIR, self.__POSTGRESQL_OUTPUT_FILE_NAME)
        sys.stdout.write("JavaScript written to '" + self.__OUTPUT_DIR + self.__JS_OUTPUT_FILE_NAME + "'.\n\n")
        js_generator = JsGenerator(self.__OUTPUT_DIR, self.__JS_OUTPUT_FILE_NAME)

        # certificate = modular_certificate_parser.get_certificate(self.__MODULAR_CERTIFICATES_DIR + '021Bouwplaatsmachinist.pdf')
        # sql_generator.write(certificate)
        # js_generator.write(certificate)
        # exit()
        modular_certificate_file_names = os.listdir(self.__MODULAR_CERTIFICATES_DIR)
        n_modular_certificates = len(modular_certificate_file_names)
        i = 1
        for certificate_file_name in modular_certificate_file_names:
            sys.stdout.write("Parsing certificate " + str(i) + "/" + str(
                n_modular_certificates) + " '" + certificate_file_name + "'...")
            certificate = modular_certificate_parser.get_certificate(
                self.__MODULAR_CERTIFICATES_DIR + certificate_file_name)
            mysql_generator.write(certificate)
            postgresql_generator.write(certificate)
            js_generator.write(certificate)
            sys.stdout.write(" Done.\n")
            i += 1
        sys.stdout.write("\nParser finished!\n")
