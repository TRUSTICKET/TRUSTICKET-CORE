package com.trusticket.trusticketcore.config.security.exception;


import com.trusticket.trusticketcore.common.ErrorDefineCode;
import com.trusticket.trusticketcore.common.exception.custom.exception.ForbiddenException403;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@AllArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        throw new ForbiddenException403(ErrorDefineCode.AUTHORIZATION_FAIL);
    }
}