package org.esnack24api.esnack24adminapi.user.repository.search;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.user.domain.QAllergyUserEntity;
import org.esnack24api.esnack24adminapi.user.domain.QUserAllergyEntity;
import org.esnack24api.esnack24adminapi.user.domain.QUserEntity;
import org.esnack24api.esnack24adminapi.user.domain.UserEntity;
import org.esnack24api.esnack24adminapi.user.dto.UserDTO;
import org.esnack24api.esnack24adminapi.user.dto.UserDetailDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
public class UserSearchImpl extends QuerydslRepositorySupport implements UserSearch {

    public UserSearchImpl() {
        super(UserEntity.class);
    }

    @Override
    public PageResponseDTO<UserDTO> getUserList(PageRequestDTO pageRequestDTO) {

        QUserEntity user = QUserEntity.userEntity;
        QUserAllergyEntity userAllergy = QUserAllergyEntity.userAllergyEntity;
        QAllergyUserEntity allergy = QAllergyUserEntity.allergyUserEntity;

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize());

        JPQLQuery<UserDTO> baseQuery = from(user)
                .select(Projections.bean(UserDTO.class,
                        user.uno.as("uno"),
                        user.username.as("username"),
                        user.uemail.as("email"),
                        user.ucallnumber.as("callNumber"),
                        user.ubirth.as("birth"),
                        user.udelete.as("udelete")))
                .distinct();

        this.getQuerydsl().applyPagination(pageable, baseQuery);

        List<UserDTO> userList = baseQuery.fetch();

        List<Long> userIds = userList.stream().map(UserDTO::getUno).collect(Collectors.toList());

        if (!userIds.isEmpty()) {
            List<Tuple> allergyData = from(userAllergy)
                    .leftJoin(allergy).on(userAllergy.allergy.ano.eq(allergy.ano))
                    .where(userAllergy.user.uno.in(userIds))
                    .select(userAllergy.user.uno, allergy.atitle_ko)
                    .fetch();

            Map<Long, List<String>> allergyMap = allergyData.stream()
                    .collect(Collectors.groupingBy(
                            tuple -> tuple.get(userAllergy.user.uno),
                            Collectors.mapping(tuple -> tuple.get(allergy.atitle_ko), Collectors.toList())
                    ));


            userList.forEach(userDTO -> {
                List<String> allergies = allergyMap.get(userDTO.getUno());
                userDTO.setAllergyList(allergies != null ? allergies : new ArrayList<>());
            });
        }

        long totalCount = baseQuery.fetchCount();


        return PageResponseDTO.<UserDTO>withAll()
                .dtoList(userList)
                .totalCount(totalCount)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }




    @Override
    public UserDetailDTO getUserDetail(Long uno) {

        QUserEntity user = QUserEntity.userEntity;
        QUserAllergyEntity userAllergy = QUserAllergyEntity.userAllergyEntity;
        QAllergyUserEntity allergy = QAllergyUserEntity.allergyUserEntity;

        JPQLQuery<Tuple> query = from(user)
                .leftJoin(userAllergy).on(user.uno.eq(userAllergy.user.uno))
                .leftJoin(allergy).on(userAllergy.allergy.ano.eq(allergy.ano))
                .where(user.uno.eq(uno))
                .select(
                        user.uno,
                        user.username,
                        user.uemail,
                        user.ucallnumber,
                        user.ubirth,
                        user.ugender,
                        user.upw,
                        user.udelete,
                        allergy.atitle_ko
                );

        List<Tuple> result = query.fetch();

        UserDetailDTO userDetail = null;
        List<String> allergyList = new ArrayList<>();

        for (Tuple row : result) {
            if (userDetail == null) {
                userDetail = new UserDetailDTO();
                userDetail.setUno(row.get(user.uno));
                userDetail.setUsername(row.get(user.username));
                userDetail.setEmail(row.get(user.uemail));
                userDetail.setCallNumber(row.get(user.ucallnumber));
                userDetail.setBirth(row.get(user.ubirth));
                userDetail.setGender(row.get(user.ugender));
                userDetail.setUpw(row.get(user.upw));
            }

            String allergyTitle = row.get(allergy.atitle_ko);
            if (allergyTitle != null) {
                allergyList.add(allergyTitle);
            }
        }

        userDetail.setAllergyList(allergyList);

        return userDetail;
    }


}


