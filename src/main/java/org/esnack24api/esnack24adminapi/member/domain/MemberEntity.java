package org.esnack24api.esnack24adminapi.member.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberEntity {

    @Id
    private String email;

    private String pw;

    private MemberRole role;
}
