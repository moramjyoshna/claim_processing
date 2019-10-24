package com.hcl.claimprocessing.dto;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClaimUpdateRequestDto {
	@NotNull
	private Long claimId;
	@NotNull
	private String reason;
	@NotNull
	private String claimStatus;
	@NotNull
	private Integer roleId;
}
