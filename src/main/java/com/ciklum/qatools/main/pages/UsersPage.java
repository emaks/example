package com.ciklum.qatools.main.pages;

import com.ciklum.qatools.lib.AbstractPage;
import com.ciklum.qatools.main.blocks.NavigationPanel;
import com.codeborne.selenide.SelenideElement;
import org.apache.commons.collections.MapUtils;
import org.hamcrest.Matchers;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.hamcrest.MatcherAssert.assertThat;

public class UsersPage extends AbstractPage<UsersPage> {
    private NavigationPanel navigationPanel = new NavigationPanel();
    private NewUserForm newUserForm = new NewUserForm();
    private UsersTable usersTable = new UsersTable();

    public UsersPage() {
        setMca("/users");
        setTitle("Users");
    }

    @Step("Add user")
    public void addUser(Map<String, String> userData) {
        newUserForm.fillForm(userData);
        newUserForm.submitForm();
    }

    public UsersTable getUsersTable() {
        return usersTable;
    }

    private class NewUserForm {
        @Step("Fill user form using data {0}")
        void fillForm(Map<String, String> userData) {
            $("#inputNewUserName").setValue(MapUtils.getString(userData, "name", ""));
            $("#inputNewUserPassword").setValue(MapUtils.getString(userData, "password", ""));
            $("#newUserEmail").setValue(MapUtils.getString(userData, "email", ""));
        }

        void submitForm() {
            $("#add_new_user_btn").click();
        }
    }

    public class UsersTable {
        private List<SelenideElement> getUsers(String userName) {
            return $$("#users_table>tbody>tr").stream()
                .filter(row -> row.$("td:nth-of-type(1)").getText().equals(userName))
                .collect(Collectors.toList());
        }

        @Step("Verify that user with name {0} is exist")
        public void assertUserExist(String userName) {
            assertThat("User is not exist", getUsers(userName), Matchers.hasSize(Matchers.equalTo(1)));
        }

        @Step("Verify that user with name {0} has email {1}")
        public void assertUserEmailEqual(String userName, String email) {
            assertThat(
                "Wrong email", getUsers(userName).get(0).$("td:nth-of-type(3)").getText(), Matchers.equalTo(email)
            );
        }
    }
}
