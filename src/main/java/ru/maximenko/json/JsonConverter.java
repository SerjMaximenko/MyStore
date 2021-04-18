package ru.maximenko.json;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

public class JsonConverter {

    private InputStream inputStream;

    public JsonConverter(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public JSONObject getJsonObject() throws JSONException, IOException {

        InputStream inputStreamObject = inputStream;


        BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStreamObject, "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();

        String inputStr;
        while ((inputStr = streamReader.readLine()) != null)
            responseStrBuilder.append(inputStr);

        JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());

        return jsonObject;



//        //if something went wrong, return null
//        return null;
    }

}