
/**
 * Beamer inherits from Item.  Player can charge or fire a beamer when carrying. 
 * Firing a charged beamer returns u to the room u charged it in.
 * 
 *
 * @author Hasib Khodayar 101225523
 * @version As2 March 12 2023
 */
public class Beamer extends Item
{
    // instance variables - replace the example below with your own
    private Room chargedRoom;

    /**
     * Constructor for objects of class Beamer
     * All beamers are the same
     */
    public Beamer()
    {
        super("A wonderful beamer",1.0,"beamer");
    }

    /**
     * Method to charge the beamer
     *
     * @param  room , the room beamer is charged in
     * 
     */
    public void charge(Room room)
    {
        // put your code here
        chargedRoom = room;
    }
    /**
     * Method to check if the room is already charged
     * @return true if the beamer is charged or false if its not
     */
    public boolean isCharged()
    {
        return (chargedRoom != null);
    }
    /**
     * Method "fire" returns you to the room the beamer was charged in or null if
     * beamer wasnt charged
     * @return the room the beamer was charged in 
     */
    public Room fire()
    {
        Room fireRoom = chargedRoom;
        chargedRoom=null; 
        return fireRoom;
    }
}
