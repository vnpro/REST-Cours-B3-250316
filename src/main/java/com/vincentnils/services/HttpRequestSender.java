package com.vincentnils.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestSender {
    public HttpRequestSender() {
    }

    // HTTP GET request
    public String sendGet(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        // add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        // int responseCode = con.getResponseCode();
        // System.out.println("\nSending 'GET' request to URL : " + url);
        // System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // print result
        // System.out.println(response.toString());

        return response.toString();
    }
}
