package com.ciklum.qatools.lib;

import org.hamcrest.Matchers;

import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;

public class Utils {
    public static String getResourcePath(String path) {
        URL resource = Utils.class.getClassLoader().getResource(path);
        assertThat("Wrong resource path - " + path, resource, Matchers.notNullValue());
        return resource.getPath();
    }
}
