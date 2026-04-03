package test_data.models.response;
import com.fasterxml.jackson.annotation.JsonProperty;

public record GetCountriesResponse(
	@JsonProperty("code")
	String code,
	@JsonProperty("imageUrl")
	String imageUrl,
	@JsonProperty("name")
	String name,
	@JsonProperty("phoneCode")
	String phoneCode
) {
}