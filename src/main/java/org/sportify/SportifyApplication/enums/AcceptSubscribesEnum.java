package org.sportify.SportifyApplication.enums;

public enum AcceptSubscribesEnum {

    YES(1),
    NO(0);

    private final int value;

    AcceptSubscribesEnum(int value){
        this.value = value;
    }

    public int getValue(){
        return  value;
    }
}
