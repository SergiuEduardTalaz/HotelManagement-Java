package ro.fortech.academy.presentation.login;

public class LoginController {
    private final LoginModel model;
    private LoginView view;

    public LoginController(LoginModel model, LoginView view) {
        this.model = model;
        this.view = view;
    }

    public void setLoginView(LoginView loginView) {
        this.view = loginView;
    }

    public void attemptLogin(String username, String password) {
        boolean isValidUser = model.isValidUser(username, password);
        if (isValidUser) {
            view.resetErrorMessage();
            view.showMainApp();
        } else {
            view.showLoginError();
        }
    }
}
