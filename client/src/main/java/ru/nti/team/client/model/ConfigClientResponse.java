package ru.nti.team.client.model;

import lombok.Data;

@Data
public class ConfigClientResponse {

    private String ipServer;
    private Integer portServer;
    private Integer frequencyX;

}
