package test_data.models.response;
import com.fasterxml.jackson.annotation.JsonProperty;

public record GetMoneyTransferOptionsResponse(
	@JsonProperty("mtSystem")
	String mtSystem,
	@JsonProperty("fee")
	int fee
) {
}