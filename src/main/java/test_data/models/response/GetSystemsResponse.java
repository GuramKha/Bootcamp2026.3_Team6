package test_data.models.response;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public record GetSystemsResponse(
	@JsonProperty("mtSystem")
	String mtSystem,
	@JsonProperty("imageUrl")
	String imageUrl,
	@JsonProperty("name")
	String name,
	@JsonProperty("currencies")
	List<String> currencies
) {
}