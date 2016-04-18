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
    __SUB_CERTIFICATE_NAMES_END_TAG_1 = '\nVoor elke module die men'
    __SUB_CERTIFICATE_NAMES_END_TAG_2 = '\n(cid:'
    __SUB_CERTIFICATE_NAMES_END_TAG_3 = ' \n \n\n'
    __SUB_CERTIFICATE_NAMES_END_TAG_4 = unichr(576000)

    __SUB_CERTIFICATE_BEGIN_TAG = 'Module '
    __SUB_CERTIFICATE_END_TAG = 'De beroepsgerichte vorming '

    def __init__(self):
        self.pdf_parser = PdfParser()

    def __get_certificate_name(self, source):
        # every modular certificate contains a generic sentence containing the name of the certificate
        name_begin_index = source.find(self.__CERTIFICATE_NAME_BEGIN_TAG) + len(self.__CERTIFICATE_NAME_BEGIN_TAG)
        name_end_index = source.find(self.__CERTIFICATE_NAME_END_TAG, name_begin_index) - 1

        name = source[name_begin_index:name_end_index]
        name = name.replace('\n', '')

        return name

    def __get_sub_certificates(self, source):
        sub_certificates = []

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

        for name, source in zip(names, sources):
            sub_certificate_categories = self.__get_sub_certificate_categories(source)
            sub_certificates.append(CertificateComponent(name, sub_certificate_categories))

        return sub_certificates

    def __get_sub_certificate_names(self, source):
        names_begin_index = source.find(self.__SUB_CERTIFICATE_NAMES_BEGIN_TAG) + len(
            self.__SUB_CERTIFICATE_NAMES_BEGIN_TAG)

        # some sources have a different format, use the tag that occurs first
        names_end_index_1 = source.find(self.__SUB_CERTIFICATE_NAMES_END_TAG_1, names_begin_index) - 1
        names_end_index_2 = source.find(self.__SUB_CERTIFICATE_NAMES_END_TAG_2, names_begin_index) - 1
        names_end_index_3 = source.find(self.__SUB_CERTIFICATE_NAMES_END_TAG_3, names_begin_index)
        names_end_index_4 = source.find(self.__SUB_CERTIFICATE_NAMES_END_TAG_4, names_begin_index) - 1
        end_index_candidates = [names_end_index_1, names_end_index_2, names_end_index_3, names_end_index_4]
        names_end_index = max(end_index_candidates)
        for index in end_index_candidates:
            if 0 <= index < names_end_index:
                names_end_index = index

        names_source = source[names_begin_index:names_end_index]

        names = names_source.split('\n')

        # merge names that are split over multiple lines
        i = 0
        previous_line_was_empty = False
        append_to_previous_line = False
        while i < len(names):
            names[i] = names[i].strip()
            if len(names[i]) <= 0:
                names.pop(i)
                if not previous_line_was_empty:
                    previous_line_was_empty = True
                    append_to_previous_line = not append_to_previous_line
            elif append_to_previous_line and i > 0:
                previous_line_was_empty = False
                names[i - 1] += ' ' + names[i]
                names.pop(i)
            else:
                previous_line_was_empty = False
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
