package org.sportify.SportifyApplication.enums;

public enum StatusEnum {

    ACTIVE(1),
    INACTIVE(0);

    private final int value;

    StatusEnum(int value){
        this.value = value;
    }

    public int getValue(){
        return  value;
    }
}
