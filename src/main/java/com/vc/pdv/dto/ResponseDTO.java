package com.vc.pdv.dto;

import java.util.List;
import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ResponseDTO<T> {
    @Getter
    private List<String> messages;

    public ResponseDTO(String message) {
        this.messages = Arrays.asList(message);
    }

}