package com.tweesky.cloudtools.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AppInfoTest {

    @Test
    void isNotJson() {
        String json = "";

        Assertions.assertThrows(RuntimeException.class, () -> new AppInfo(json));
    }

    @Test
    void appInfo() {
        AppInfo appInfo = new AppInfo(getJsonResponse());

        assertEquals("downstreamservice", appInfo.getName());
        assertEquals("eu", appInfo.getRegion());
        assertEquals("/bin/sh -c java\\ -Dserver.port\\=\\$PORT\\ \\$JAVA_OPTS\\ -jar\\ /software/rest1.jar", appInfo.getCommand());
    }

    private String getJsonResponse() {
        return "" +
                "{  \"addons\": [],  \"app\": {    \"acm\": false,    \"archived_at\": null,    " +
                "\"buildpack_provided_description\": \"Container\",    " +
                "\"build_stack\": {      \"id\": \"fa15409e-a5da-4998-9954-0e865cb1a19f\",      \"name\": \"container\"    },   " +
                " \"created_at\": \"2019-08-27T07:39:46Z\",    \"id\": \"9ab14e1b-b50a-4c54-bc16-685feaaf69f3\",    " +
                "\"git_url\": \"https://git.heroku.com/downstreamservice.git\",    \"maintenance\": false,    " +
                "\"name\": \"downstreamservice\",    \"owner\": {      \"email\": \"beppe.catanese@gmail.com\",     " +
                " \"id\": \"b0ecd8cd-4f67-4610-827f-78bdae590127\"    },    \"region\": " +
                "{      \"id\": \"ed30241c-ed8c-4bb6-9714-61953675d0b4\",      \"name\": \"eu\"    },    " +
                "\"organization\": null,    \"team\": null,    \"space\": null,    \"internal_routing\": null,    " +
                "\"released_at\": \"2022-02-26T09:32:44Z\",    \"repo_size\": 31310,    \"slug_size\": null,    " +
                "\"stack\": {      \"id\": \"fa15409e-a5da-4998-9954-0e865cb1a19f\",      \"name\": \"container\"    }," +
                "    \"updated_at\": \"2022-02-26T09:32:44Z\",    \"web_url\": \"https://downstreamservice.herokuapp.com/\"  }," +
                "  \"dynos\": [    {      \"attach_url\": null,      " +
                "\"command\": \"/bin/sh -c java\\\\ -Dserver.port\\\\=\\\\$PORT\\\\ \\\\$JAVA_OPTS\\\\ -jar\\\\ /software/rest1.jar\",      " +
                "\"created_at\": \"2022-03-01T02:43:09Z\",      \"id\": \"f14eca59-b987-4f8d-a456-e7542db1c9dd\",     " +
                " \"name\": \"web.1\",      \"app\": {        \"id\": \"9ab14e1b-b50a-4c54-bc16-685feaaf69f3\",        " +
                "\"name\": \"downstreamservice\"      },      \"release\": {        \"id\": \"be1556a9-b0d7-49cf-9634-96826b463dc7\",       " +
                " \"version\": 33      },      \"size\": \"Free\",      \"state\": \"crashed\",     " +
                " \"type\": \"web\",      \"updated_at\": \"2022-03-01T02:43:09Z\"    }  ],  " +
                "\"collaborators\": [    {      \"created_at\": \"2019-08-27T07:39:46Z\",     " +
                " \"id\": \"b0ecd8cd-4f67-4610-827f-78bdae590127\",      \"updated_at\": \"2019-08-27T07:39:46Z\",      " +
                "\"user\": {        \"email\": \"beppe.catanese@gmail.com\",        \"federated\": false,        " +
                "\"id\": \"b0ecd8cd-4f67-4610-827f-78bdae590127\"      },      \"app\": {       " +
                " \"id\": \"9ab14e1b-b50a-4c54-bc16-685feaaf69f3\",        \"name\": \"downstreamservice\"      },     " +
                " \"role\": \"owner\"    }  ],  \"pipeline_coupling\": null}\n" +
                "";
    }
}
