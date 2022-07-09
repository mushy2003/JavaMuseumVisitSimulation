package museumvisit;

public class ExhibitionRoom extends MuseumSite {

  protected int capacity;

  public ExhibitionRoom(String name, int capacity) {
    super(name);
    assert capacity > 0;
    this.capacity = capacity;
  }

  public int getCapacity() {
    return capacity;
  }

  @Override
  public boolean hasAvailability() {
    return occupancy < capacity;
  }
}
