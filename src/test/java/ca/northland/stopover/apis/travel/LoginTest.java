package ca.northland.stopover.apis.travel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class LoginTest {

  LoginInterface login = Login.INSTANCE;

  @Test
  void getAmadeus() {
    assertNotNull(login.getAmadeus());
  }
}
