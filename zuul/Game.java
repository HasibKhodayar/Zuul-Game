import java.util.Stack;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 * 
 * @author Hasib Khodayar
 * @version As2 March 9, 2023
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
    private Stack<Room> stackedRooms;
    private Item itemheld;
    private int itemsCarryBeforeEat; 
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        stackedRooms = new Stack<Room>();
        itemheld = null;
        itemsCarryBeforeEat = 0;
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theatre, pub, lab, office;
        Item chair1,chair2, chair3,chair4, tree1,tree2, computer1,computer2, desk1,desk2,desk3, mac, stool1,stool2, cookie1,cookie2,cookie3;
        Beamer beamer1, beamer2;
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        TransporterRoom transporter = new TransporterRoom();
        
        // initialise room exits
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theatre.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);
        
        // create items
        chair1 = new Item("a wooden chair", 5.0, "chair");
        chair2 = new Item("a wooden chair", 5.0, "chair");
        chair3 = new Item("a wooden chair", 5.0, "chair");
        chair4 = new Item("a wooden chair", 5.0, "chair");
        tree1 = new Item("a fig tree", 500.5, "tree");
        tree2 = new Item("a fig tree", 500.5, "tree");
        computer1 = new Item("a PC", 2.4, "computer");
        computer2 = new Item("a PC", 2.4, "computer");
        desk1 = new Item("a wooden desk",10.5, "desk");
        desk2 = new Item("a wooden desk",10.5, "desk");
        desk3 = new Item("a wooden desk",10.5, "desk");
        mac = new Item ("a Mac laptop", 5.0, "mac");
        stool1 = new Item("a wooden stool", 4.0, "stool");
        stool2 = new Item("a wooden stool", 4.0, "stool");
        cookie1 = new Item("a yummy chocolate chip cookie",0.2,"cookie");
        cookie2 = new Item("a yummy chocolate chip cookie",0.2,"cookie");
        cookie3 = new Item("a yummy chocolate chip cookie",0.2,"cookie");
        
        beamer1 = new Beamer();
        beamer2 = new Beamer();
        
        // add items to rooms
        outside.addItem(tree1);
        outside.addItem(tree2);
        outside.addItem(cookie1);
        
        theatre.addItem(chair1);
        theatre.addItem(cookie2);
        theatre.addItem(beamer2);
        
        lab.addItem(chair2);
        lab.addItem(chair3);
        lab.addItem(computer1);
        lab.addItem(computer2);
        lab.addItem(desk1);
        lab.addItem(desk2);
        
        office.addItem(desk3);
        office.addItem(chair4);
        office.addItem(mac);
        office.addItem(beamer1);
        
        pub.addItem(stool1);
        pub.addItem(stool2);
        pub.addItem(cookie3);
        
        currentRoom = outside;  // start game outside
        previousRoom = null;
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        roomAndItemDes();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @param command The command to be processed
     * @return true If the command ends the game, false otherwise
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            look(command);
        }
        else if (commandWord.equals("eat")) {
            eat(command);
        }
        else if (commandWord.equals("back")) {
            back(command);
        }
         else if (commandWord.equals("stackBack")) {
            stackBack(command);
        }
        else if (commandWord.equals("take")){
            take(command);  
        }
         else if (commandWord.equals("drop")){
            drop(command);  
        }
         else if (commandWord.equals("charge")){
            charge(command);  
        }
         else if (commandWord.equals("fire")){
            fire(command);  
        }
        
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print a cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.getCommands());
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * 
     * @param command The command to be processed
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            previousRoom = currentRoom;
            stackedRooms.push(currentRoom);
            currentRoom = nextRoom;
            roomAndItemDes();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * 
     * @param command The command to be processed
     * @return true, if this command quits the game, false otherwise
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    /**
     * "Look" was entered. Check the rest of the command to see
     * whether we really want to look.
     * @param command The command to be processed
     */
    private void look(Command command)
    {
        if(command.hasSecondWord()){
            System.out.println("look what?");
        }
        
        else {
            roomAndItemDes();
        }
    }
    /**
     * "Eat" was entered. Check the rest of the command to see
     * whether we really want to eat.
     * @param command The command to be processed 
     */
    private void eat(Command command)
    {
        if(command.hasSecondWord()){
            System.out.println("eat what?");
        }
        
        else if(itemheld == null || ! itemheld.getName().equals( "cookie")){
            System.out.println("Nothing to eat!");
        }
    
        else{
            System.out.println("You have eaten and are no longer hungry.");
            itemheld = null;
            itemsCarryBeforeEat = 5;
        }
    }
    /**
     * "Back" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @param command The command to be processed
     */
    private void back(Command command)
    {
        Room temp = currentRoom; 
        if(command.hasSecondWord()){
            System.out.println("back what?");
        }
        else if(previousRoom == null){
            System.out.println("back where? you're at the start of the game");
        }
        else{
            Room tempRoom = currentRoom; 
            currentRoom = previousRoom;
            previousRoom = tempRoom;
            stackedRooms.push(tempRoom);
            roomAndItemDes();
        }
        
    }
    /**
     *"StackBack" was entered. Check the rest of the command to see
     * whether we really want to stackBack.
     * @param command The command to be processed
     */
    private void stackBack(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("StackBack what?");
        }
        
        else if(stackedRooms.isEmpty()){
            System.out.println("no room to stack back to");
        }
        else{
            previousRoom = currentRoom;
            currentRoom = stackedRooms.pop();
            roomAndItemDes();
        }
    }
    
    /**
     *"Take" was entered. Check the rest of the command to see
     * whether we really want to Take.
     * 
     * Removes the wanted item from the room (if it exists) and lets the player carry it.
     * 
     * @param command The command to be processed
     */
    private void take(Command command)
    {
        if(! command.hasSecondWord()){
            System.out.println("Take what");
            return;
        }
        
        if(itemheld != null){
            System.out.println("You're already holding something");
            return;
        }
        
        
        else{
            String pickUpItem = command.getSecondWord();
            
            if( ! pickUpItem.equals("cookie") && itemsCarryBeforeEat <= 0){
                System.out.println("You must take and eat a cookie before taking anything else.");
                return;
            }
            
            itemheld = currentRoom.removeItem(pickUpItem);
            
            if(itemheld == null){
                System.out.println("That item is not in room");
            }
            else{
                itemsCarryBeforeEat --;
                System.out.println("You picked up " + pickUpItem + "." );
            }
        }
    }
    /**
     *"Drop" was entered. Check the rest of the command to see
     * whether we really want to Drop.
     * 
     * If player is carrying an Item, drop it.
     * 
     * @param command The command to be processed
     */
    private void drop(Command command){
        if(command.hasSecondWord()) {
            System.out.println("Drop what?");
        }
        
        else if(itemheld == null){
            System.out.println("You have nothing to drop");
        }
        
        else{
            System.out.println("You have dropped"+ itemheld.getName() + ".");
            currentRoom.addItem(itemheld);
            itemheld = null;
        }
        
    }
    /**
     * Prints the long description of the room and any items the player is carrying
     */
    private  void roomAndItemDes(){
        System.out.println(currentRoom.getLongDescription());
        
        if (itemheld == null){
            System.out.println("You're not carrying anything");
        }
        else if(itemheld != null){
            System.out.println("You're carrying: \n"+ itemheld.getDescription());
        }
    }
    
    /** 
     * "Charge" was entered. Check the rest of the command to see
     * whether we really want to charge.
     * 
     * If the item being carried is a beamer, then we charge the beamer
     * 
     * @param command The command to be processed
     */
    private void charge(Command command){
        if(command.hasSecondWord()) {
            System.out.println("Charge what?");
        }
        
        else if(! itemheld.getName().equals("beamer") || itemheld == null){
            System.out.println("No beamer to charge");
        }
        
        else{
            Beamer beamer = (Beamer) itemheld ;
            if(beamer.isCharged()){
                System.out.println("Beamer is already charged");
            }
            else{
                beamer.charge(currentRoom);
                System.out.println("Beamer successully charged");
            }
        }
        
        
    }
    
    /** 
     * "Fire" was entered. Check the rest of the command to see
     * whether we really want to fire.
     * 
     * If the item being carried is a beamer and it is charged, then this command 
     * fires it.
     * 
     * @param command The command to be processed
     */
    private void fire(Command command){
        if(command.hasSecondWord()) {
            System.out.println("Charge what?");
        }
        
        else if(! itemheld.getName().equals("beamer") || itemheld == null){
            System.out.println("No beamer to fire");
        } 
        
        else{
            Beamer beamer = (Beamer) itemheld ;
            if(! beamer.isCharged()){
                System.out.println("Beamer is not charged");
                return;
            }
            
            previousRoom = currentRoom;
            stackedRooms.push(currentRoom);
            currentRoom = beamer.fire();
            System.out.println("Beamer has been fired");
            roomAndItemDes();
        }
    }
}
