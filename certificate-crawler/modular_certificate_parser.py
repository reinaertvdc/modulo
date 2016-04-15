from pdf_parser import PdfParser


class ModularCertificateParser:
    __CERTIFICATE_NAME_BEGIN_TAG = 'De opleiding '
    __CERTIFICATE_NAME_END_TAG = 'bestaat '

    def __init__(self):
        self.pdf_parser = PdfParser()

    def parse(self, path):
        content = ' '.join(self.pdf_parser.to_plain_text(path).split())

        name_begin_index = content.find(self.__CERTIFICATE_NAME_BEGIN_TAG) + len(self.__CERTIFICATE_NAME_BEGIN_TAG)
        name_end_index = content.find(self.__CERTIFICATE_NAME_END_TAG, name_begin_index) - 1

        name = content[name_begin_index:name_end_index].replace('\n', '')

        print name
