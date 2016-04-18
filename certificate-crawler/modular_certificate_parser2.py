import re

from certificate_component import CertificateComponent
from pdf_parser import PdfParser


class ModularCertificateParser:
    __SECTION_DESCRIPTION_BEGIN_TAG = '\n1'
    __SECTION_SUB_CERTIFICATES_BEGIN_TAG = '\n2'
    __SECTION_SITUATION_BEGIN_TAG = '\n3'

    __SECTION_DESCRIPTION_BEGIN_TAG_ALTERNATIVE = '1. '
    __SECTION_SUB_CERTIFICATES_BEGIN_TAG_ALTERNATIVE = '2. '
    __SECTION_SITUATION_BEGIN_TAG_ALTERNATIVE = '3. '

    __CERTIFICATE_NAME_BEGIN_TAG = 'De opleiding '
    __CERTIFICATE_NAME_END_TAG = 'bestaat '

    __SUB_CERTIFICATE_NAMES_BEGIN_TAG = 'modules: \n \n'
    __SUB_CERTIFICATE_NAMES_END_TAG = ' \n\n'

    __SUB_CERTIFICATE_BEGIN_TAG = 'Module '
    __SUB_CERTIFICATE_END_TAG = 'De beroepsgerichte vorming '

    def __init__(self):
        self.pdf_parser = PdfParser()

    def __get_certificate_name(self, source):
        # every modular certificate contains a generic sentence containing the name of the certificate
        name_begin_index = source.find(self.__CERTIFICATE_NAME_BEGIN_TAG) + len(self.__CERTIFICATE_NAME_BEGIN_TAG)
        name_end_index = source.find(self.__CERTIFICATE_NAME_END_TAG, name_begin_index) - 1

        return source[name_begin_index:name_end_index]

    def __get_sub_certificates(self, source):
        sub_certificates = []

        print source

        # the 'situation' section follows the 'sub certificates' section and thus the begin tag of the former can be
        # used as the end tag for the latter
        sub_certificates_begin_index = source.find(self.__SECTION_SUB_CERTIFICATES_BEGIN_TAG)
        sub_certificates_end_index = source.find(self.__SECTION_SITUATION_BEGIN_TAG)

        # some sources have a different format, if one of the tags could not be found, try the alternative
        if sub_certificates_begin_index < 0:
            sub_certificates_begin_index = source.find(self.__SECTION_SUB_CERTIFICATES_BEGIN_TAG_ALTERNATIVE)
        if sub_certificates_end_index < 0:
            sub_certificates_end_index = source.find(self.__SECTION_SITUATION_BEGIN_TAG_ALTERNATIVE)

        sub_certificates_source = source[sub_certificates_begin_index:sub_certificates_end_index]

        sources = self.__get_sub_certificate_sources(sub_certificates_source)

        names = self.__get_sub_certificate_names(source)

        print str(len(sources)) + ' == ' + str(len(names))
        if len(sources) == len(names):
            print 'ok'
        else:
            print ('staph!')
            exit()

        for name, source in zip(names, sources):
            print name
            sub_certificate_categories = self.__get_sub_certificate_categories(source)
            sub_certificates.append(CertificateComponent(name, sub_certificate_categories))

        return sub_certificates

    def __get_sub_certificate_names(self, source):
        names_begin_index = source.find(self.__SUB_CERTIFICATE_NAMES_BEGIN_TAG) + len(
            self.__SUB_CERTIFICATE_NAMES_BEGIN_TAG)
        names_end_index = source.find(self.__SUB_CERTIFICATE_NAMES_END_TAG, names_begin_index) - 1

        names_source = source[names_begin_index:names_end_index]

        names = names_source.split('\n')

        # remove names that only consist of spaces
        i = 0
        while i < len(names):
            names[i] = names[i].strip()
            if len(names[i]) <= 0:
                names.remove(names[i])
            i += 1

        return names

    def __get_sub_certificate_sources(self, sub_certificates_source):
        sources = []

        source_begin_index = sub_certificates_source.find('\n')
        while True:
            if sub_certificates_source[source_begin_index] != '\n':
                break
            source_begin_index += 1
        source_end_index = sub_certificates_source.find(self.__SUB_CERTIFICATE_END_TAG, source_begin_index)

        while True:
            if source_begin_index < 0 or source_end_index < 0:
                break

            # the source would still contain its title, this needs to be excluded
            source_begin_index = sub_certificates_source.find('\n\n', source_begin_index) + 2

            certificate_source = sub_certificates_source[source_begin_index:source_end_index]
            sources.append(certificate_source)

            previous_end_index = source_end_index

            source_begin_index = sub_certificates_source.find(self.__SUB_CERTIFICATE_BEGIN_TAG, previous_end_index)
            source_end_index = sub_certificates_source.find(self.__SUB_CERTIFICATE_END_TAG, source_begin_index)
        return sources

    def __get_sub_certificate_categories(self, categories_source):
        categories = []

        categories_source = categories_source.replace('\n', '')

        return categories

    def get_certificate(self, path):
        # Series of spaces need to be removed because they sometimes occur mid-sentence, but all newlines need to be
        # kept because they are needed for parsing. Tabs do not occur.
        source_with_series_of_spaces = self.pdf_parser.to_plain_text(path)
        source = re.sub(' +', ' ', source_with_series_of_spaces)

        name = self.__get_certificate_name(source)
        sub_certificates = self.__get_sub_certificates(source)

        return CertificateComponent(name, sub_certificates)
