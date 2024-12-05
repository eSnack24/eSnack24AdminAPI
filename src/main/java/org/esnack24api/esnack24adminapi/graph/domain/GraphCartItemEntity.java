package org.esnack24api.esnack24adminapi.graph.domain;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tbl_cartitem")
public class GraphCartItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cino;
    private Long cno;
    private Long pno;
    private Timestamp ciregdate;
    private Integer ciqty;
}