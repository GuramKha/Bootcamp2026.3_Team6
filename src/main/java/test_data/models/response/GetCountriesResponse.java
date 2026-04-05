package test_data.models.response;
<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
=======
import com.fasterxml.jackson.annotation.JsonProperty;

>>>>>>> ab219e7d007fb592df4446232b45517321056ed8
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