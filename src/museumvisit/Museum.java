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
    final int numberOfVisitors = 100; // Your solution has to work with any
    // number of visitors
    final Museum museum = buildLoopyMuseum(); // buildLoopyMuseum();

    // create the threads for the visitors and get them moving
    final Thread[] visitors = new Thread[numberOfVisitors];

    for (int i = 0; i < visitors.length; i++) {
      visitors[i] = new Thread(new Visitor(String.valueOf(i), museum.getEntrance()));
      visitors[i].start();
    }

    // wait for them to complete their visit
    Arrays.stream(visitors)
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
    ExhibitionRoom exhibitionRoom = new ExhibitionRoom("Exhibition Room", 10);

    entrance.addExitTurnstile(new Turnstile(entrance, exhibitionRoom));
    exhibitionRoom.addExitTurnstile(new Turnstile(exhibitionRoom, exit));

    return new Museum(entrance, exit, Set.of(entrance, exhibitionRoom, exit));

  }

  public static Museum buildLoopyMuseum() {
    Entrance entrance = new Entrance();
    Exit exit = new Exit();
    ExhibitionRoom exhibitionRoom1 = new ExhibitionRoom("VenomKillerAndCureRoom", 10);
    ExhibitionRoom exhibitionRoom2 = new ExhibitionRoom("Whales Exhibition Room", 10);

    entrance.addExitTurnstile(new Turnstile(entrance, exhibitionRoom1));
    exhibitionRoom1.addExitTurnstile(new Turnstile(exhibitionRoom1, exit));
    exhibitionRoom1.addExitTurnstile(new Turnstile(exhibitionRoom1, exhibitionRoom2));
    exhibitionRoom2.addExitTurnstile(new Turnstile(exhibitionRoom2, exhibitionRoom1));

    return new Museum(entrance, exit, Set.of(entrance, exhibitionRoom1, exhibitionRoom2, exit));
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
