package com.hcs.fastsales.dto.user;

import java.io.Serializable;

/**
 * Created by fernando.ramirez on 22/06/2015.
 */
public class DtoUserStatus implements Serializable {

    public enum Status {
        ACTIVE ("ACTIVE", "Activo", "Usuario activado para operaciones"),
        RECEIVED ("RECEIVED", "Recibido", "Usuario recibido en el systema para ser aprobado"),
        INACTIVE ("INACTIVE", "Inactivo", "Usuario sin actividad"),
        CANCELLED ("CANCELLED", "Cancelado", "Usuario cancelado o no aceptado");

        private final String status;
        private final String statusName;
        private final String description;

        Status(String status, String statusName, String description) {
            this.status = status;
            this.statusName = statusName;
            this.description = description;
        }

        public String status() { return status; }
        public String statusName() { return statusName; }
        public String description() { return description; }
    }

    private static final long serialVersionUID = 1L;
    private String userSatus;
    private String statusName;
    private String description;

    public DtoUserStatus(String userSatus, String statusName, String description) {
        this.userSatus = userSatus;
        this.setStatusName(statusName);
        this.description = description;
    }

    /**
     * @return the userSatus
     */
    public String getUserSatus() {
        return userSatus;
    }

    /**
     * @param userSatus the userSatus to set
     */
    public void setUserSatus(String userSatus) {
        this.userSatus = userSatus;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
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

    /**
     * Method hashCode @Override
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((userSatus == null) ? 0 : userSatus.hashCode());
        return result;
    }

    /**
     * Method equals @Override
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DtoUserStatus other = (DtoUserStatus) obj;
        if (userSatus == null) {
            if (other.userSatus != null)
                return false;
        } else if (!userSatus.equalsIgnoreCase(other.userSatus))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "DtoUserStatus{" +
                "userSatus='" + userSatus + '\'' +
                ", statusName='" + statusName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
