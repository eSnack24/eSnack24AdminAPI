package org.esnack24api.esnack24adminapi.fcm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FCMRequestDTO {

    private List<String> token;
    private String title;
    private String body;
}