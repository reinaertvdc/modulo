import os


class JsGenerator:
    def __init__(self, output_directory, output_file_name):
        if not os.path.exists(os.path.dirname(output_directory)):
            os.makedirs(os.path.dirname(output_directory))
        self.output_file = open(output_directory + output_file_name, 'w')
        self.certificate_id = self.sub_certificate_id = self.sub_certificate_category_id = self.competence_id = 1

    def __get_certificate_query(self, id, name):
        return "new Certificate(" + str(id) + ", '" + name + "', true, [\n"

    def __get_sub_certificate_query(self, id, parent_id, name):
        return "    new SubCertificate(" + str(id) + ", '" + name + "', null, [\n"

    def __get_sub_certificate_category_query(self, id, parent_id, name):
        return "        new SubCertificateCategory(" + str(id) + ", '" + name + "', null, [\n"

    def __get_competence_query(self, id, parent_id, name):
        return "            new Competence(" + str(id) + ", '" + name + "', null),\n"

    def write(self, certificate):
        query = ''
        query += self.__get_certificate_query(self.certificate_id, certificate.name)
        for sub_certificate in certificate.components:
            query += self.__get_sub_certificate_query(self.sub_certificate_id, self.certificate_id,
                                                      sub_certificate.name)
            for sub_certificate_category in sub_certificate.components:
                query += self.__get_sub_certificate_category_query(self.sub_certificate_category_id,
                                                                   self.sub_certificate_id,
                                                                   sub_certificate_category.name)
                for competence in sub_certificate_category.components:
                    query += self.__get_competence_query(self.competence_id,
                                                         sub_certificate_category,
                                                         competence.name)
                    self.competence_id += 1
                query += "        ]),\n"
                self.sub_certificate_category_id += 1
            query += "    ]),\n"
            self.sub_certificate_id += 1
        query += "]),\n"
        self.certificate_id += 1
        self.output_file.write(query.encode('utf8'))
