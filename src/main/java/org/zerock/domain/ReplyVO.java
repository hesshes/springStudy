package org.zerock.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyVO {

	private long rno;
	private long bno;
	
	String reply;
	String replyer;
	Date replyDate;
	Date updateDate;
	
}
