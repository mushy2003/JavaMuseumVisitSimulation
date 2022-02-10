package museumvisit;

public class Entrance extends MuseumSite {

  public Entrance() {
    super("Entrance");
  }

  @Override
  public void enter() {
    occupancy++;
  }

  @Override
  public void exit() {
    occupancy--;
  }
}
