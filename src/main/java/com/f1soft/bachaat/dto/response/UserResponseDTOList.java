package com.f1soft.bachaat.dto.response;

import java.util.List;

public class UserResponseDTOList {

    private long records;
    private List<UserResponseDTO> userResponseDTOList;

    public UserResponseDTOList(long records, List<UserResponseDTO> userResponseDTOList) {
        this.records = records;
        this.userResponseDTOList = userResponseDTOList;
    }

    public UserResponseDTOList() {
    }

    public long getRecords() {
        return records;
    }

    public void setRecords(long records) {
        this.records = records;
    }

    public List<UserResponseDTO> getUserResponseDTOList() {
        return userResponseDTOList;
    }

    public void setUserResponseDTOList(List<UserResponseDTO> userResponseDTOList) {
        this.userResponseDTOList = userResponseDTOList;
    }
}
