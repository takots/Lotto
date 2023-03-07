package com.lotto.model;
import java.sql.Timestamp;

import lombok.Data;
@Data
public class DescribeVO {
	private Integer type;
	private String Content;
	private String nnstr;
	private String numberStr;
	private Integer buildOrg;
	private Timestamp recDate;
}
