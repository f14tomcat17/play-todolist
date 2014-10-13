# Tasks schema
 
# --- !Ups

CREATE SEQUENCE task_id_seq;
CREATE TABLE task (
    id integer NOT NULL DEFAULT nextval('task_id_seq'),
    username varchar(255),
    label varchar(255),
  	constraint pk_task primary key (id)
);

CREATE TABLE task_user (
	name varchar(255),
	constraint pk_user primary key(name)
);

ALTER TABLE task add constraint fk_user foreign key (username) references task_user (name) on delete restrict on update restrict;
 
# --- !Downs
 
DROP TABLE task;
DROP TABLE task_user;
DROP SEQUENCE task_id_seq;