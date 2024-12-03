package org.esnack24api.esnack24adminapi.user.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.user.dto.UserDTO;
import org.esnack24api.esnack24adminapi.user.dto.UserDetailDTO;
import org.esnack24api.esnack24adminapi.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public PageResponseDTO<UserDTO> userList(PageRequestDTO pageRequestDTO) {

        return userRepository.getUserList(pageRequestDTO);

    }

    public UserDetailDTO userDetail(Long uno) {

        return userRepository.getUserDetail(uno);
    }

}
