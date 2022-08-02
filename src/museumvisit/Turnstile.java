package museumvisit;

import java.util.Optional;

public class Turnstile {

  private final MuseumSite originRoom;
  private final MuseumSite destinationRoom;

  public Turnstile(MuseumSite originRoom, MuseumSite destinationRoom) {
    assert !originRoom.equals(destinationRoom);
    this.originRoom = originRoom;
    this.destinationRoom = destinationRoom;
  }

  public Optional<MuseumSite> passToNextRoom() {
    MuseumSite firstRoomToLock, secondRoomToLock;
    // Ensures there are no circular dependencies and so no deadlock.
    if (originRoom.name.compareTo(destinationRoom.name) < 0) {
      firstRoomToLock = originRoom;
      secondRoomToLock = destinationRoom;
    } else {
      firstRoomToLock = destinationRoom;
      secondRoomToLock = originRoom;
    }

    firstRoomToLock.lock.lock();
    secondRoomToLock.lock.lock();

    try {
      Optional<MuseumSite> result = Optional.empty();
      if (destinationRoom.hasAvailability()) {
        originRoom.exit();
        destinationRoom.enter();
        result = Optional.of(destinationRoom);
      }
      return result;
    } finally {
      secondRoomToLock.lock.unlock();
      firstRoomToLock.lock.unlock();
    }
  }

  public MuseumSite getOriginRoom() {
    return originRoom;
  }

  public MuseumSite getDestinationRoom() {
    return destinationRoom;
  }
}
