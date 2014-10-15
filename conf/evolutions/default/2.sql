# --- Inserciones

# --- !Ups
insert into task_user(name) values ('daniel');
insert into task_user(name) values ('anon');
insert into task_user(name) values ('pepito');

# --- !Downs

delete from task;
delete from task_user;
