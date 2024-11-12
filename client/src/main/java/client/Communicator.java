package client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import exception.ResponseException;
import model.UserData;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Communicator {
    private final String serverURL;
    private final ServerFacade serverFacade;

    public Communicator(ServerFacade facade, String serverURL) {
        this.serverURL = serverURL;
        this.serverFacade = facade;
    }

    public void register(String action, String path, UserData user) throws ResponseException {
        var result = this.makeRequest(action, path, user, Map.class);
        serverFacade.setAuth((String) result.get("authToken"));
    }

    public void login(String action, String path, UserData user) throws ResponseException, URISyntaxException, IOException {
        var result = this.makeRequest(action, path, user, Map.class);
        serverFacade.setAuth((String) result.get("authToken"));
    }

    public void logout(String action, String path) throws ResponseException {
        this.makeRequest(action, path, null, Map.class);
        serverFacade.setAuth(null);
    }

    public void creategame(String action, String path, String name) throws ResponseException {
        this.makeRequest(action, path, name, String.class);
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws ResponseException {
        Map result;
        try {
            URL url = (new URI(serverURL + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            if (serverFacade.getAuth() != null) {
                http.addRequestProperty("Authorization", serverFacade.getAuth());
            }

            writeBody(request, http);

            http.connect();
            throwIfNotSuccessful(http);
            try (InputStream body = http.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(body);
                result = new Gson().fromJson(inputStreamReader, Map.class);
            }
            return (T) result;
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }


    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new ResponseException(status, "failure: " + status);
        }
    }

//    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
//        T response = null;
//        if (http.getContentLength() < 0) {
//            try (InputStream respBody = http.getInputStream()) {
//                InputStreamReader reader = new InputStreamReader(respBody);
//                if (responseClass != null) {
//                    response = new Gson().fromJson(reader, responseClass);
//                }
//            }
//        }
//        return response;
//    }


    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}
