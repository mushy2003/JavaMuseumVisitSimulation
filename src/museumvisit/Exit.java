package museumvisit;

public class Exit extends MuseumSite {

  public Exit() {
    super("Exit");
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
