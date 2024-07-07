package ru.nti.team.client.model;

/**
 * Применения шаблона проектирования Singleton.
 */
public class ConfigClient {

    private ConfigClient(){
    }

    private static class ConfigServerHolder {
        private static final ConfigClient INSTANCE = new ConfigClient();
        private static String ipServer;
        private static Integer portServer;
        private static Integer frequencyX;
    }

    public static ConfigClient getInstance(String ipServer, Integer portServer, Integer frequencyX) {
        ConfigClient configClient = ConfigServerHolder.INSTANCE;
        ConfigServerHolder.ipServer = ipServer;
        ConfigServerHolder.portServer = portServer;
        ConfigServerHolder.frequencyX = frequencyX;
        return configClient;
    }

    public static String getIpServer() {
        return ConfigServerHolder.ipServer;
    }

    public static Integer getPortServer() {
        return ConfigServerHolder.portServer;
    }

    public static Integer getFrequencyX() {
        return ConfigServerHolder.frequencyX;
    }
}
