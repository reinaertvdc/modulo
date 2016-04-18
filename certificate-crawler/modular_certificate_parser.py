from certificate_component import CertificateComponent
from pdf_parser import PdfParser


class ModularCertificateParser:
    __CERTIFICATE_NAME_BEGIN_TAG = 'De opleiding '
    __CERTIFICATE_NAME_END_TAG = 'bestaat '
    __NEWLINE_TAG = '^'
    __MODULES_LIST_START_TAG = 'modules: ' + __NEWLINE_TAG + ' ' + __NEWLINE_TAG
    __MODULES_LIST_SPLIT_TAG = ' ' + __NEWLINE_TAG
    __MODULES_LIST_END_TAG = ' ' + __NEWLINE_TAG + __NEWLINE_TAG
    __SECTION_SUB_CERTIFICATES_TITLE_BEGIN_TAG = __NEWLINE_TAG + '2. ' + __NEWLINE_TAG + ' '
    __SECTION_SUB_CERTIFICATES_TITLE_END_TAG = __NEWLINE_TAG + ' ' + __NEWLINE_TAG + __NEWLINE_TAG
    __SECTION_SUB_CERTIFICATES_END_TAG = __NEWLINE_TAG + '3. ' + __NEWLINE_TAG
    __SUB_CERTIFICATE_SPLIT_TAG = __NEWLINE_TAG + __NEWLINE_TAG
    __COMPETENCE_SPLIT_TAG = unichr(61656)
    __SUB_CERTIFICATE_CATEGORY_END_TAG = 'zoals: '

    def __init__(self):
        self.pdf_parser = PdfParser()
        self.content = ''

    def __parse_name(self):
        name_begin_index = self.content.find(self.__CERTIFICATE_NAME_BEGIN_TAG) + len(self.__CERTIFICATE_NAME_BEGIN_TAG)
        name_end_index = self.content.find(self.__CERTIFICATE_NAME_END_TAG, name_begin_index) - 1
        return self.content[name_begin_index:name_end_index]

    def __parse_sub_certificate_names(self):
        module_names = []
        current_module_name_index = self.content.find(self.__MODULES_LIST_START_TAG) + len(
            self.__MODULES_LIST_START_TAG)
        module_names_end_index = self.content.find(self.__MODULES_LIST_END_TAG, current_module_name_index)
        while True:
            next_module_name_index = self.content.find(self.__MODULES_LIST_SPLIT_TAG, current_module_name_index) + len(
                self.__MODULES_LIST_SPLIT_TAG)
            module_name = self.content[
                          current_module_name_index:next_module_name_index - len(self.__MODULES_LIST_SPLIT_TAG)]
            if len(module_name) > 0:
                module_names.append(module_name)
            current_module_name_index = next_module_name_index
            if module_names_end_index - current_module_name_index <= 1:
                break
        return module_names

    def __parse_competences(self, begin_index, end_index):
        return []

    def __parse_sub_certificate_category_and_competences(self, begin_index, end_index):
        return []

    def __parse_sub_certificate_categories_and_competences(self):
        sub_certificate_categories = []
        section_end_tag = self.content.find(self.__SECTION_SUB_CERTIFICATES_END_TAG)
        if section_end_tag < 0:
            section_end_tag = len(self.content) - 1
        title_end_tag_index = self.content.find(self.__SECTION_SUB_CERTIFICATES_TITLE_END_TAG, self.content.find(
            self.__SECTION_SUB_CERTIFICATES_TITLE_BEGIN_TAG) + len(self.__SECTION_SUB_CERTIFICATES_TITLE_BEGIN_TAG))
        previous_sub_certificate_end_index = self.content.find(self.__SUB_CERTIFICATE_SPLIT_TAG,
                                                               title_end_tag_index + len(
                                                                   self.__SECTION_SUB_CERTIFICATES_TITLE_END_TAG))
        while True:
            current_sub_certificate_end_tag = self.content.find(self.__SUB_CERTIFICATE_SPLIT_TAG,
                                                                previous_sub_certificate_end_index + len(
                                                                    self.__SUB_CERTIFICATE_SPLIT_TAG))
            if current_sub_certificate_end_tag >= section_end_tag or current_sub_certificate_end_tag < 0:
                break
            if self.content[previous_sub_certificate_end_index + len(
                    self.__SUB_CERTIFICATE_SPLIT_TAG):current_sub_certificate_end_tag].find(
                    self.__COMPETENCE_SPLIT_TAG) >= 0 and self.content[previous_sub_certificate_end_index + len(
                    self.__SUB_CERTIFICATE_SPLIT_TAG):previous_sub_certificate_end_index + len(
                    self.__SUB_CERTIFICATE_SPLIT_TAG) + len(
                    self.__COMPETENCE_SPLIT_TAG)] != self.__COMPETENCE_SPLIT_TAG:
                print self.content[previous_sub_certificate_end_index + len(
                    self.__SUB_CERTIFICATE_SPLIT_TAG):current_sub_certificate_end_tag + len(
                    self.__SUB_CERTIFICATE_SPLIT_TAG)]
                sub_certificate_categories.append(self.__parse_sub_certificate_category_and_competences(
                    previous_sub_certificate_end_index + len(self.__SUB_CERTIFICATE_SPLIT_TAG),
                    current_sub_certificate_end_tag + len(self.__SUB_CERTIFICATE_SPLIT_TAG)))
            previous_sub_certificate_end_index = current_sub_certificate_end_tag
        return []  # sub_certificate_categories

    def parse(self, path):
        self.content = ' '.join(self.pdf_parser.to_plain_text(path).replace("\n", self.__NEWLINE_TAG).split())
        print self.content
        certificate_name = self.__parse_name()
        sub_certificates = []
        for sub_certificate_name in self.__parse_sub_certificate_names():
            sub_certificates.append(CertificateComponent(sub_certificate_name, []))
        i = 0
        for sub_certificate_categories in self.__parse_sub_certificate_categories_and_competences():
            sub_certificates[i].components = sub_certificate_categories
            i += 1
        return CertificateComponent(certificate_name, sub_certificates)
