# Play TodoList v1.0

En esta práctica 1 desarrollamos una API REST basada en el proyecto de la 
práctica anterior.Sustituimos el interfaz mediante Forms y creamos un control
 de peticiones y respuestas mediante HTTP.

# Feature 1

Consistente en crear la API REST básica. Gracias a ello, trabajamos con las 
peticiones GET, POST y DELETE y mediante ello podemos trabajar con la lista de tareas, 
ya sea mediante consultas, inserciones o borrados.

El archivo routes está configurado de tal manera que GET /tasks redirige a la función de 
listar tareas (listTasks), y añadiendo el argumento id (GET /tasks/id) hacemos 
un getTask con dicho argumento. DELETE /tasks/id funciona de forma parecida para el borrado. De igual modo, con POST /tasks creamos una 
nueva tarea con los argumentos que le pasamos en el cuerpo de la petición HTTP.

# Feature 2

Ahora la API REST trabaja con usuarios. Las tareas se relacionan directamente con dichos usuarios, no 
pudiendo ser creadas sin pertenecer a alguno de ellos. Para ello se ha modificado 
la BD para añadir una tabla nueva (task_user) y se ha alterado la existente para añadir un atributo extra (username) que tome valores de estos. 
Además, ahora existe un usuario 'anon' para contemplar las peticiones HTTP de la feature anterior (GET /tasks o POST /tasks), mientras que para el resto de los usuarios es necesario introducir 
el usuario en la URL (GET /{login}/tasks/ o POST /{login}/tasks).

# Feature 3

Próximamente...

# Heroku

Desplegada en el siguiente enlace:

http://hidden-inlet-2087.herokuapp.com/
