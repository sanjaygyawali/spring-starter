package com.rasello.auth.base;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TableResponse<T> {
    private TableRequest request;
    private Integer page;
    private Integer limit;
    private Integer totalPage;
    private Long totalRecords;
    private List<T> data;
}
