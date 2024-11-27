package org.esnack24api.esnack24adminapi.allergy.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tbl_allergy")
public class Allergy2Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ano;

    private String atitle_ko;

    private String atitle_en;

    private String atitle_ja;

    private String atitle_zh;
}