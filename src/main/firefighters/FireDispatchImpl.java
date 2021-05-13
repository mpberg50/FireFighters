package main.firefighters;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import main.api.City;
import main.api.CityNode;
import main.api.FireDispatch;
import main.api.Firefighter;
import main.api.exceptions.NoFireFoundException;

public class FireDispatchImpl implements FireDispatch {
  List<Firefighter> fireFighterList;
  City city;

  public FireDispatchImpl(City city) {
    this.city = city;
    fireFighterList = new ArrayList<>();
  }

  @Override
  public void setFirefighters(int numFirefighters) {
    if(fireFighterList.size() < numFirefighters) {
      for(int x = fireFighterList.size(); x < numFirefighters; x++) {
        fireFighterList.add(new FirefighterImpl(city.getFireStation().getLocation()));
      }
    } else {
      while(fireFighterList.size() > numFirefighters) {
        fireFighterList.remove(fireFighterList.size() - 1);
      }
    }
  }

  @Override
  public List<Firefighter> getFirefighters() {
    return fireFighterList;
  }

  public int getTotalDistance(List<Firefighter> fireFighterList) {
    if(fireFighterList == null) {
      return 0;
    }
    int totalDistance = 0;
    for(Firefighter firefighter : fireFighterList) {
      totalDistance +=firefighter.distanceTraveled();
    }
    return totalDistance;
  }

  public List<Firefighter> dispatch(HashSet<CityNode> burningBuildingsLeft, List<Firefighter> fireFighters) {
    if(burningBuildingsLeft == null || burningBuildingsLeft.size() == 0) {
      return fireFighters;
    } else {
      List<Firefighter> minFireFighters = new ArrayList<>();
      for(CityNode burningBuilding : burningBuildingsLeft) {
        HashSet<CityNode> removedBurningBuildingsLeft = new HashSet<>();

        for(CityNode addBurningBuilding : burningBuildingsLeft) {
          if(addBurningBuilding != burningBuilding ) {
            removedBurningBuildingsLeft.add(addBurningBuilding.clone());
          }
        }

        for(int x = 0; x < fireFighters.size(); x++) {
          List<Firefighter> movedFireFighters = new ArrayList<>();
          for(Firefighter firefighter : fireFighters) {
            movedFireFighters.add(firefighter.clone());
          }
          Firefighter firefighterPutOutFire = movedFireFighters.get(x);
          firefighterPutOutFire.moveLocation(burningBuilding);
          List<Firefighter> bestDispatchFireFighters = dispatch(removedBurningBuildingsLeft, movedFireFighters);

          int totalDistance = getTotalDistance(bestDispatchFireFighters);
          if(minFireFighters.size() == 0 || totalDistance < getTotalDistance(minFireFighters)) {
            minFireFighters = bestDispatchFireFighters;
          }
        }
      }
      return minFireFighters;
    }
  }


  @Override
  public void dispatchFirefighers(CityNode... burningBuildings) throws NoFireFoundException {
    HashSet<CityNode> buildingSet = new HashSet<>();
    for(CityNode burningBuilding : burningBuildings) {
      buildingSet.add(burningBuilding);
    }
    fireFighterList = dispatch(buildingSet, fireFighterList);
    for(CityNode burningBuilding : burningBuildings) {
      city.getBuilding(burningBuilding).extinguishFire();
    }

  }
}
