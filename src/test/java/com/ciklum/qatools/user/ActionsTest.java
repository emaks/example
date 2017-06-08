package com.ciklum.qatools.user;

import com.ciklum.qatools.lib.Utils;
import com.ciklum.qatools.main.pages.LoginPage;
import com.ciklum.qatools.main.pages.MainPage;
import com.ciklum.qatools.main.pages.UsersPage;
import com.google.common.collect.ImmutableMap;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;

public class ActionsTest {
    private LoginPage loginPage;

    @BeforeMethod
    public void before() {
        ChromeDriverManager.getInstance().setup();
        System.setProperty("selenide.browser", "Chrome");
        loginPage = page(LoginPage.class).open();
    }

    @AfterMethod
    public void after() {
        closeWebDriver();
    }

    @Test
    public void validLogin() {
        MainPage mainPage = loginPage.login("admin", "admin");

        mainPage.getNavigationPanel().assertLoggedUserNameEqual("admin");
        mainPage.getNavigationPanel().assertLoggedUserRoleEqual("admin");
    }

    @Test
    public void invalidLogin() {
        loginPage.getLoginForm().fillForm("admin", "wrongPass");
        loginPage.getLoginForm().submitForm();

        loginPage.validate();
    }

    @Test
    public void addNewUser() {
        String randomPart = RandomStringUtils.randomAlphabetic(7).toLowerCase();
        Map<String, String> newUser = ImmutableMap.of(
            "name", randomPart + "User",
            "password", "123123q",
            "email", randomPart + "-user@example.com"
        );

        UsersPage usersPage = loginPage.login("admin", "admin")
            .openUsersPage();
        usersPage.addUser(newUser);

        usersPage.getUsersTable().assertUserExist(newUser.get("name"));
        usersPage.getUsersTable().assertUserEmailEqual(newUser.get("name"), newUser.get("email"));
    }

    @Test
    public void uploadNewVideo() {
        String file = "1mbVideo.flv";

        MainPage mainPage = loginPage.login("admin", "admin");
        mainPage.getFileUploader().upload(Utils.getResourcePath(file));

        mainPage.getFilesTable().assertFileUploaded(file);
    }

    @Test(dependsOnMethods = {"uploadNewVideo"})
    public void deleteNewVideo() {
        String file = "1mbVideo.flv";

        MainPage mainPage = loginPage.login("admin", "admin");
        mainPage.getFilesTable().deleteFile(file);

        mainPage.getFilesTable().assertFileDeleted(file);
    }
}
