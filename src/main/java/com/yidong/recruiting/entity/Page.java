package com.yidong.recruiting.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Page {
    private Integer pageNum=1;
    private Integer pageSize=2;
}
