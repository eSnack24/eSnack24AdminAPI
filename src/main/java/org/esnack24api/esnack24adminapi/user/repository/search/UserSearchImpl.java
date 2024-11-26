package org.esnack24api.esnack24adminapi.user.repository.search;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.user.domain.QAllergyEntity;
import org.esnack24api.esnack24adminapi.user.domain.QUserAllergyEntity;
import org.esnack24api.esnack24adminapi.user.domain.QUserEntity;
import org.esnack24api.esnack24adminapi.user.domain.UserEntity;
import org.esnack24api.esnack24adminapi.user.dto.UserDTO;
import org.esnack24api.esnack24adminapi.user.dto.UserDetailDTO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class UserSearchImpl extends QuerydslRepositorySupport implements UserSearch {

    public UserSearchImpl() {
        super(UserEntity.class);
    }

    @Override
    public List<UserDTO> getUserList(PageRequestDTO pageRequestDTO) {

        QUserEntity user = QUserEntity.userEntity;
        QUserAllergyEntity userAllergy = QUserAllergyEntity.userAllergyEntity;
        QAllergyEntity allergy = QAllergyEntity.allergyEntity;

        JPQLQuery<Tuple> query = from(user)
                .leftJoin(userAllergy).on(user.uno.eq(userAllergy.user.uno))
                .leftJoin(allergy).on(userAllergy.allergy.ano.eq(allergy.ano))
                .select(user.uno, user.username, user.uemail, user.ucallnumber,
                        user.ubirth, user.udelete, allergy.atitle_ko);

        List<Tuple> result = query.fetch();

        Map<Long, UserDTO> userMap = new HashMap<>();

        for (Tuple row : result) {
            Long uno = row.get(user.uno);
            UserDTO userDTO = userMap.computeIfAbsent(uno, id -> {
                UserDTO dto = new UserDTO();
                dto.setUno(id);
                dto.setUsername(row.get(user.username));
                dto.setEmail(row.get(user.uemail));
                dto.setCallNumber(row.get(user.ucallnumber));
                dto.setBirth(row.get(user.ubirth));
                dto.setAllergyList(new ArrayList<>());
                return dto;
            });


            String allergyTitle = row.get(allergy.atitle_ko);
            if (allergyTitle != null) {
                userDTO.getAllergyList().add(allergyTitle);
            }
        }


        return new ArrayList<>(userMap.values());
    }




    @Override
    public UserDetailDTO getUserDetail(Long uno) {

        QUserEntity user = QUserEntity.userEntity;
        QUserAllergyEntity userAllergy = QUserAllergyEntity.userAllergyEntity;
        QAllergyEntity allergy = QAllergyEntity.allergyEntity;

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


