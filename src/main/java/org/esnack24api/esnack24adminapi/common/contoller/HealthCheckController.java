package org.esnack24api.esnack24adminapi.common.contoller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/api/v1/")
@Log4j2
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class HealthCheckController {

    @GetMapping("")
    public String healthCheckController() {

        return "Health Check Complete";
    }
}
