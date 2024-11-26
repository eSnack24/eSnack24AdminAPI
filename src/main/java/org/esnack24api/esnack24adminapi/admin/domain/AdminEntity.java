package org.esnack24api.esnack24adminapi.admin.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Entity
@Table(name = "tbl_admin")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long admno;

    private String admid;

    private String admpw;

    private String admrole;

    private boolean admdelete;

    private Timestamp admregdate;

    private Timestamp admmoddate;
}
