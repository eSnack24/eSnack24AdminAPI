package org.esnack24api.esnack24adminapi.fcm.service;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.fcm.dto.FCMRequestDTO;
import org.esnack24api.esnack24adminapi.fcm.exceptions.FCMMessageException;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@RequiredArgsConstructor
@Log4j2
public class FCMService {
    private final FirebaseMessaging firebaseMessaging;
    public void sendMessage(FCMRequestDTO fcmRequestDTO) {
        if (fcmRequestDTO == null) {
            throw new FCMMessageException("fcmRequestDTO is null");
        }
        if (fcmRequestDTO.getToken() == null || fcmRequestDTO.getToken().isEmpty()) {
            throw new FCMMessageException("fcmRequestDTO.getToken is null or empty");
        }
        if (fcmRequestDTO.getTitle() == null || fcmRequestDTO.getTitle().isEmpty()) {
            throw new FCMMessageException("title is null or empty");
        }
        // Notification 객체 생성
        Notification notification = Notification.builder()
                .setBody(fcmRequestDTO.getBody())
                .setTitle(fcmRequestDTO.getTitle())
                .build();
        // 각 토큰에 대해 메시지 전송
        List<String> tokens = fcmRequestDTO.getToken();
        for (String token : tokens) {
            Message message = Message.builder()
                    .setToken(token)  // 각 토큰을 사용하여 메시지 생성
                    .setNotification(notification)
                    .build();
            try {
                // FirebaseMessaging을 통해 메시지 전송
                firebaseMessaging.send(message);
                log.info("Notification sent to token: {}", token);
            } catch (FirebaseMessagingException e) {
                // 각 토큰에 대해 전송 실패 시 예외 처리
                log.error("Failed to send message to token: {}", token, e);
                throw new FCMMessageException("Failed to send message to token: " + token);
            }
        }
    }
}














