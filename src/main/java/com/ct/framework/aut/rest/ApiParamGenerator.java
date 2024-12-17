package com.ct.framework.aut.rest;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiParamGenerator {


    public static JSONObject getSignInParams(String email, String password) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", email);
        jsonObject.put("password", password);
        return jsonObject;
    }

}
