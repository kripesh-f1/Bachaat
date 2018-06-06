package com.f1soft.bachaat.dto.response;

import java.util.List;

public class UserResponseDTOList {

    private long count;
    private List<UserResponseDTO> userResponseDTOList;

    public UserResponseDTOList(long count, List<UserResponseDTO> userResponseDTOList) {
        this.count = count;
        this.userResponseDTOList = userResponseDTOList;
    }

    public UserResponseDTOList() {
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<UserResponseDTO> getUserResponseDTOList() {
        return userResponseDTOList;
    }

    public void setUserResponseDTOList(List<UserResponseDTO> userResponseDTOList) {
        this.userResponseDTOList = userResponseDTOList;
    }
}
