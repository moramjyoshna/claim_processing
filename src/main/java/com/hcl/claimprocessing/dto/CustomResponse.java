package com.hcl.claimprocessing.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomResponse {
	private String message;
	private Integer statusCode;
}
