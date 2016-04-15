class SqlGenerator:
    def __init__(self):
        pass

    def __get_certificate_query(self, id, name):
        return "INSERT INTO 'certificates' VALUES (" + str(id) + ", '" + name + "', '1');\n"

    def __get_sub_certificate_query(self, id, parent_id, name):
        return "    INSERT INTO 'sub_certificates' VALUES (" + str(id) + ", " + str(parent_id) + ", '" + name + "', NULL, '1');\n"

    def __get_sub_certificate_category_query(self, id, parent_id, name):
        return "        INSERT INTO 'sub_certificate_categories' VALUES (" + str(id) + ", " + str(parent_id) + ", '" + name + "', NULL, '1');\n"

    def __get_competence_query(self, id, parent_id, name):
        return "            INSERT INTO 'competences' VALUES (" + str(id) + ", " + str(parent_id) + ", '" + name + "', NULL, '1');\n"

    def get(self, certificates):
        query = ''
        certificate_id = sub_certificate_id = sub_certificate_category_id = competence_id = 1
        for certificate in certificates:
            query += self.__get_certificate_query(certificate_id, certificate.name)
            for sub_certificate in certificate.components:
                query += self.__get_sub_certificate_query(sub_certificate_id, certificate_id, sub_certificate.name)
                for sub_certificate_category in sub_certificate.components:
                    query += self.__get_sub_certificate_category_query(sub_certificate_category_id, sub_certificate_id,
                                                                       sub_certificate_category.name)
                    for competence in sub_certificate_category.components:
                        query += self.__get_competence_query(competence_id,
                                                             sub_certificate_category,
                                                             competence.name)
                        competence_id += 1
                    sub_certificate_category_id += 1
                sub_certificate_id += 1
            certificate_id += 1
        return query
