package com.rasello.auth.base;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TableRequest {
    private Integer page;
    private Integer count;
    private List<String> outputFields;
    private List<TableField> tableFields;
}
