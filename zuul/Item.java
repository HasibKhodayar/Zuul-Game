
/**
 * Class that represents an item that can be put in the room.
 *
 * @author Hasib Khodayar 101225523
 * @version As2 Tuesday March 9 2023
 */
public class Item
{
    
    private String description;
    private double weight;
    private String name;

    /**
     * Constructor for objects of class Item
     * @param description, the string description of the item
     * @param weight, weight of the item in kg 
     * @param name, the name of the item
     */
    public Item(String description, double weight, String name)
    {
        // initialise instance variables
        this.description = description;
        this.weight = weight;
        this.name = name;
    }

    /**
     * Method to get the whole description of an item, name, description and weight
     *
     * @return  the name and description of the item and how much it weighs
     */
    public String getDescription()
    {
        // put your code here
        return name + ":" + description + " that weighs " + weight + "kg";
    }
    
    /**
     * Method to get the name of an item
     *
     * @return the name of the item
     */
    public String getName()
    {
        return name;
    }
}
