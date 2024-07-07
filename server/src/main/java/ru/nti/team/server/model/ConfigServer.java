package ru.nti.team.server.model;

/**
 * Применения шаблона проектирования Singleton.
 */
public class ConfigServer {

    private ConfigServer(){
    }

    private static class ConfigServerHolder {
        private static final ConfigServer INSTANCE = new ConfigServer();
        private static Integer portClient;
    }

    public static ConfigServer getInstance(Integer portServer) {
        ConfigServer configServer = ConfigServerHolder.INSTANCE;
        ConfigServerHolder.portClient = portServer;
        return configServer;
    }

    public static Integer getPortClient() {
        return ConfigServerHolder.portClient;
    }
}
