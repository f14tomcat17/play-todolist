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
    Redirect(routes.Application.listTasks)
  }


  implicit val taskReads: Reads[Task] = (
      (JsPath \ "id").read[Long] and
      (JsPath \ "label").read[String])(Task.apply _)

  implicit val taskWrites: Writes[Task] = (
      (JsPath \ "id").write[Long] and
      (JsPath \ "label").write[String])(unlift(Task.unapply))

  def listTasks = Action {
    val json = Json.toJson(Task.all())
    Ok(json)
  }

    def getTask (id:Long) = Action {
    val json = Json.toJson(Task.porId(id))
    Ok(json)
  }
  
/*def newTask = Action { implicit request =>
  taskForm.bindFromRequest.fold(
    errors => BadRequest(views.html.index(Task.all(), errors)),
    label => {
      Task.create(label)
      Redirect(routes.Application.tasks)
    }
  )
}*/
  
def deleteTask(id: Long) = Action {
  Task.delete(id)
  Redirect(routes.Application.listTasks)
}
  
}