SELECT * FROM escuelas.examen;
INSERT INTO categoria (alta, nombre) VALUES (1, 'BACKEND');
INSERT INTO categoria (alta, nombre) VALUES (1, 'FRONTEND');
INSERT INTO categoria (alta, nombre) VALUES (1, 'BASE DE DATOS');

INSERT INTO rol (alta, nombre) VALUES (true, 'ADMIN');
INSERT INTO rol (alta, nombre) VALUES (true, 'USER');

INSERT INTO tematica (alta, nombre, categoria_id) VALUES (true, "JAVA", 1);
INSERT INTO tematica (alta, nombre, categoria_id) VALUES (true, "SQL", 3);
INSERT INTO tematica (alta, nombre, categoria_id) VALUES (true, "CSS", 2);
INSERT INTO tematica (alta, nombre, categoria_id) VALUES (true, "HTML", 2);
INSERT INTO tematica (alta, nombre, categoria_id) VALUES (true, "PYTHON", 1);
INSERT INTO tematica (alta, nombre, categoria_id) VALUES (true, "MONGO DB", 3);

INSERT INTO examen (alta, dificultad, fecha_creacion, fecha_modificacion, nota_requerida, tematica_id,nombre) VALUES (true, null, '2021-12-07', '2021-12-07', '60', '1','Intro java');
INSERT INTO examen (alta, dificultad, fecha_creacion, fecha_modificacion, nota_requerida, tematica_id,nombre) VALUES (true, null, '2021-11-07', '2021-11-07', '80', '2','SQL Basic');
INSERT INTO examen (alta, dificultad, fecha_creacion, fecha_modificacion, nota_requerida, tematica_id,nombre) VALUES (true, null, '2021-10-07', '2021-10-07', '40', '4','Intro HTML');
INSERT INTO examen (alta, dificultad, fecha_creacion, fecha_modificacion, nota_requerida, tematica_id,nombre) VALUES (true, null, '2021-12-09', '2021-12-09', '60', '3','Aprendiendo CSS');
INSERT INTO examen (alta, dificultad, fecha_creacion, fecha_modificacion, nota_requerida, tematica_id,nombre) VALUES (true, null, '2021-12-12', '2021-12-12', '70', '5','Python Basic');
INSERT INTO examen (alta, dificultad, fecha_creacion, fecha_modificacion, nota_requerida, tematica_id,nombre) VALUES (true, null, '2021-12-10', '2021-12-10', '50', '6','Introduccion y teoria MongoDB');

INSERT INTO pregunta (alta, dificultad, enunciado, puntaje, respuesta_correcta, examen_id) VALUES (true, null, 'enunciado1', 15, '1', 3);
INSERT INTO pregunta (alta, dificultad, enunciado, puntaje, respuesta_correcta, examen_id) VALUES (true, null, 'enunciado2', 10, '1', 3);
INSERT INTO pregunta (alta, dificultad, enunciado, puntaje, respuesta_correcta, examen_id) VALUES (true, null, 'enunciado3', 15, '1', 3);
INSERT INTO pregunta (alta, dificultad, enunciado, puntaje, respuesta_correcta, examen_id) VALUES (true, null, 'enunciado4', 20, '1', 3);
INSERT INTO pregunta (alta, dificultad, enunciado, puntaje, respuesta_correcta, examen_id) VALUES (true, null, 'enunciado5', 30, '1', 3);
INSERT INTO pregunta (alta, dificultad, enunciado, puntaje, respuesta_correcta, examen_id) VALUES (true, null, 'enunciado6', 10, '1', 3);

INSERT INTO pregunta_respuestas (respuestas) values (Correctas);

INSERT INTO pregunta (alta, dificultad, enunciado, puntaje, respuesta_correcta, examen_id) VALUES (true, null, 'enunciado1', 30, '1', 4);
INSERT INTO pregunta (alta, dificultad, enunciado, puntaje, respuesta_correcta, examen_id) VALUES (true, null, 'enunciado2', 20, '1', 4);
INSERT INTO pregunta (alta, dificultad, enunciado, puntaje, respuesta_correcta, examen_id) VALUES (true, null, 'enunciado3', 10, '1', 4);
INSERT INTO pregunta (alta, dificultad, enunciado, puntaje, respuesta_correcta, examen_id) VALUES (true, null, 'enunciado4', 20, '1', 4);
INSERT INTO pregunta (alta, dificultad, enunciado, puntaje, respuesta_correcta, examen_id) VALUES (true, null, 'enunciado5', 10, '1', 4);
INSERT INTO pregunta (alta, dificultad, enunciado, puntaje, respuesta_correcta, examen_id) VALUES (true, null, 'enunciado6', 10, '1', 4);

update