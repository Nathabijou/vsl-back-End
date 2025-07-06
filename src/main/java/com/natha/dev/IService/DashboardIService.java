package com.natha.dev.IService;

import com.natha.dev.Dto.KpiResponse;
import com.natha.dev.Model.DashboardFilter;

public interface DashboardIService {
    KpiResponse getKpiData(DashboardFilter filter, String username);
}
