package main.firefighters;

import main.api.CityNode;
import main.api.Firefighter;

public class FirefighterImpl implements Firefighter {
  private int totalDistanceTraveled;
  private CityNode currentLocation;

  public FirefighterImpl(CityNode fireStationLocation) {
    totalDistanceTraveled = 0;
    currentLocation = fireStationLocation;
  }

  public FirefighterImpl(CityNode currentLocation, int totalDistanceTraveled) {
    this.totalDistanceTraveled = totalDistanceTraveled;
    this.currentLocation = currentLocation;
  }

  @Override
  public void moveLocation(CityNode cityNode) {
    totalDistanceTraveled += currentLocation.getDistance(cityNode);
    currentLocation = cityNode;
  }

  @Override
  public CityNode getLocation() {
    return currentLocation;
  }

  @Override
  public int distanceTraveled() {
    return totalDistanceTraveled;
  }

  @Override
  public Firefighter clone() {
     return new FirefighterImpl(currentLocation.clone(), totalDistanceTraveled);
  }
}
