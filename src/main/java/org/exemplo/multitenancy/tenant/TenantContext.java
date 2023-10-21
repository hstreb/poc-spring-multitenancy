package org.exemplo.multitenancy.tenant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TenantContext {

    private static final Logger LOGGER = LoggerFactory.getLogger(TenantContext.class);
    private static final ThreadLocal<String> tenantId = new InheritableThreadLocal<>();

    public static String getTenantId() {
        return tenantId.get();
    }

    public static void setTenantId(String tenant) {
        LOGGER.debug("Ajustando para o tenant: {}", tenant);
        tenantId.set(tenant);
    }

    public static void clear() {
        if (getTenantId() != null) {
            LOGGER.debug("Limpando tenant: {}", getTenantId());
            tenantId.remove();
        }
    }

}
