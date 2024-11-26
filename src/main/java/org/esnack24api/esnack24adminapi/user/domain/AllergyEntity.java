package org.esnack24api.esnack24adminapi.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "tbl_allergy")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AllergyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoIncrement로 자동 생성되는 pk
    private Long ano;

    private String atitle_ko;

}
