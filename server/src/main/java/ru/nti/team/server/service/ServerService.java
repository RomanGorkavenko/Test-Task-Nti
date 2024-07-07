package ru.nti.team.server.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import ru.nti.team.server.model.ConfigServer;
import ru.nti.team.server.model.ConfigServerRequest;
import ru.nti.team.server.model.ConfigServerResponse;

import java.io.IOException;

@Service
@Log
public class ServerService {

    private static final SocketServerService SERVER = new SocketServerService();

    public void start(Integer frequencyX) {
        String url = "http://www.google.com";
        String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();

        try {
            if (os.contains("win")) {

                rt.exec("rundll32 url.dll,FileProtocolHandler " + url);

            } else if (os.contains("mac")) {

                rt.exec("open " + url);

            } else if (os.contains("nix") || os.contains("nux")) {

                String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror",
                        "netscape", "opera", "links", "lynx"};
                StringBuilder cmd = new StringBuilder();

                for (int i = 0; i < browsers.length; i++)
                    cmd.append(i == 0 ? "" : " || ").append(browsers[i]).append(" \"").append(url).append("\" ");

                rt.exec(new String[]{"sh", "-c", cmd.toString()});

            } else {
                return;
            }

        } catch (Exception e) {
            return;
        }

        SendingCursor.sendingCursor(frequencyX);
    }

    public void stop(boolean flag) {
        SendingCursor.setFlag(flag);
    }


    public ConfigServerResponse config(ConfigServerRequest config) {
        ConfigServer.getInstance(config.getPortClient());
        ConfigServerResponse response = new ConfigServerResponse();
        response.setPortClient(ConfigServer.getPortClient());
        return response;
    }

    public void launching() throws IOException {
        SERVER.start(ConfigServer.getPortClient());
        log.info("The server is listening on the port " + ConfigServer.getPortClient());
    }

    public void disabling() throws IOException {
        SERVER.stop();
        log.info("The server is stopped");
    }
}
