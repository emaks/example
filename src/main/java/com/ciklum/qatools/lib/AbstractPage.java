package com.ciklum.qatools.lib;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.hamcrest.Matchers;
import ru.yandex.qatools.allure.annotations.Step;

import static org.hamcrest.MatcherAssert.assertThat;

public abstract class AbstractPage<T> {
    private String mca;
    private String title;

    protected void setMca(String value) {
        mca = value;
    }

    protected void setTitle(String value) {
        title = value;
    }

    @Step("Open page")
    public T open() {
        Selenide.open(getUrl());
        return validate();
    }

    public T validate() {
        validateUrl();
        validateTitle();
        return (T) this;
    }

    private void validateUrl() {
        assertThat("Opened page has wrong url", WebDriverRunner.url(), Matchers.equalTo(getUrl()));
    }

    private void validateTitle() {
        assertThat("Opened page has wrong title", Selenide.title(), Matchers.equalTo(title));
    }

    private String getUrl() {
        return "http://localhost:8086" + mca;
    }
}
