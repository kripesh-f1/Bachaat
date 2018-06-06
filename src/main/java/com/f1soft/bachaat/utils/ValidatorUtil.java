package com.f1soft.bachaat.utils;

import com.f1soft.bachaat.exception.ValidationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ValidatorUtil {


    public static boolean getStringList(String sort) {
        String[] property = {"","firstName", "middleName", "lastName", "emailAddress", "mobileNumber", "password"};
        List<String> strings = Arrays.asList(property);
        if (strings.contains(sort)) {
            return true;
        }
        return false;
    }

    public  Pageable getPageable(Pageable pageable,String sort,String order){
        if(!getStringList(sort)){
            throw new ValidationException(String.format("Unknown field : %s",sort));
        }
        else if(!sort.isEmpty()&&order.isEmpty()){
            pageable=PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), Sort.by(sort));
        }
        else if(!sort.isEmpty()&&order.equalsIgnoreCase("DESC")){
            pageable= PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), Sort.by(sort).descending());
        }
        else if((sort.isEmpty())&&(order.isEmpty())||!order.equalsIgnoreCase("DESC")){
            pageable=PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), Sort.Direction.ASC,"id");
        }else{
            pageable= PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), Sort.Direction.DESC,"id");
        }
            return  pageable;
    }
}
