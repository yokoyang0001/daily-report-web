package controllers

import forms._
import javax.inject.{Inject, Singleton}
import models.{Project, ProjectUser, User}
import play.api.mvc._
import play.filters.csrf.CSRF

@Singleton
class ProjectController @Inject()(
                                cc: ControllerComponents,
                                authenticatedController: AuthenticatedController,
                              ) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def index() = authenticatedController { implicit request: Request[AnyContent] =>
    val projects = Project.find().findList()
    Ok(views.html.projects(projects))
  }

  def detail(id: Long) = authenticatedController { implicit request: Request[AnyContent] =>
    val project = Project.find(id)
    val token: Option[CSRF.Token] = CSRF.getToken
    val errorMessage: String = ""
    Ok(views.html.project_detail(project, token.get.value, errorMessage))
  }

  def create() = authenticatedController { implicit request =>
    val token: Option[CSRF.Token] = CSRF.getToken
    val errorMessage: String = ""
    Ok(views.html.project_create(token.get.value, errorMessage))
  }

  def register() = authenticatedController { implicit request =>
    ProjectForm.form.bindFromRequest.fold(
      formWithErrors => {
        val token: Option[CSRF.Token] = CSRF.getToken
        val errorMessage: String = "Login ID or password is incorrect"
        BadRequest(views.html.login(token.get.value, errorMessage))
      },
      formData => {
        val project = new Project
        project.name = formData.name
        project.content = formData.content
        Project.save(project)
        val projects = Project.find().findList()
        if (projects != null) {
          Redirect(routes.ProjectController.index())
        } else {
          val token: Option[CSRF.Token] = CSRF.getToken
          val errorMessage: String = "Login ID or password is incorrect"
          Ok(views.html.project_create(token.get.value, errorMessage))
        }
      }
    )
  }

  def edit(id: Long) = authenticatedController { implicit request =>
    val token: Option[CSRF.Token] = CSRF.getToken
    val errorMessage: String = ""
    val project = Project.find(id)
    Ok(views.html.project_edit(project, token.get.value, errorMessage))
  }

  def update() = authenticatedController { implicit request =>
    ProjectUpdateForm.form.bindFromRequest.fold(
      formWithErrors => {
        val token: Option[CSRF.Token] = CSRF.getToken
        val errorMessage: String = "Login ID or password is incorrect"
        BadRequest(views.html.login(token.get.value, errorMessage))
      },
      formData => {
        val project = Project.find(formData.id)
        project.name = formData.name
        project.content = formData.content
        Project.save(project)
        val projects = Project.find().findList()
        if (projects != null) {
          Redirect(routes.ProjectController.index())
        } else {
          val token: Option[CSRF.Token] = CSRF.getToken
          val errorMessage: String = "Login ID or password is incorrect"
          Ok(views.html.project_edit(project, token.get.value, errorMessage))
        }
      }
    )
  }

  def delete() = authenticatedController { implicit request =>
    ProjectDeleteForm.form.bindFromRequest.fold(
      formWithErrors => {
        Redirect(routes.ProjectController.detail(formWithErrors.value.get.id))
      },
      formData => {
        val project = Project.find(formData.id)
        Project.delete(project)
        val projects = Project.find().findList()
        if (projects != null) {
          Redirect(routes.ProjectController.index())
        } else {
          val token: Option[CSRF.Token] = CSRF.getToken
          val errorMessage: String = "Error Delete project"
          Ok(views.html.project_detail(project, token.get.value, errorMessage))
        }
      }
    )
  }

  def user(id: Long) = authenticatedController { implicit request =>
    val token: Option[CSRF.Token] = CSRF.getToken
    val errorMessage: String = ""
    val project = Project.find(id)
    val users = User.find().findList()
    Ok(views.html.project_users(project, users, token.get.value, errorMessage))
  }


  def userAssign() = authenticatedController { implicit request =>
    ProjectUserAssignForm.form.bindFromRequest.fold(
      formWithErrors => {
        val token: Option[CSRF.Token] = CSRF.getToken
        val errorMessage: String = "Login ID or password is incorrect"
        BadRequest(views.html.login(token.get.value, errorMessage))
      },
      formData => {
        val projectUser = new ProjectUser
        val user = User.find(formData.userId)
        projectUser.user = user
        val project = Project.find(formData.projectId)
        projectUser.project = project
        projectUser.role = User.ROLE_MEMBER
        ProjectUser.save(projectUser)
        Redirect(routes.ProjectController.index())
      }
    )
  }

  def userWithdraw() = authenticatedController { implicit request =>
    ProjectUserWithDrawForm.form.bindFromRequest.fold(
      formWithErrors => {
        val token: Option[CSRF.Token] = CSRF.getToken
        val errorMessage: String = "Login ID or password is incorrect"
        BadRequest(views.html.login(token.get.value, errorMessage))
      },
      formData => {
        val projectUser = ProjectUser.find(formData.id)
        ProjectUser.delete(projectUser)
        Redirect(routes.ProjectController.index())
      }
    )
  }
}
