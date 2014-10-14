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

  val taskForm = Form(
    "label" -> nonEmptyText
  )

    def index = Action {
      Redirect(routes.Application.listTasks("anon"))
  }


  implicit val taskReads: Reads[Task] = (
      (JsPath \ "id").read[Long] and
      (JsPath \ "username").read[String] and
      (JsPath \ "label").read[String])(Task.apply _)

  implicit val taskWrites: Writes[Task] = (
      (JsPath \ "id").write[Long] and
      (JsPath \ "username").write[String] and
      (JsPath \ "label").write[String])(unlift(Task.unapply))

  def listTasks = Action {
    val json = Json.toJson(Task.all())
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
  
  /*def saveTask = Action { implicit request =>
    taskForm.bindFromRequest.fold(
      errors => BadRequest("Error al crear tarea"),
      label => {
        Task.create(label)
        Created("Tarea creada"+Json.toJson(Task.porLabel(label)))
      }
    )
  }*/
  
  def deleteTask(id: Long) = Action {
    if (Task.porId(id) == None) {
      NotFound("No se ha encontrado la tarea mencionada")
    }
    else {
      Task.delete(id)
      Redirect(routes.Application.listTasks)
    }
  }
  
}