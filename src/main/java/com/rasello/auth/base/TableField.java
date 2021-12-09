package com.rasello.auth.base;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TableField {
    private String name;
    private String value;
    private SearchType searchType;
    private OrderType orderType;
}
