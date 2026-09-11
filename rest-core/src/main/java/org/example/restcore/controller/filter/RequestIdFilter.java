package org.example.restcore.controller.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import static java.util.Objects.isNull;
import static java.util.UUID.randomUUID;

@Slf4j
@Component
public class RequestIdFilter extends OncePerRequestFilter {

    private static final String REQUEST_HEADER = "X-Request-Id";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) {
        var requestHeader = request.getHeader(REQUEST_HEADER);

        if (isNull(requestHeader)) {
            requestHeader = randomUUID().toString();
        }

        response.addHeader(REQUEST_HEADER, requestHeader);

        log.info(
                "Получен запрос: {}. Тип запроса {}. Путь запроса {}.",
                requestHeader, request.getMethod(), request.getServletPath()
        );
    }
}
