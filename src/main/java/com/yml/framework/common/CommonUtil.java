package com.yml.framework.common;


import com.aventstack.extentreports.ExtentTest;
import com.google.gson.*;
import com.google.inject.Inject;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class CommonUtil {

    private ExtentTest currentTestCase;

    @Inject
    public Logger logger;



    public ExtentTest getCurrentTestInstance() {
        return currentTestCase;
    }

    public void setCurrentTestInstance(ExtentTest extentTest) {
        this.currentTestCase = extentTest;
    }


    public static void addFileHandler(Logger logger){

        FileHandler fh;
        try {

            // This block configure the logger with handler and formatter
            fh = new FileHandler("automation-log.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // the following statement is used to log any messages
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Object jsonsEqual(Object obj1, Object obj2) throws JSONException {

        JSONObject diff = new JSONObject();

        if (!obj1.getClass().equals(obj2.getClass())) {
            return diff;
        }

        if (obj1 instanceof JSONObject && obj2 instanceof JSONObject) {
            JSONObject jsonObj1 = (JSONObject) obj1;

            JSONObject jsonObj2 = (JSONObject) obj2;

            List<String> names = new ArrayList(Arrays.asList(JSONObject.getNames(jsonObj1)));
            List<String> names2 = new ArrayList(Arrays.asList(JSONObject.getNames(jsonObj2)));
            if (!names.containsAll(names2) && names2.removeAll(names)) {
                for (String fieldName : names2) {
                    if (jsonObj1.has(fieldName))
                        diff.put(fieldName, jsonObj1.get(fieldName));
                    else if (jsonObj2.has(fieldName))
                        diff.put(fieldName, jsonObj2.get(fieldName));
                }
                names2 = Arrays.asList(JSONObject.getNames(jsonObj2));
            }

            if (names.containsAll(names2)) {
                for (String fieldName : names) {
                    Object obj1FieldValue = jsonObj1.get(fieldName);
                    Object obj2FieldValue = jsonObj2.get(fieldName);
                    Object obj = jsonsEqual(obj1FieldValue, obj2FieldValue);
                    if (obj != null && !checkObjectIsEmpty(obj))
                        diff.put(fieldName, obj);
                }
            }
            return diff;
        } else if (obj1 instanceof JSONArray && obj2 instanceof JSONArray) {

            JSONArray obj1Array = (JSONArray) obj1;
            JSONArray obj2Array = (JSONArray) obj2;
            if (!obj1Array.toString().equals(obj2Array.toString())) {
                JSONArray diffArray = new JSONArray();
                for (int i = 0; i < obj1Array.length(); i++) {
                    Object obj = null;
                    matchFound:
                    for (int j = 0; j < obj2Array.length(); j++) {
                        obj = jsonsEqual(obj1Array.get(i), obj2Array.get(j));
                        if (obj == null) {
                            break matchFound;
                        }
                    }
                    if (obj != null)
                        diffArray.put(obj);
                }
                if (diffArray.length() > 0)
                    return diffArray;
            }
        } else {
            if (!obj1.equals(obj2)) {
                return obj2;
            }
        }

        return null;
    }

    private static boolean checkObjectIsEmpty(Object obj) {
        if (obj == null)
            return true;
        String objData = obj.toString();
        if (objData.length() == 0)
            return true;
        if (objData.equalsIgnoreCase("{}"))
            return true;
        return false;
    }

    /**
     * This method formats a json object of type JSONObject and
     * return formatted json as a string
     *
     * @param jsonString
     * @return formattedJSON as String
     */
    public static String getFormattedJSON(String jsonString) {

        String formattedJSON = null;

        try {


            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(jsonString);
            formattedJSON = gson.toJson(je);

        } catch (Exception e) {
            e.printStackTrace();
            return formattedJSON;

        }

        return formattedJSON;
    }

    public static String getProjectDir() {
        String projectRootDir = null;
        String userDirectory = System.getProperty("user.dir");
        int index = userDirectory.indexOf("\\src\\");
        projectRootDir = (index == -1) ? userDirectory : userDirectory.substring(0, index);
        return projectRootDir;
    }


    public static JsonObject getJsonObjectFromString(String json) {
        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        return jsonObject;

    }

    /**
     * This Method converts the provided input to Html formatted text
     * So that it can be printed in the Extent Report
     *
     * @param text
     * @return
     */
    public static String getStringForReport(String text) {

        return "<pre>" + text + "</pre>";
    }



    public static String prettyPrintHeaders(Map<String ,Object> headerMap){

        String prettyHeaders="";
        for (String key:headerMap.keySet()){
            prettyHeaders=prettyHeaders.concat(key+":"+headerMap.get(key)+"\n");
        }
        return prettyHeaders;
    }

    public  void logExecutionStart(){

       String methodName =Thread.currentThread().getStackTrace()[2].getMethodName();
        logger.info("\n\n************************* Execution Starts for:: "+methodName+" ***************\n\n");
    }

    public  void logExecutionEnd(){

        String methodName =Thread.currentThread().getStackTrace()[2].getMethodName();
        logger.info("\n\n************************* Execution Finished for:: "+methodName+" ***************\n\n");

    }


    public ExtentTest getTestCaseIfNull(ExtentTest currentTestCase){
       ExtentTest newTestCase=null;
        if(currentTestCase == null){
            String callerMethodName =Thread.currentThread().getStackTrace()[2].getMethodName();
            newTestCase.createNode(callerMethodName);
            return newTestCase;
        }
        else
            return currentTestCase;

    }

    public String removeWhiteSpaces(String str){
        if(str == null){
            return "";
        }
        else
            return  str.replaceAll("\\s", "").toLowerCase();
    }

    public String getScreenShot(WebDriver driver, String screenshotName) throws Exception {
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        //after execution, you could see a folder "FailedTestsScreenshots" under src folder
        String screenShotName=screenshotName+dateName+".png";
        String destination =  getProjectDir() + "/reports/"+screenShotName;
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return screenShotName;
    }

    public JSONArray readFileAsJSONArray(String resourceNameWithPath){

        JSONArray fileContent=null;
        File file = new File(resourceNameWithPath);
        try {
            String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
            fileContent = new JSONArray(content);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileContent;
    }


}
