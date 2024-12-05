package org.esnack24api.esnack24adminapi.graph.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_cart")
public class GraphCartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cno;
    private Long uno;

}