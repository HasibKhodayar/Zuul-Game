import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 * 
 * @author Hasib Khodayar 101225523
 * @version As2 March 12, 2023
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Item> items;
    private static ArrayList<Room> allRooms = new ArrayList<Room>();
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * 
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String, Room>();
        items = new ArrayList<Item>();
        allRooms.add(this);
    }

    /**
     * Define an exit from this room.
     * 
     * @param direction The direction of the exit
     * @param neighbour The room to which the exit leads
     */
    public void setExit(String direction, Room neighbour) 
    {
        exits.put(direction, neighbour);
    }

    /**
     * Returns a short description of the room, i.e. the one that
     * was defined in the constructor
     * 
     * @return The short description of the room
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a long description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     *     
     * @return A long description of this room and any items that are in that room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString() + "\n" 
        + getItemString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * 
     * @return Details of the room's exits
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * 
     * @param direction The exit's direction
     * @return The room in the given direction
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * Add an item object to a room. 
     * @param item, item to be added to the rom
     */
    public void addItem(Item item){
        items.add(item);
    }
    
    /**
     * Remove an item object to a room. 
     * @param item, item to be removed to the room
     * @return null if item not in room else return the item 
     */
    public Item removeItem(String item){
        for(Item itemm:items){
            if(itemm.getName().equals(item)){
                Item retItem = itemm;
                items.remove(itemm);
                return retItem;
            }
        }
        
        return null;
    }
    
    /**
     * Return a string describing the room's items
     * 
     * @return string of the room's items
     */
    public String getItemString()
    {
        String returnString = "Items:";
        for(Item item : items) {
            returnString += "\n" + item.getDescription();
        }
        return returnString;
    }
    
    /**
     * Method to return the arraylist of all the rooms in the game
     * @return ArrayList<Room> containing all the rooms
     */
    static public ArrayList<Room> getRoomList()
    {
        return allRooms;
    }
}

