package com.emr.slgi.common.dto;

import java.util.List;

public record ListResponse<T>(
    List<T> list
) {}
