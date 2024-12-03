package org.esnack24api.esnack24adminapi.user.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.user.dto.UserDTO;
import org.esnack24api.esnack24adminapi.user.dto.UserDetailDTO;
import org.esnack24api.esnack24adminapi.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("admin/api/v1/user")
@Log4j2
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("list")
    public ResponseEntity<PageResponseDTO<UserDTO>> userLists(@Validated PageRequestDTO pageRequestDTO){

        return ResponseEntity.ok(userService.userList(pageRequestDTO));

    }

    @GetMapping("detail/{uno}")
    public ResponseEntity<UserDetailDTO> userDetails(@PathVariable Long uno){

        return ResponseEntity.ok(userService.userDetail(uno));

    }



}
