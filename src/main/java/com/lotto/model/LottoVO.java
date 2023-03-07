package com.lotto.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LottoVO implements Serializable {
	private String lotto;
	private Integer special;
	private Integer lRecId;
//	private LocalDateTime recDate;
	private Timestamp recDate;
	private String bet;
	private String win;
	private Integer winnum;
}