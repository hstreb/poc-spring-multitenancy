package org.exemplo.multitenancy.datasource;

import org.exemplo.multitenancy.tenant.TenantContext;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultitenantDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return TenantContext.getTenantId();
    }
}
