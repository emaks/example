package com.ciklum.qatools.main.pages;

import com.ciklum.qatools.lib.AbstractPage;
import ru.yandex.qatools.allure.annotations.Step;

import static com.codeborne.selenide.Selenide.*;

public class LoginPage extends AbstractPage<LoginPage> {
    private LoginForm loginForm = new LoginForm();

    public LoginPage() {
        setMca("/login");
        setTitle("Login");
    }

    @Step("Login user")
    public MainPage login(String email, String password) {
        loginForm.fillForm(email, password);
        loginForm.submitForm();
        return page(MainPage.class).validate();
    }

    public LoginForm getLoginForm() {
        return loginForm;
    }

    public class LoginForm {
        @Step("Fill login form: email={0}, password={1}")
        public void fillForm(String email, String password) {
            $("#inputEmail3").setValue(email);
            $("#inputPassword3").setValue(password);
        }

        @Step("Click \'sign in\' button")
        public void submitForm() {
            $x("//button[text()='Sign in']").click();
        }
    }
}
