package ca.northland.stopover.models;

import com.amadeus.resources.HotelOffer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

@AutoValue
@JsonDeserialize(builder = AutoValue_AirportHotels.Builder.class)
public abstract class AirportHotels {

  private static Builder builder() {
    return new AutoValue_AirportHotels.Builder();
  }

  public static AirportHotels fromHotelOffer(HotelOffer hotelOffer) {
    HotelOffer.AddressType addressType = hotelOffer.getHotel().getAddress();

    // adding the cheapest room from the list of rooms offered
    // just in case if amadeus fails to send only one offer, rather than getting first index element
    BigDecimal minRate;
    if (hotelOffer.getOffers().length > 1) {
      minRate =
          Arrays.stream(hotelOffer.getOffers())
              .map(offer -> offer.getPrice().getTotal())
              .collect(Collectors.toList())
              .stream()
              .map(BigDecimal::new)
              .min(Comparator.naturalOrder())
              .orElseGet(() -> new BigDecimal(0));
    } else {
      minRate = new BigDecimal(hotelOffer.getOffers()[0].getPrice().getTotal());
    }
    return AirportHotels.builder()
        .name(hotelOffer.getHotel().getName())
        .phone(hotelOffer.getHotel().getContact().getPhone())
        .rate(minRate)
        .address(
            Address.builder()
                .lines(Arrays.stream(addressType.getLines()).collect(Collectors.toList()))
                .cityName(addressType.getCityName())
                .postalCode(hotelOffer.getHotel().getAddress().getPostalCode())
                .cityName(hotelOffer.getHotel().getName())
                .countryCode(addressType.getCountryCode())
                .build())
        .build();
  }

  @JsonProperty("name")
  abstract String name();

  @JsonProperty("address")
  abstract Address address();

  @JsonProperty("phone")
  abstract String phone();

  @JsonProperty("rate")
  abstract BigDecimal rate();

  @AutoValue.Builder
  public abstract static class Builder {

    @JsonProperty("name")
    public abstract Builder name(String name);

    @JsonProperty("address")
    public abstract Builder address(Address address);

    @JsonProperty("phone")
    public abstract Builder phone(String phone);

    @JsonProperty("rate")
    public abstract Builder rate(BigDecimal rate);

    public abstract AirportHotels build();
  }
}
