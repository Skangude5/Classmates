package com.skangude5.classmates.model;

public class Badge {
    private String badgeType;
    private Integer badgeImageId;
    private String badgeLevel;
    private String badgeDescription;

    public Badge(String badgeType, Integer badgeImageId, String badgeLevel, String badgeDescription) {
        this.badgeType = badgeType;
        this.badgeImageId = badgeImageId;
        this.badgeLevel = badgeLevel;
        this.badgeDescription = badgeDescription;
    }

    public void setBadge(String badgeType, Integer badgeImageId, String badgeLevel, String badgeDescription) {
        this.badgeType = badgeType;
        this.badgeImageId = badgeImageId;
        this.badgeLevel = badgeLevel;
        this.badgeDescription = badgeDescription;
    }
    public String getBadgeType() {
        return badgeType;
    }

    public void setBadgeType(String badgeType) {
        this.badgeType = badgeType;
    }

    public Integer getBadgeImageId() {
        return badgeImageId;
    }

    public void setBadgeImageId(Integer badgeImageId) {
        this.badgeImageId = badgeImageId;
    }

    public String getBadgeLevel() {
        return badgeLevel;
    }

    public void setBadgeLevel(String badgeLevel) {
        this.badgeLevel = badgeLevel;
    }

    public String getBadgeDescription() {
        return badgeDescription;
    }

    public void setBadgeDescription(String badgeDescription) {
        this.badgeDescription = badgeDescription;
    }
}
