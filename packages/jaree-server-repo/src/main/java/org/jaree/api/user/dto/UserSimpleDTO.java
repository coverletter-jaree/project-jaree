package org.jaree.api.user.dto;

import java.time.LocalDateTime;

import org.jaree.api.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSimpleDTO {
    private String id;
    private String name;
    private LocalDateTime createdAt;

    public static UserSimpleDTO of(User user) {
        if(user == null) {
            return null;
        }

        return UserSimpleDTO.builder()
            .id(user.getId())
            .name(user.getName())
            .createdAt(user.getCreatedAt())
            .build();
    }
}
