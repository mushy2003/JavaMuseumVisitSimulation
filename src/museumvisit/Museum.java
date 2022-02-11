package museumvisit;

import java.util.Arrays;
import java.util.Set;

public class Museum {

  private final Entrance entrance;
  private final Exit exit;
  private final Set<MuseumSite> sites;

  public Museum(Entrance entrance, Exit exit, Set<MuseumSite> sites) {
    this.entrance = entrance;
    this.exit = exit;
    this.sites = sites;
  }

  public static void main(String[] args) {
    final int numberOfVisitors = 250; // Your solution has to work with any
    // number of visitors
    final Museum museum = buildSimpleMuseum(); // buildLoopyMuseum();

    // create the threads for the visitors and get them moving
    Thread threads[] = new Thread[numberOfVisitors];

    Arrays.setAll(threads, t -> new Thread(new Visitor(String.valueOf(t), museum.entrance)));
    Arrays.stream(threads).forEach(t -> t.start());

    // wait for them to complete their visit

    Arrays.stream(threads)
        .forEach(
            t -> {
              try {
                t.join();
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            });

    // Checking no one is left behind
    if (museum.getExit().getOccupancy() == numberOfVisitors) {
      System.out.println("\nAll the visitors reached the exit\n");
    } else {
      System.out.println(
          "\n"
              + (numberOfVisitors - museum.getExit().getOccupancy())
              + " visitors did not reach the exit. Where are they?\n");
    }

    System.out.println("Occupancy status for each room (should all be zero, but the exit site):");
    museum
        .getSites()
        .forEach(
            s -> {
              System.out.println("Site " + s.getName() + " final occupancy: " + s.getOccupancy());
            });
  }

  public static Museum buildSimpleMuseum() {
    Entrance entrance = new Entrance();
    Exit exit = new Exit();
    MuseumSite exhibitionRoom = new ExhibitionRoom("Exhibition Room", 10);
    entrance.addExitTurnstile(new Turnstile(entrance, exhibitionRoom));
    exhibitionRoom.addExitTurnstile(new Turnstile(exhibitionRoom, exit));
    return new Museum(entrance, exit, Set.of(exhibitionRoom));
  }

  public static Museum buildLoopyMuseum() {
    Entrance entrance = new Entrance();
    Exit exit = new Exit();
    MuseumSite venomRoom = new ExhibitionRoom("VenomKillerAndCureRoom", 10);
    MuseumSite whalesRoom = new ExhibitionRoom("Whales Exhibition Room", 10);
    entrance.addExitTurnstile(new Turnstile(entrance, venomRoom));
    venomRoom.addExitTurnstile(new Turnstile(venomRoom, whalesRoom));
    venomRoom.addExitTurnstile(new Turnstile(venomRoom, exit));
    whalesRoom.addExitTurnstile(new Turnstile(whalesRoom, venomRoom));
    return new Museum(entrance, exit, Set.of(venomRoom, whalesRoom));
  }

  public Entrance getEntrance() {
    return entrance;
  }

  public Exit getExit() {
    return exit;
  }

  public Set<MuseumSite> getSites() {
    return sites;
  }
}
