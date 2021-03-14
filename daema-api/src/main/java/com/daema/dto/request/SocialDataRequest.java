package com.daema.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SocialDataRequest {
    private String id;
    private String name;
    private String email;
    private String type;
}
