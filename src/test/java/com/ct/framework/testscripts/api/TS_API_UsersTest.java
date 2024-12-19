package com.ct.framework.testscripts.api;

import com.ct.framework.aut.rest.ApiEndPoints;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class TS_API_UsersTest extends  TS_API_BaseTest{




    @Test
    public void tc001_list_users(){
        commonUtil.logExecutionStart();
        currentTestCase.info("test started");
        String list_user_url=super.apiUrl+ ApiEndPoints.LIST_USERS;
        Map<String, Object> params=new HashMap<>();
        params.put("page",2);
        Response response=requestUtil.getRequestWithHeadersAndParam(list_user_url,new HashMap<>(),params,true);
        //currentTestCase.pass(MarkupHelper.createCodeBlock(response.asString(),CodeLanguage.JSON));

    }
}
