package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.json.Writes._
import play.api.libs.functional.syntax._

import views._
import models._

object Application extends Controller {


    def index = Action {
      Redirect(routes.Application.listTasks("anon"))
  }
  val taskForm = Form(
      "label" -> nonEmptyText
  )



  implicit val taskReads: Reads[Task] = (
      (JsPath \ "id").read[Long] and
      (JsPath \ "username").read[String] and
      (JsPath \ "label").read[String])(Task.apply _)

  implicit val taskWrites: Writes[Task] = (
      (JsPath \ "id").write[Long] and
      (JsPath \ "username").write[String] and
      (JsPath \ "label").write[String])(unlift(Task.unapply))

  def listTasks (username:String) = Action {
    val json = Json.toJson(Task.porUsuario(username))
    Ok(json)
  }


  def getTask (id:Long) = Action {
    val json = Json.toJson(Task.porId(id))
    if(json == null) {
      NotFound("Task "+id+" not found")
    } 
    else {
      Ok(json)
    }
  }


  def saveTask (username:String)= Action { implicit request =>
    taskForm.bindFromRequest.fold(
      errors => BadRequest("Error al crear tarea"),
      label => {
        if(Task.usuarioPorNombre(username)==None) {
          NotFound("User "+username+" not found")
        }
        else {
          Task.create(username, label)
          val json = Json.toJson(Task.porUsuarioLabel(username, label))
          Created("Tarea creada "+json)
        }
      }
    )
  }

  def deleteTask(id: Long) = Action {
    if (Task.porId(id) == None) {
      NotFound("No se ha encontrado la tarea mencionada")
    }
    else {
      Task.delete(id)
      Ok("Borrada correctamente")
    }
  }
  
}