package com.trier.KON_BackEnd.dto.request;

import lombok.Data;

@Data
public class OAuth2UserInfoDTO {

    private String email;
    private String name;
    private String picture;
    private String sub;

}