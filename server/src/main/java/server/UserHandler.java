package server;

import exception.ResponseException;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.api.Response;

public class UserHandler {

    public Object register(Request req, Response res) throws ResponseException {}

    public Object login(Request req, Response res) throws ResponseException {}

    public Object logout(Request req, Response res) throws ResponseException {}
}
