package com.base.utils.mymapper.enums;

/**
 * 通用mapper 条件查询枚举类(建议单表查询时使用,多表复杂SQL建议使用mybatis xml 写SQL实现,以提升可读性)
 * @author json
 * @version 1.0
 * @date 2019/9/4 10:57
 */
public enum MapperCriteriaOpTypeEnum {
    EQUAL_TO(1,"="),
    NOT_EQUAL_TO(2,"!="),
    IN(3,"in(?)"),
    NOT_IN(4,"not in(...)"),
    LIKE(5,"like..."),
    NOT_LIKE(6,"not like ..."),
    IS_NULL(7,"is null"),
    IS_NOT_NULL(8,"is not null"),
    LessThan(9,"<"),
    LessThanOrEqualTo(10,"<="),
    GreaterThan(11,">"),
    GreaterThanOrEqualTo(12,">="),
    Between(13,"between..."),
    NotBetween(14,"not between"),
    ;

    Integer code;
    String descr;

    MapperCriteriaOpTypeEnum(Integer code, String descr) {
        this.code = code;
        this.descr = descr;
    }


    public Integer getCode() {
        return code;
    }

    public String getDescr() {
        return descr;
    }
}
