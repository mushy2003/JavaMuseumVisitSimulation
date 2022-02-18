package museumvisit;

import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Turnstile {

  private final MuseumSite originRoom;
  private final MuseumSite destinationRoom;

  public Turnstile(MuseumSite originRoom, MuseumSite destinationRoom) {
    assert !originRoom.equals(destinationRoom);
    this.originRoom = originRoom;
    this.destinationRoom = destinationRoom;
  }

  public Optional<MuseumSite> passToNextRoom() {
    Optional<MuseumSite> result = Optional.empty();
    if (destinationRoom.getName().compareTo(originRoom.getName()) >= 0) {
      destinationRoom.getLock().lock();
      originRoom.getLock().lock();
    } else {
      originRoom.getLock().lock();
      destinationRoom.getLock().lock();
    }

    try {
      if (destinationRoom.hasAvailability()) {
        originRoom.exit();
        destinationRoom.enter();
        result = Optional.of(destinationRoom);
      }
    } finally {
      originRoom.getLock().unlock();
      destinationRoom.getLock().unlock();
    }
    return result;
  }

  public MuseumSite getOriginRoom() {
    return originRoom;
  }

  public MuseumSite getDestinationRoom() {
    return destinationRoom;
  }
}
