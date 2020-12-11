package com.cy.news.common.Pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * comments
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class Comments implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long cId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long nId;

    private Integer uId;

    private String uNickname;

    private Date cDate;

    private Integer cStatus;

    private String cContent;

    private static final long serialVersionUID = 1L;

}