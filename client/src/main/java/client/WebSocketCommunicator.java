package client;

import com.google.gson.Gson;
import websocket.messages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static ui.EscapeSequences.ERASE_LINE;

public class WebSocketCommunicator extends Endpoint {

    Session session;

    public WebSocketCommunicator(String domain) throws Exception {
        try {
            URI uri = new URI("ws://" + domain + "/connect");

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, uri);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    messageHandler(message);
                }
            });

        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new Exception();
        }

    }

    protected void sendMessage(String message) {
        this.session.getAsyncRemote().sendText(message);
    }

    private void messageHandler(String message) {
        ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
        ServerMessage.ServerMessageType type = serverMessage.getServerMessageType();

        switch (message) {
            //case  -> notify(message);
            //case ERROR -> error(message);
            //case LOAD_GAME -> load(message);
        }
    }

    private void error(String message) {}

    private void load(String message) {}

    private void notify(String message) {
        System.out.print(ERASE_LINE + '\r');
        System.out.printf("\n%s\n[IN-GAME] >>> ", message);
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {}
}
