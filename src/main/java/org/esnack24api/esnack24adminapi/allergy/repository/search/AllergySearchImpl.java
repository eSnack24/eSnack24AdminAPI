package org.esnack24api.esnack24adminapi.allergy.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.esnack24api.esnack24adminapi.allergy.domain.AllergyEntity;
import org.esnack24api.esnack24adminapi.allergy.domain.QAllergyEntity;
import org.esnack24api.esnack24adminapi.allergy.dto.AllergyCrudDTO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AllergySearchImpl extends QuerydslRepositorySupport implements AllergySearch {

    private final JPAQueryFactory queryFactory;

    public AllergySearchImpl(EntityManager entityManager) {
        super(AllergyEntity.class);
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<AllergyCrudDTO> listAllergy() {
        QAllergyEntity allergy = QAllergyEntity.allergyEntity;

        return queryFactory
                .select(Projections.fields(AllergyCrudDTO.class,
                        allergy.ano,
                        allergy.atitle_ko,
                        allergy.atitle_en,
                        allergy.atitle_ja,
                        allergy.atitle_zh))
                .from(allergy)
                .fetch();
    }

    @Override
    public AllergyCrudDTO getDetailAllergy(Long ano) {
        QAllergyEntity allergy = QAllergyEntity.allergyEntity;

        return queryFactory
                .select(Projections.fields(AllergyCrudDTO.class,
                        allergy.ano,
                        allergy.atitle_ko,
                        allergy.atitle_en,
                        allergy.atitle_ja,
                        allergy.atitle_zh))
                .from(allergy)
                .where(allergy.ano.eq(ano))
                .fetchOne();
    }
}