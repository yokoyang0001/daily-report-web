package controllers
import forms.{UserDeleteForm, UserForm, UserUpdateForm}
import javax.inject.{Inject, Singleton}
import models.User
import org.mindrot.jbcrypt.BCrypt
import play.api.mvc._
import play.filters.csrf.CSRF

@Singleton
class UserController @Inject()(
                                cc: ControllerComponents,
                                authenticatedController: AuthenticatedController
                              ) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def index() = authenticatedController { implicit request: Request[AnyContent] =>
    val users = User.find().findList()
    Ok(views.html.users(users))
  }

  def detail(id: Long) = authenticatedController { implicit request: Request[AnyContent] =>
    val user = User.find(id)
    val token: Option[CSRF.Token] = CSRF.getToken
    val errorMessage: String = ""
    Ok(views.html.user_detail(user, token.get.value, errorMessage))
  }

  def create() = authenticatedController { implicit request =>
    val token: Option[CSRF.Token] = CSRF.getToken
    val errorMessage: String = ""
    Ok(views.html.user_create(token.get.value, errorMessage))
  }

  def register() = authenticatedController { implicit request =>
    UserForm.form.bindFromRequest.fold(
      formWithErrors => {
        val token: Option[CSRF.Token] = CSRF.getToken
        val errorMessage: String = "Login ID or password is incorrect"
        BadRequest(views.html.login(token.get.value, errorMessage))
      },
      formData => {
        val user = new User
        user.loginId = formData.loginId
        val salt: String = BCrypt.gensalt()
        user.password = BCrypt.hashpw(formData.password, salt)
        user.name = formData.name
        user.role = formData.role
        User.save(user)
        val users = User.find().findList()
        if (users != null) {
          Redirect(routes.UserController.index())
        } else {
          val token: Option[CSRF.Token] = CSRF.getToken
          val errorMessage: String = "Login ID or password is incorrect"
          Ok(views.html.user_create(token.get.value, errorMessage))
        }
      }
    )
  }

  def edit(id: Long) = authenticatedController { implicit request =>
    val token: Option[CSRF.Token] = CSRF.getToken
    val errorMessage: String = ""
    val user = User.find(id)
    Ok(views.html.user_edit(user, token.get.value, errorMessage))
  }

  def update() = authenticatedController { implicit request =>
    UserUpdateForm.form.bindFromRequest.fold(
      formWithErrors => {
        val token: Option[CSRF.Token] = CSRF.getToken
        val errorMessage: String = "Login ID or password is incorrect"
        BadRequest(views.html.login(token.get.value, errorMessage))
      },
      formData => {
        val user = User.find(formData.id)
        user.name = formData.name
        user.role = formData.role
        User.save(user)
        val users = User.find().findList()
        if (users != null) {
          Redirect(routes.UserController.index())
        } else {
          val token: Option[CSRF.Token] = CSRF.getToken
          val errorMessage: String = "Login ID or password is incorrect"
          Ok(views.html.user_edit(user, token.get.value, errorMessage))
        }
      }
    )
  }

  def delete() = authenticatedController { implicit request =>
    UserDeleteForm.form.bindFromRequest.fold(
      formWithErrors => {
        Redirect(routes.UserController.detail(formWithErrors.value.get.id))
      },
      formData => {
        val user = User.find(formData.id)
        User.delete(user)
        val users = User.find().findList()
        if (users != null) {
          Redirect(routes.UserController.index())
        } else {
          val token: Option[CSRF.Token] = CSRF.getToken
          val errorMessage: String = "Error Delete user"
          Ok(views.html.user_detail(user, token.get.value, errorMessage))
        }
      }
    )
  }
}
