package com.hcs.fastsales.dto.user;

import java.io.Serializable;

/**
 * Created by fernando.ramirez on 22/06/2015.
 */
public class DtoUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private int userId;
    private String userName;
    private String userPass;
    private String firstName;
    private String lastName;
    private String address;
    private String emailAddress;
    private DtoUserType dtoUserType;
    private DtoUserStatus dtoUserStatus;
    final public static int PIN_LENGTH = 4;

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the userPass
     */
    public String getUserPass() {
        return userPass;
    }

    /**
     * @param userPass the userPass to set
     */
    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return this.getFirstName().concat(" ").concat(this.getLastName());
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * @return the dtoUserType
     */
    public DtoUserType getDtoUserType() {
        return dtoUserType;
    }

    /**
     * @param dtoUserType the dtoUserType to set
     */
    public void setDtoUserType(DtoUserType dtoUserType) {
        this.dtoUserType = dtoUserType;
    }

    /**
     * @return the dtoUserStatus
     */
    public DtoUserStatus getDtoUserStatus() {
        return dtoUserStatus;
    }

    /**
     * @param dtoUserStatus the dtoUserStatus to set
     */
    public void setDtoUserStatus(DtoUserStatus dtoUserStatus) {
        this.dtoUserStatus = dtoUserStatus;
    }

    @Override
    public String toString() {
        return "DtoUser{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPass='" + userPass + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", dtoUserType=" + dtoUserType +
                ", dtoUserStatus=" + dtoUserStatus +
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
        result = prime * result + userId;
        result = prime * result
                + ((userName == null) ? 0 : userName.hashCode());
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
        DtoUser other = (DtoUser) obj;
        if (userId != other.userId)
            return false;
        if (userName == null) {
            if (other.userName != null)
                return false;
        } else if (!userName.equalsIgnoreCase(other.userName))
            return false;
        return true;
    }
}