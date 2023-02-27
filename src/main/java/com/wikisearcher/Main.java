package com.wikisearcher;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Main {
    public static RequestManager requestManager = new RequestManager();

    public static void main(String[] args) throws IOException {
        requestManager.userRequest(args[0]);

    }


}





