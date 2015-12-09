package com.hcs.fastsales.dto.user;

import java.io.Serializable;

/**
 * Created by fernando.ramirez on 22/06/2015.
 */
public class DtoUserType implements Serializable {

    public enum Type {
        SUPER ("SUPER", "SUPERVISOR", "Super usuario, tiene todos los accesos"),
        ADMIN ("ADMIN", "Administrador", "Usuario adminitrador, tiene accesos administrativos y de control"),
        SALES ("SALES", "Agente De Ventas", "Usuario con acceso a realizar ventas"),
        BASIC ("BASIC", "Usuario Basico", "Usuario con minimos permisos, o rol no definido");

        private final String userType;
        private final String typeName;
        private final String description;

        Type(String userType, String typeName, String description) {
            this.userType = userType;
            this.typeName = typeName;
            this.description = description;
        }

        public String userType() { return userType; }
        public String typeName() { return typeName; }
        public String description() { return description; }
    }

    private static final long serialVersionUID = 1L;
    private String userType;
    private String typeName;
    private String description;

    public DtoUserType(String userType, String typeName, String description) {
        this.userType = userType;
        this.typeName = typeName;
        this.description = description;
    }

    /**
     * @return the userType
     */
    public String getUserType() {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "DtoUserType{" +
                "userType='" + userType + '\'' +
                ", typeName='" + typeName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    /**
     * Method hashCode @Override
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((userType == null) ? 0 : userType.hashCode());
        return result;
    }

    /**
     * Method equals @Override
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()){
            return false;
        }

        DtoUserType other = (DtoUserType) obj;
        if (userType == null) {
            if (other.userType != null)
                return false;
        } else if (!userType.equalsIgnoreCase(other.userType))
            return false;
        return true;
    }
}
