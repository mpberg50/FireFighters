package main.api;

public interface Firefighter {

  /**
   * Get the firefighter's current location. Initially, the firefighter should be at the FireStation
   *
   * @return {@link CityNode} representing the firefighter's current location
   */
  CityNode getLocation();

  /**
   * Get the total distance traveled by this firefighter. Distances should be represented using TaxiCab
   * Geometry: https://en.wikipedia.org/wiki/Taxicab_geometry
   *
   * @return the total distance traveled by this firefighter
   */
  int distanceTraveled();

  /**
   * Moves the firefighters location to a new location
   * @param cityNode - The new location of this firefighter
   */
  void moveLocation(CityNode cityNode);

  /**
   * Creates a clone of this Firefighter
   * @return clone of this Firefighter
   */
  Firefighter clone();
}
