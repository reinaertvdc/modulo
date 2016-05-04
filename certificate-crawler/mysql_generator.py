import os


class MySqlGenerator:
    def __init__(self, output_directory, output_file_name):
        if not os.path.exists(os.path.dirname(output_directory)):
            os.makedirs(os.path.dirname(output_directory))
        self.output_file = open(output_directory + output_file_name, 'w')
        self.certificate_id = self.sub_certificate_id = self.sub_certificate_category_id = self.competence_id = 1

    def __get_certificate_query(self, id, name):
        return "INSERT INTO `certificates` VALUES (" + str(id) + ", '" + name + "', '0');\n"

    def __get_sub_certificate_query(self, id, parent_id, name):
        return "    INSERT INTO `sub_certificates` VALUES (" + str(id) + ", " + str(
            parent_id) + ", '" + name + "', NULL, '1');\n"

    def __get_sub_certificate_category_query(self, id, parent_id, name):
        return "        INSERT INTO `sub_certificate_categories` VALUES (" + str(id) + ", " + str(
            parent_id) + ", '" + name + "', NULL, '1');\n"

    def __get_competence_query(self, id, parent_id, name):
        return "            INSERT INTO `competences` VALUES (" + str(id) + ", " + str(
            parent_id) + ", '" + name + "', NULL, '1');\n"

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
                                                         self.sub_certificate_category_id,
                                                         competence.name)
                    self.competence_id += 1
                self.sub_certificate_category_id += 1
            self.sub_certificate_id += 1
        self.certificate_id += 1
        self.output_file.write(query.encode('utf8'))
