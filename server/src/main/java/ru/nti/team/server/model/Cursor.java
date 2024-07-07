package ru.nti.team.server.model;

import lombok.Data;

import java.time.LocalTime;

@Data
public class Cursor {

    private LocalTime time;
    private double x;
    private double y;
}
