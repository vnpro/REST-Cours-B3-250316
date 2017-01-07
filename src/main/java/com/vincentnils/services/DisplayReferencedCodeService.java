package com.vincentnils.services;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DisplayReferencedCodeService {
    private static DisplayReferencedCodeService ourInstance = new DisplayReferencedCodeService();

    public static DisplayReferencedCodeService getInstance() {
        return ourInstance;
    }

    Map<String, Map<String, String>> queryMap;

    private DisplayReferencedCodeService() {
        queryMap = new HashMap<>();

        // JAVA
        Map javaMap = new HashMap<>();
        javaMap.put("class creation", "https://docs.google.com/document/d/1Sc4wtEaZ8zwBL6bikvLhrhNADa7ksRijg731vFc3YXY");
        javaMap.put("function creation", "https://docs.google.com/document/d/1eiyoN1xqfk4WJUzldILYBmRgR_0qEft0hU_3QiTv4k4");
        javaMap.put("web service", "");
        queryMap.put("java", javaMap);

        // C++
        Map cppMap = new HashMap<>();
        cppMap.put("class creation", "https://docs.google.com/document/d/1JkSKOKAZDfRWKh21cUdYkI70vTQ5-muQQTMg52hwWl8/edit");
        cppMap.put("function creation", "https://docs.google.com/document/d/1B34rzrBSYTW7OAtmMgSketj2mr7GSaiECfe3RsGauLU/edit");
        queryMap.put("cpp", cppMap);

        // C
        Map cMap = new HashMap<>();
        cMap.put("function creation", "https://docs.google.com/document/d/1dwbONZzhGurGqE-9jcm5b78sxP9egDAaZtlcHYH--z8/edit");
        queryMap.put("c", cMap);

        // C#
        Map csMap = new HashMap<>();
        csMap.put("class creation", "");
        csMap.put("function creation", "");
        queryMap.put("cs", csMap);

        // SHELL
        Map shMap = new HashMap<>();
        shMap.put("function creation", "https://docs.google.com/document/d/10aSbTtPh_MVfExStSS5MvPLrdSNZY7cZOMFRH-qIGhY/edit");
        queryMap.put("sh", shMap);

        // LUA
        Map luaMap = new HashMap<>();
        luaMap.put("function creation", "");
        queryMap.put("lua", luaMap);

        // PYTHON
        Map pyMap = new HashMap<>();
        pyMap.put("function creation", "");
        queryMap.put("py", pyMap);

        // SQL
        Map sqlMap = new HashMap<>();
        sqlMap.put("function creation", "");
        queryMap.put("sql", sqlMap);
    }

    public String getUrlFromKey(String lang, String subj) {
        // TODO = DATABASE QUERY ?

        String query = queryMap.get(lang).get(subj);

        System.out.println(query);

        if (query == null)
            return "404";
        return query;
    }

    public String display(JSONObject jsonObject) {
        String lang = "";   // Language
        String subj = "";   // Subject

        try {
            System.out.println(jsonObject.getJSONArray("entities"));

            JSONArray arr = jsonObject.getJSONArray("entities");

            if (arr.length() < 2) {
                return "404";
            }

            System.out.println(arr.length());
            for (int i = 0; i < arr.length(); i++) {
                System.out.println(arr.getString(i));
                JSONObject jo = arr.getJSONObject(i);
                if (jo.getString("type").contains("Language::")) {
                    lang = jo.getString("entity");
                }
                else if (jo.getString("type").contains("CodeSubject")) {
                    subj = jo.getString("entity");
                }
            }
            System.out.println("récupération en "+lang+" de "+subj);
        }
        catch (Exception e) {e.printStackTrace();}

        return getUrlFromKey(lang, subj);
    }
}
