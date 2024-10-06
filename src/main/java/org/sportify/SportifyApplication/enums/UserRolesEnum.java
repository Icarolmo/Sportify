package org.sportify.SportifyApplication.enums;

public enum UserRolesEnum {
    ADMIN("admin"),
    ENTITY("entity"),
    COMMON("common");

    private String role;

    UserRolesEnum(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
