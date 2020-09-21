package ca.northland.stopover.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
@JsonDeserialize(builder = AutoValue_Address.Builder.class)
public abstract class Address {

  public static Builder builder() {
    return new AutoValue_Address.Builder();
  }

  @JsonProperty("postalCode")
  abstract String postalCode();

  @JsonProperty("cityName")
  abstract String cityName();

  @JsonProperty("countryCode")
  abstract String countryCode();

  @JsonProperty("addressLines")
  abstract List<String> lines();

  @AutoValue.Builder
  public abstract static class Builder {

    @JsonProperty("postalCode")
    public abstract Builder postalCode(String postalCode);

    @JsonProperty("cityName")
    public abstract Builder cityName(String cityName);

    @JsonProperty("countrycode")
    public abstract Builder countryCode(String countryCode);

    @JsonProperty("addressLines")
    public abstract Builder lines(List<String> lines);

    public abstract Address build();
  }
}
