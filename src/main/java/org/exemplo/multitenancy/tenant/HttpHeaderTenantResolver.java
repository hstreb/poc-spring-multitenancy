package org.exemplo.multitenancy.tenant;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
public class HttpHeaderTenantResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpHeaderTenantResolver.class);

    private final String header;
    private final ObjectMapper objectMapper;

    public HttpHeaderTenantResolver(@Value("${multitenacy.header:X-TenantId}") String header, ObjectMapper objectMapper) {
        this.header = header;
        this.objectMapper = objectMapper;
    }

    public Optional<String> resolveTenantId(@NonNull HttpServletRequest request, HttpServletResponse response) {
        var tenantId = request.getHeader(header);
        if (tenantId == null || tenantId.isBlank()) {
            String mensagem = "Header '" + header + "' not found!";
            var errorResponse = Map.of("mensagem", mensagem);
            LOGGER.warn(mensagem);
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try {
                response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return Optional.empty();
        }
        return Optional.of(tenantId);
    }

}
