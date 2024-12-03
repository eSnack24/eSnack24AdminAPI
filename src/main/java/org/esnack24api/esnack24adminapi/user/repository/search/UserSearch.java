package org.esnack24api.esnack24adminapi.user.repository.search;

import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.user.dto.UserDTO;
import org.esnack24api.esnack24adminapi.user.dto.UserDetailDTO;



public interface UserSearch {

    PageResponseDTO<UserDTO> getUserList(PageRequestDTO pageRequestDTO);

    UserDetailDTO getUserDetail(Long uno);


}
