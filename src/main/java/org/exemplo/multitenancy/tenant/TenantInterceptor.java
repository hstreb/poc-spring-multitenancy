package org.exemplo.multitenancy.tenant;

import io.micrometer.common.KeyValue;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.exemplo.multitenancy.ClienteController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.ServerHttpObservationFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Component
public class TenantInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TenantInterceptor.class);
    private final HttpHeaderTenantResolver httpHeaderTenantResolver;
    private final List<String> ignoreUrls;

    public TenantInterceptor(HttpHeaderTenantResolver httpHeaderTenantResolver, @Value("${security.ignore.urls}") List<String> ignoreUrls) {
        this.httpHeaderTenantResolver = httpHeaderTenantResolver;
        this.ignoreUrls = ignoreUrls;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (shouldNotFilter(request)) {
            return true;
        }
        return httpHeaderTenantResolver.resolveTenantId(request, response)
                .map(tenantId -> {
                    TenantContext.setTenantId(tenantId);
                    configureLogs(tenantId);
                    configureTraces(tenantId, request);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        clear();
    }

    protected boolean shouldNotFilter(HttpServletRequest request) {
        var matcher = new AntPathMatcher();
        var uri = request.getRequestURI();
        var result = ignoreUrls.stream()
                .anyMatch(url -> matcher.match(url, uri));
        LOGGER.debug("IgnoreUrls: {}, URI: '{}' deve ser ignorada: {}", ignoreUrls, uri, result);
        return result;
    }

    private void configureLogs(String tenantId) {
        MDC.put("tenantId", tenantId);
    }

    private void configureTraces(String tenantId, HttpServletRequest request) {
        ServerHttpObservationFilter.findObservationContext(request).ifPresent(context -> {
            if (tenantId != null) {
                context.addLowCardinalityKeyValue(KeyValue.of("tenant.id", tenantId));
            }
        });
    }

    private void clear() {
        MDC.remove("tenantId");
        TenantContext.clear();
    }

}
