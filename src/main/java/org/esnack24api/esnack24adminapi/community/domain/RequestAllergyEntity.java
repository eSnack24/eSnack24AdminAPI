package org.esnack24api.esnack24adminapi.community.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tbl_community_allergy")
public class RequestAllergyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cano;

    private String caallergy;

    private String catitle;

    private String caanswer;

    private boolean cadelete;

    private Timestamp caregdate;

    @UpdateTimestamp
    private Timestamp camoddate;

}
