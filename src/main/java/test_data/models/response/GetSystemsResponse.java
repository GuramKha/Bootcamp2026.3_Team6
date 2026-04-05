package test_data.models.response;
import java.util.List;
<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
=======
import com.fasterxml.jackson.annotation.JsonProperty;

>>>>>>> ab219e7d007fb592df4446232b45517321056ed8
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