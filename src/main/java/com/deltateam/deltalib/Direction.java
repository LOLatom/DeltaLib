package com.deltateam.deltalib;

public enum Direction {
    NORTH("NORTH"),
    SOUTH("SOUTH"),
    EAST("EAST"),
    WEST("WEST"),
    UP("UP"),
    DOWN("DOWN"),
    DOWNEAST("DOWNEAST"),
    DOWNNORTH("DOWNNORTH"),
    DOWNSOUTH("DOWNSOUTH"),
    DOWNWEST("DOWNWEST"),
    UPNORTH("UPNORTH"),
    UPEAST("UPEAST"),
    UPSOUTH("UPSOUTH"),
    UPWEST("UPWEST"),
    NORTHEAST("NORTHEAST"),
    NORTHWEST("NORTHWEST"),
    SOUTHEAST("SOUTHEAST"),
    SOUTHWEST("SOUTHWEST");

    String name="";
    Direction(String name) {
        this.name=name;
    }
}
