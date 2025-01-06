package org.example.ubersocketserver.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class chatRequest {

    private String name;
    private String message;
}
