package models
import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class Task(id: Long, username: String, label: String)

object Task {  

val task = {
  get[Long]("id") ~
  get[String]("username") ~ 
  get[String]("label") map {
    case id~username~label => Task(id, username, label)
  }
}

 def porUsuario(username: String): List[Task] = {
    DB.withConnection { implicit connection =>
      SQL("select * from task where username = {username}").on('username -> username).as(Task.task *)
    }
  }

   def porUsuarioLabel(username: String, label:String): Option[Task] = {
    DB.withConnection { implicit connection =>
      SQL("select * from task where username = {username} and label = {label}").on(
        'username -> username,
        'label -> label).as(Task.task.singleOpt)
    }
  }

  def usuarioPorNombre(name:String): Option[String] = {
    DB.withConnection { implicit connection =>
      SQL("select name from task_user where name = {name}").on('name -> name).as(str("name").singleOpt)
    }
  }


  def porId(id: Long): Option[Task] = {
    DB.withConnection { implicit connection =>
      SQL("select * from task where id = {id}").on('id -> id).as(Task.task.singleOpt)
    }
  }

  def all(): List[Task] = DB.withConnection { implicit c =>
  SQL("select * from task").as(task *)
}
def create(username: String, label: String):Long = {
  DB.withConnection { implicit c =>
    SQL("insert into task values ((select next value for task_id_seq),{username}, {label})").on(
      'username -> username,
      'label -> label
    ).executeUpdate()
  }
}

def delete(id: Long) {
  DB.withConnection { implicit c =>
    SQL("delete from task where id = {id}").on(
      'id -> id
    ).executeUpdate()
  }
}
  
}