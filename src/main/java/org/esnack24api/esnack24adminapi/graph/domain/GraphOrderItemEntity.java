package org.esnack24api.esnack24adminapi.graph.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_order_item")
public class GraphOrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oino;
    private Long ono;
    private Long pno;
    private Integer oiqty;

}