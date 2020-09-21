package ca.northland.stopover.service;

import ca.northland.stopover.apis.travel.TravelApi;
import ca.northland.stopover.apis.travel.amadeus.AmadeusImpl;
import ca.northland.stopover.models.AirportHotels;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.HotelOffer;
import com.amadeus.resources.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HotelsService {

  private final TravelApi travelApi;

  @Autowired
  public HotelsService(AmadeusImpl amadeus) {
    this.travelApi = amadeus;
  }

  public Set<AirportHotels> getAirportHotelsNearby(
      String cityCode, LocalDate checkInDate, LocalDate checkOutDate) throws ResponseException {
    Location city = travelApi.getLocationInformation(cityCode);

    Location[] airports =
        travelApi.getClosestAirport(
            city.getGeoCode().getLongitude(), city.getGeoCode().getLatitude());
    if (airports.length == 0) {
      return Collections.emptySet();
    }

    HotelOffer[] hotelOffers =
        travelApi.searchHotels(
            airports[0].getGeoCode().getLongitude(),
            airports[0].getGeoCode().getLatitude(),
            checkInDate,
            checkOutDate);
    // return top 3
    return Arrays.stream(hotelOffers)
        .map(AirportHotels::fromHotelOffer)
        .limit(3)
        .collect(Collectors.toSet());
  }
}
