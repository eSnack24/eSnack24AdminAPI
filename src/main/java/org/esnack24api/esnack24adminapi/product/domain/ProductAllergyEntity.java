package org.esnack24api.esnack24adminapi.product.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.esnack24api.esnack24adminapi.allergy.domain.Allergy2Entity;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"product", "allergy"})
@Table(name = "tbl_product_allergy")
public class ProductAllergyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pano;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ano")
    private Allergy2Entity allergy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pno")
    private ProductEntity product;
}