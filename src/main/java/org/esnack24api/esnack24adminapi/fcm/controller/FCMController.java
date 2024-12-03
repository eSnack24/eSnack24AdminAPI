package org.esnack24api.esnack24adminapi.fcm.controller;
import lombok.RequiredArgsConstructor;
import org.esnack24api.esnack24adminapi.admin.dto.FCMTokenRequestDTO;
import org.esnack24api.esnack24adminapi.admin.service.AdminService;
import org.esnack24api.esnack24adminapi.fcm.dto.FCMRequestDTO;
import org.esnack24api.esnack24adminapi.fcm.service.FCMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/fcm")
@RequiredArgsConstructor
public class FCMController {
    private final FCMService fcmService;
    @Autowired
    private AdminService adminService;
    // FCM 메시지 전송 엔드포인트
    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody FCMRequestDTO fcmRequestDTO) {
        try {
            // FCMService에서 여러 토큰을 처리하도록 이미 수정된 상태에서
            fcmService.sendMessage(fcmRequestDTO);
            return ResponseEntity.ok("Notification sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to send notification: " + e.getMessage());
        }
    }
    @PostMapping("/savetoken")
    public ResponseEntity<String> saveToken(@RequestBody FCMTokenRequestDTO tokenRequest) {
        try {
            // 기존의 토큰을 제거하고 새로운 토큰으로 갱신
            adminService.updateToken(tokenRequest);
            return ResponseEntity.ok("Token saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to save token: " + e.getMessage());
        }
    }
}












