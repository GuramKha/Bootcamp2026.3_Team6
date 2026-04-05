package test_data.models.response;
<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
=======
import com.fasterxml.jackson.annotation.JsonProperty;

>>>>>>> ab219e7d007fb592df4446232b45517321056ed8
public record GetMoneyTransferOptionsResponse(
	@JsonProperty("mtSystem")
	String mtSystem,
	@JsonProperty("fee")
	int fee
) {
}