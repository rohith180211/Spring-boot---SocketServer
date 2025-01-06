package org.example.ubersocketserver.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class chatResponse {
    private String message;
    private String name;
    private String time;

}
