package com.david;

import com.sun.net.httpserver.HttpExchange;


import java.io.IOException;
import java.io.OutputStream;

import static javax.swing.text.html.FormSubmitEvent.MethodType.POST;

public class Route {

    public void genSite(HttpExchange reqData, String response) throws IOException{
        reqData.sendResponseHeaders(200, response.length());
        OutputStream os = reqData.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }


    @WebRoute("/test")
    public void onTest(HttpExchange reqData) throws IOException {
        String response = "You are on the test page.";
        genSite(reqData, response);

    }

    @WebRoute("/users")
    public void onUser(HttpExchange reqData) throws IOException {
        String response = "You are on the user page.";
        genSite(reqData, response);
    }

    @WebRoute("/user/<userName>")
    public void onUserId(HttpExchange reqData, String userName) throws IOException {
        String response = "You are on " + userName + " user page.";
        genSite(reqData, response);
    }

    @WebRoute(method=POST, value = "/users")
    public void onUserPost(HttpExchange reqData) throws IOException {
        String response = "Post method on users";
        genSite(reqData, response);
    }

    public void onError(HttpExchange reqData) throws IOException {
        String response = "Something went wrong!";

        reqData.sendResponseHeaders(404, response.length());
        OutputStream os = reqData.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
