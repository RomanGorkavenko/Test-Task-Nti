package ru.nti.team.client.model;

import lombok.Data;

@Data
public class ConfigClientRequest {

    private String ipServer;
    private Integer portServer;
    private Integer frequencyX;

}
