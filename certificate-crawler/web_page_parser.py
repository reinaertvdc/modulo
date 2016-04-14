class WebPageParser:
    __SECTION_TITLE_START_TAG = '<h2>'
    __SECTION_TITLE_END_TAG = '</h2>'
    __PAGE_END_TAG = '<div id="bottom">'
    __PDF_URL_START_TAG = '<a href="'
    __PDF_URL_END_TAG = '" '

    def __init__(self, web_page):
        self.__web_page = web_page

    def __get_section(self, title):
        search_string = self.__SECTION_TITLE_START_TAG + title + self.__SECTION_TITLE_END_TAG
        section_begin_index = self.__web_page.find(search_string)
        section_end_index = self.__web_page.find(self.__SECTION_TITLE_START_TAG,
                                                 section_begin_index + len(search_string) + 1)
        if section_end_index <= section_begin_index:
            section_end_index = self.__web_page.find(self.__PAGE_END_TAG) - 1
        return self.__web_page[section_begin_index:section_end_index]

    def get_certificate_pdf_urls(self, section_title):
        section = self.__get_section(section_title)
        certificate_pdf_urls = []
        last_certificate_pdf_url_index = -1
        while True:
            current_certificate_pdf_url_index = section.find(self.__PDF_URL_START_TAG,
                                                             last_certificate_pdf_url_index + 1)
            if current_certificate_pdf_url_index < 0:
                break
            certificate_pdf_urls.append(section[current_certificate_pdf_url_index + len(
                self.__PDF_URL_START_TAG):section.find(self.__PDF_URL_END_TAG, current_certificate_pdf_url_index)])
            last_certificate_pdf_url_index = current_certificate_pdf_url_index
        return certificate_pdf_urls
