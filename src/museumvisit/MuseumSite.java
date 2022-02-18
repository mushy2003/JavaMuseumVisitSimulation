package museumvisit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class MuseumSite {

  protected final String name;
  protected int occupancy;
  protected List<Turnstile> exitTurnstiles;
  protected final Lock lock;

  public MuseumSite(String name) {
    this.name = name;
    this.occupancy = 0;
    this.exitTurnstiles = new ArrayList<>();
    this.lock = new ReentrantLock();
  }

  boolean hasAvailability() {
    return true;
  }

  public Lock getLock() {
    return lock;
  }

  public void enter() {
    // to be implemented
  }

  public void exit() {
    assert occupancy > 0;
    // to be implemented
  }

  public void addExitTurnstile(Turnstile turnstile) {
    exitTurnstiles.add(turnstile);
  }

  public List<Turnstile> getExitTurnstiles() {
    return new ArrayList<>(exitTurnstiles);
  }

  public String getName() {
    return name;
  }

  public int getOccupancy() {
    return occupancy;
  }

  @Override
  public String toString() {
    return "Site[" + name + "]";
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof MuseumSite) {
      return name.equals(((MuseumSite) obj).name);
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(getClass(), name);
  }
}
