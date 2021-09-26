package com.khoders.pos.dto;

public class UnitMeasurement {
    private String id;
    private String units;

    public UnitMeasurement(String id, String units) {
        this.id = id;
        this.units = units;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
