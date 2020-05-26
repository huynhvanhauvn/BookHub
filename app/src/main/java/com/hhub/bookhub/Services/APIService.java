package com.hhub.bookhub.Services;

public class APIService {
    private static String base_url = "http://huynhvanhaua.000webhostapp.com/server/bookhub/";

    public static Service getService(){
        return APIRetrofitClient.getClient(base_url).create(Service.class);
    }
}
