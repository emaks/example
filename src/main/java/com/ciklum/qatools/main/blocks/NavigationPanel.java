package com.ciklum.qatools.main.blocks;

import org.hamcrest.Matchers;
import ru.yandex.qatools.allure.annotations.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static org.hamcrest.MatcherAssert.assertThat;

public class NavigationPanel {
    @Step("Click on \'users\' link")
    public void openUsersPage() {
        $("#users_link").click();
    }

    @Step("Verify that logged user name is {0}")
    public void assertLoggedUserNameEqual(String name) {
        assertThat(
            "Logged user has wrong name", $x("//span[contains(text(),'username:')]/*").getText(), Matchers.equalTo(name)
        );
    }

    @Step("Verify that logged user role is {0}")
    public void assertLoggedUserRoleEqual(String role) {
        assertThat(
            "Logged user has wrong role", $x("//span[contains(text(),'role:')]/*").getText(), Matchers.equalTo(role)
        );
    }
}
