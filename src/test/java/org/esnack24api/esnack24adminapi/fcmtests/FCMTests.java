package org.esnack24api.esnack24adminapi.fcmtests;

import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.fcm.dto.FCMRequestDTO;
import org.esnack24api.esnack24adminapi.fcm.service.FCMService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class FCMTests {

    @Autowired
    FCMService fcmService;

    @Test
    public void sendTest() {


//        String token = "eNApZOgX3TivQ4-Sk1xhA1:APA91bF5LjbjTimlIWaqWyZuNv_KA2KSxX0DKnDafrkd9-6CxEUQKGGjgkJV9riqrx-HonnxAEcUDAZtQ8_8Qr2gASADKKZH0fPnJQBcg0QhyOrNDPBbUBU";
//        String token = "eHhNtEiv5LImnRxs7NSbkE:APA91bFU6c7aIKRgdoDmLuOBXC-ZpU7j1hHpK8r7QoYQuqPc5lmYmxkg0hd4_LeZUnMviUSyC11BPMrB4annAx5B6_e_0N962V-7tpm_w30KE5pCbvSdV-k";
          String token = "cPeNRP3Puh9ywYp0u6cMtO:APA91bEYplDdgFDcOfOfFmpVcISh3eO-3w0aYLZI1dLyN57t8AX_qWDZYojw4xCSCYel0GRgiD46cUUGT_AIjqCjBvS2hQCTPbf4HmZAoOthkhgkw66Kwr8";


        String title = "메시지 전송 테스트";

        String body = "HTML로 전송된 메시지 이것은 FCM을 통해 전송된 HTML 메시지입니다.";

        FCMRequestDTO dto = FCMRequestDTO.builder()
                .token(token)
                .title(title)
                .body(body)
                .build();

        fcmService.sendMessage(dto);
    }
}







