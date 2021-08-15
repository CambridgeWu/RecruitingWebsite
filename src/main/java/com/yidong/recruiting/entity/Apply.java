package com.yidong.recruiting.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Apply {
    private String telephone;
    private int num;              //对应的职位编号
}
