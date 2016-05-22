CREATE TABLE bgv_scores
(
    id BIGINT PRIMARY KEY NOT NULL,
    remarks VARCHAR(255),
    score VARCHAR(255) NOT NULL,
    week INTEGER NOT NULL,
    competence_id BIGINT,
    student_id BIGINT,
    CONSTRAINT fk_t9yq0aj9fg0wiquh7i917d9d9 FOREIGN KEY (competence_id) REFERENCES competences (id),
    CONSTRAINT fk_gpqwi58mh9toehi4pl9t6gdoq FOREIGN KEY (student_id) REFERENCES student_info (id)
);
CREATE TABLE certificates
(
    id BIGINT PRIMARY KEY NOT NULL,
    enabled BOOLEAN NOT NULL,
    name VARCHAR(255) NOT NULL
);
CREATE TABLE classes
(
    id BIGINT PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    certificate_id BIGINT,
    grade_id BIGINT,
    teacher_id BIGINT,
    CONSTRAINT fk_s8cf607is4tpeuhn6dsvs3g2a FOREIGN KEY (certificate_id) REFERENCES certificates (id),
    CONSTRAINT fk_bej9xsilrylbi7835wv3ivfii FOREIGN KEY (grade_id) REFERENCES grades (id),
    CONSTRAINT fk_q04x54tgu6ph5k0scj9r5ia7j FOREIGN KEY (teacher_id) REFERENCES users (id)
);
CREATE TABLE classes_users
(
    class_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_64buli6jnx0dx6vuhfpatcodd FOREIGN KEY (class_id) REFERENCES classes (id),
    CONSTRAINT fk_oqfvytp6c23e2labf8vu9kip3 FOREIGN KEY (user_id) REFERENCES users (id)
);
CREATE TABLE competences
(
    id BIGINT PRIMARY KEY NOT NULL,
    custom_name VARCHAR(255),
    enabled BOOLEAN NOT NULL,
    name VARCHAR(255) NOT NULL,
    sub_certificate_category_id BIGINT,
    CONSTRAINT fk_1e7h0m1mfidro3eu4c1ycat9q FOREIGN KEY (sub_certificate_category_id) REFERENCES sub_certificate_categories (id)
);
CREATE TABLE course_topic_students
(
    course_topic_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_4f4e6xn85ccpv16nxo7ytcnph FOREIGN KEY (course_topic_id) REFERENCES course_topics (id),
    CONSTRAINT fk_bs0700bowt767x224bholhyil FOREIGN KEY (user_id) REFERENCES users (id)
);
CREATE TABLE course_topics
(
    id BIGINT PRIMARY KEY NOT NULL,
    description VARCHAR(255),
    name VARCHAR(255) NOT NULL,
    resit BOOLEAN NOT NULL,
    clazz_id BIGINT,
    grade_id BIGINT,
    CONSTRAINT fk_ffvcvd8mng0rqyxnr786xlerx FOREIGN KEY (clazz_id) REFERENCES classes (id),
    CONSTRAINT fk_hjvtrkapucb2rgvq5xus4llg FOREIGN KEY (grade_id) REFERENCES grades (id)
);
CREATE TABLE course_topics_objectives
(
    course_topics BIGINT NOT NULL,
    objectives BIGINT NOT NULL,
    CONSTRAINT fk_77026iif7pirtwhfvhdq7fjh FOREIGN KEY (course_topics) REFERENCES course_topics (id),
    CONSTRAINT fk_bkuvn8r4bstngae4fkg9aqgfb FOREIGN KEY (objectives) REFERENCES objectives (id)
);
CREATE TABLE grades
(
    id BIGINT PRIMARY KEY NOT NULL,
    enabled BOOLEAN NOT NULL,
    name VARCHAR(255) NOT NULL
);
CREATE TABLE objectives
(
    id BIGINT PRIMARY KEY NOT NULL,
    custom_name VARCHAR(255),
    enabled BOOLEAN NOT NULL,
    name VARCHAR(255) NOT NULL,
    grade_id BIGINT,
    CONSTRAINT fk_ce4qwl4bj5rkfdrfl1vsiim6o FOREIGN KEY (grade_id) REFERENCES grades (id)
);
CREATE TABLE pav_scores
(
    id BIGINT PRIMARY KEY NOT NULL,
    remarks VARCHAR(255),
    score VARCHAR(255) NOT NULL,
    week INTEGER NOT NULL,
    course_topic_id BIGINT,
    objective_id BIGINT,
    student_id BIGINT,
    CONSTRAINT fk_38kxm9v58b2105epm2cy4m55g FOREIGN KEY (course_topic_id) REFERENCES course_topics (id),
    CONSTRAINT fk_ekmdrh5a74yf41yrbhboax08a FOREIGN KEY (objective_id) REFERENCES objectives (id),
    CONSTRAINT fk_kanl34j0gwjctkl3sqggr1t7j FOREIGN KEY (student_id) REFERENCES student_info (id)
);
CREATE TABLE student_info
(
    id BIGINT PRIMARY KEY NOT NULL,
    bank_account VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    birth_place VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    emergency_number VARCHAR(255) NOT NULL,
    house_number VARCHAR(255) NOT NULL,
    national_identification_number VARCHAR(11) NOT NULL,
    nationality VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    postal_code VARCHAR(4) NOT NULL,
    street VARCHAR(255) NOT NULL,
    certificate_id BIGINT,
    grade_id BIGINT,
    user_id BIGINT,
    CONSTRAINT fk_s2el2t2nk84cyrk1bh66dvci0 FOREIGN KEY (certificate_id) REFERENCES certificates (id),
    CONSTRAINT fk_fgfwuv5ieg6yte4fj7hy62js FOREIGN KEY (grade_id) REFERENCES grades (id),
    CONSTRAINT fk_oahrw4qqp44alf5qmtveqy3w2 FOREIGN KEY (user_id) REFERENCES users (id)
);
CREATE UNIQUE INDEX uk_oahrw4qqp44alf5qmtveqy3w2 ON student_info (user_id);
CREATE TABLE sub_certificate_categories
(
    id BIGINT PRIMARY KEY NOT NULL,
    custom_name VARCHAR(255),
    enabled BOOLEAN NOT NULL,
    name VARCHAR(255) NOT NULL,
    sub_certificate_id BIGINT,
    CONSTRAINT fk_8y2iy66nn178prhlm8bbjd3ur FOREIGN KEY (sub_certificate_id) REFERENCES sub_certificates (id)
);
CREATE TABLE sub_certificates
(
    id BIGINT PRIMARY KEY NOT NULL,
    custom_name VARCHAR(255),
    enabled BOOLEAN NOT NULL,
    name VARCHAR(255) NOT NULL,
    certificate_id BIGINT,
    CONSTRAINT fk_n39y8w0nwp6hlsi97dphk3t7t FOREIGN KEY (certificate_id) REFERENCES certificates (id)
);
CREATE TABLE task_scores
(
    id BIGINT PRIMARY KEY NOT NULL,
    file_name VARCHAR(255),
    graded_date DATE NOT NULL,
    remarks VARCHAR(255),
    score VARCHAR(255),
    task_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_jmhg6e7bhsu8ili1cboulk8w9 FOREIGN KEY (task_id) REFERENCES tasks (id),
    CONSTRAINT fk_16cobpylwyi7jndvo7jtaj245 FOREIGN KEY (user_id) REFERENCES users (id)
);
CREATE TABLE tasks
(
    id BIGINT PRIMARY KEY NOT NULL,
    deadline DATE NOT NULL,
    description VARCHAR(255),
    name VARCHAR(255) NOT NULL,
    class_id BIGINT,
    CONSTRAINT fk_431k6ugsprd59yqaaw39o1shr FOREIGN KEY (class_id) REFERENCES classes (id)
);
CREATE UNIQUE INDEX uk_4082wti9ufd9w6jwf6cu7a59f ON tasks (name, class_id);
CREATE TABLE users
(
    id BIGINT PRIMARY KEY NOT NULL,
    email VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    password VARCHAR(128) NOT NULL,
    role VARCHAR(255) NOT NULL,
    sex VARCHAR(255) NOT NULL,
    parent_id BIGINT,
    CONSTRAINT fk_td7rkphgwhhgunswiyk8u0mvu FOREIGN KEY (parent_id) REFERENCES users (id)
);
CREATE UNIQUE INDEX uk_6dotkott2kjsp8vw4d0m25fb7 ON users (email);