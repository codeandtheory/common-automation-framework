package com.yml.api;

import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.yml.loreal.common.CommonUtil;
import com.yml.loreal.rest.ApiEndPoints;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
