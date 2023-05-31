package com.yevini.myvelog.web.dto.request;

import com.yevini.myvelog.web.dto.request.variables.Variables;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RequestBody {

    private String operationName;
    private Variables variables;
    private String query;
}
