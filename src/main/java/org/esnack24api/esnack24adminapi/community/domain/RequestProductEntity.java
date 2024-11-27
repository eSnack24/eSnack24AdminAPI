package org.esnack24api.esnack24adminapi.community.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tbl_community_product")
public class RequestProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cpno;

    private String cpproduct;

    private String cptitle;

    private String cpanswer;

    private Timestamp cpregdate;

    private Timestamp cpmoddate;

    private boolean cpdelete;

}
