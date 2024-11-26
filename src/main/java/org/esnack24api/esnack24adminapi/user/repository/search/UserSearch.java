package org.esnack24api.esnack24adminapi.user.repository.search;

import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.user.dto.UserDTO;
import org.esnack24api.esnack24adminapi.user.dto.UserDetailDTO;

import java.util.List;

public interface UserSearch {

    List<UserDTO> getUserList(PageRequestDTO pageRequestDTO);

    UserDetailDTO getUserDetail(Long uno);


}
