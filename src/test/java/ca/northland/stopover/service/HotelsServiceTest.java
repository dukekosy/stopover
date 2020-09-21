package ca.northland.stopover.service;

import ca.northland.stopover.apis.travel.Login;
import ca.northland.stopover.apis.travel.LoginInterface;
import ca.northland.stopover.apis.travel.amadeus.AmadeusImpl;
import ca.northland.stopover.models.AirportHotels;
import com.amadeus.exceptions.ResponseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
class HotelsServiceTest {

  HotelsService hotelsService = new HotelsService(new AmadeusImpl());

  LoginInterface login = Login.INSTANCE;

  String locationID = "MUC";

  @BeforeEach
  void setUp() {
    login.getAmadeus();
  }

  @Test
  void getAirportHotelsNearby() throws ResponseException {
    LocalDate checkinDate = LocalDate.of(2020, 9, 20);

    Set<AirportHotels> airportHotels =
        hotelsService.getAirportHotelsNearby(locationID, checkinDate, checkinDate.plusDays(1));
    assertTrue(airportHotels.size() != 0);
  }
}
