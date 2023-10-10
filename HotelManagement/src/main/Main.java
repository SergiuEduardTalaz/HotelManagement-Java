package ro.fortech.academy;


import ro.fortech.academy.presentation.login.LoginController;
import ro.fortech.academy.presentation.login.LoginModel;
import ro.fortech.academy.presentation.login.LoginView;


public class Main {
    public static void main(String[] args) {
        LoginModel model = new LoginModel();
        LoginView view = new LoginView();
        LoginController controller = new LoginController(model, view);
        controller.setLoginView(view);
        view.setLoginController(controller);
    }
}