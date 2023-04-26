import java.util.Random;

/**
 * Class defining a transporter room that inherits from class Room.
 * Transporter room takes u to a random room when exiting
 *
 * @author Hasib Khodayar 101225523
 * @version As2 March 12 2023
 */
public class TransporterRoom extends Room
{
    private Random rand;
    
    /**
     * Constructs a new transporter room using super and sets exit to null
     */
    public TransporterRoom(){
        super("in a Transporter Room");
        setExit("anywhere",null);
        rand = new Random();
    }
    
    /**
    * Returns a random room, independent of the direction parameter.
    *
    * @param direction Ignored.
    * @return A randomly selected room.
    */
    public Room getExit(String direction)
    {
    return findRandomRoom();
    }
    
    /**
    * Choose a random room.
    *
    * @return The room we end up in upon leaving this one.
    */
    private Room findRandomRoom()
    {
       int randRoomIndex = rand.nextInt(Room.getRoomList().size());
        
       return Room.getRoomList().get(randRoomIndex);
    }
}