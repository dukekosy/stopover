package ca.northland.stopover.apis.travel.amadeus;

import ca.northland.stopover.apis.travel.TravelApi;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.HotelOffer;
import com.amadeus.resources.Location;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AmadeusImplTest {

  String locationIATA = "MUC";
  double longitude = -0.44161;
  double latitude = 51.57285;
  @InjectMocks private TravelApi travelApi = new AmadeusImpl();

  @Test
  void getLocationInformation() {
    try {
      Location location = travelApi.getLocationInformation(locationIATA);
      assertThat(location.getIataCode(), is(locationIATA));
    } catch (ResponseException e) {
      e.printStackTrace();
    }
    System.out.println();
  }

  @Test
  void getClosestAirport() {
    try {
      Location[] location = travelApi.getClosestAirport(longitude, latitude);
      assertTrue(location.length != 0);
    } catch (ResponseException e) {
      e.printStackTrace();
    }
  }

  @Test
  void searchHotels() {
    LocalDate now = LocalDate.now();
    try {
      HotelOffer[] hotelOffers = travelApi.searchHotels(longitude, latitude, now, now.plusDays(1));
      assertTrue(hotelOffers.length != 0);
    } catch (ResponseException e) {
      e.printStackTrace();
    }
  }
}
