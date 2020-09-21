package ca.northland.stopover.apis.travel;

import com.amadeus.Amadeus;

public enum Login implements LoginInterface {
  INSTANCE;
  private Amadeus amadeus = null;

  // key and secret should come off a database
  private void login() {
    amadeus = Amadeus.builder("OQoxzEGFAIxMlAeb8J638QzN1GBvz9Xh", "AJ45yIWNEzBM4msY").build();
  }

  @Override
  public Amadeus getAmadeus() {
    if (amadeus == null) login();
    return amadeus;
  }
}
