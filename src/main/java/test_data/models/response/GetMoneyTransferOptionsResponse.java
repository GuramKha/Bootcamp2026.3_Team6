package test_data.models.response;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public record GetMoneyTransferOptionsResponse(
	@JsonProperty("mtSystem")
	String mtSystem,
	@JsonProperty("fee")
	int fee
) {
}