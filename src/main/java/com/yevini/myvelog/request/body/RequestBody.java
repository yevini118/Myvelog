package com.yevini.myvelog.request.body;

import com.yevini.myvelog.request.body.variables.Variables;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RequestBody {

    private String operationName;
    private Variables variables;
    private String query;
}
