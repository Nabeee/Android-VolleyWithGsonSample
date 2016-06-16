package com.android.volleywithgson.model;


public class JsonObject {
    public String name;
    public String email;
    public Phone phone;

    public class Phone {
        public String home;
        public String mobile;
    }
}
