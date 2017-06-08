package com.ciklum.qatools.main.pages;

import com.ciklum.qatools.lib.AbstractPage;
import com.ciklum.qatools.main.blocks.NavigationPanel;
import com.codeborne.selenide.SelenideElement;
import org.hamcrest.Matchers;
import ru.yandex.qatools.allure.annotations.Step;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class MainPage extends AbstractPage<MainPage> {
    private NavigationPanel navigationPanel = new NavigationPanel();
    private FileUploader fileUploader = new FileUploader();
    private FilesTable filesTable = new FilesTable();

    public MainPage() {
        setMca("/index");
        setTitle("Index");
    }

    @Step("Open users page")
    public UsersPage openUsersPage() {
        navigationPanel.openUsersPage();
        return page(UsersPage.class).validate();
    }

    public NavigationPanel getNavigationPanel() {
        return navigationPanel;
    }

    public FileUploader getFileUploader() {
        return fileUploader;
    }

    public FilesTable getFilesTable() {
        return filesTable;
    }

    public class FileUploader {
        @Step("Upload file {0}")
        public void upload(String filePath) {
            $("[id='filestyle-0']").uploadFile(new File(filePath));
            $("#upload_submit>button").click();
        }
    }

    public class FilesTable {
        @Step("Delete file {0}")
        public void deleteFile(String file) {
            List<SelenideElement> files = getFiles(file);
            assertThat("There is no file with name " + file, files, Matchers.hasSize(Matchers.equalTo(1)));

            files.get(0).$("#delete_item").click();
            $("#confirm-delete a.btn-ok").click();
        }

        private List<SelenideElement> getFiles(String fileName) {
            return $$("#accordion").stream()
                .filter(row -> row.$("a[data-parent='#accordion']").getText().equals(fileName))
                .collect(Collectors.toList());
        }

        @Step("Verify that file {0} is uploaded")
        public void assertFileUploaded(String file) {
            assertThat("File is not uploaded", getFiles(file), Matchers.hasSize(Matchers.equalTo(1)));
        }

        @Step("Verify that file {0} is deleted")
        public void assertFileDeleted(String file) {
            assertThat("File is not deleted", getFiles(file), Matchers.hasSize(Matchers.equalTo(0)));
        }
    }
}
