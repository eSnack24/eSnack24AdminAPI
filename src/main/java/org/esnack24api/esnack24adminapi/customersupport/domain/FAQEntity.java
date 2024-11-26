package org.esnack24api.esnack24adminapi.customersupport.domain;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Entity
@Table(name = "tbl_faq")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"admno"})
public class FAQEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoIncrement로 자동 생성되는 pk
    private Long fno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="admno")
    private adminEntity admno;

    private String ftitle;

    private String fcategory;

    private boolean fdelete;

    private String fcontent;
}
