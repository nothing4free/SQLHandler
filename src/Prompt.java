import java.util.Scanner;

public class Prompt {

    private Scanner s = new Scanner(System.in);
    private String opt1;
    private String opt2;
    private SQLHandler sh = new SQLHandler();

    public Prompt() {
        System.out.println("[Prompt] > SQLHandler v0.1, by Jorge Martinez (nothing4free)");
        System.out.println("           Select option 6 to display a help menu, or option 7 to display the full credits.");
        askForCommands();
    }

    // method containing the user console.
    // depending on the input, it calls the appropiate method.
    private void askForCommands() {
        while(1<2) {
            System.out.print("[Prompt] > ");
            opt1 = s.nextLine();
            switch (opt1) {
                case "open":
                    startConnection();
                    break;
                case "add":
                    addEntry();
                    break;
                case "remove":
                    removeEntry();
                    break;
                case "display":
                    displayEntries();
                    break;
                case "close":
                    closeConnection();
                    break;
                case "help":
                    showHelp();
                    break;
                case "credits":
                    showCredits();
                    break;
                case "exit":
                    // if there is an open connection, closes it and exits the java program.
                    // otherwise, simply exits the java program.
                    System.out.println("[Prompt] > Goodbye!");
                    if(sh.getConnState() == true) {
                        sh.closeConnection();
                        System.exit(0);
                    } else if (sh.getConnState() == false) {
                        System.exit(0);
                    }
            }
        }
    }

    // if there is an open connection already, returns an error message.
    // if there is no database connection, attempts establishing one.
    private void startConnection() {
        if(sh.getConnState() == false) {
            System.out.println("[Prompt] > Starting SQLHandler...\n");
            sh.startConnection();
        } else {
            System.err.println("[Prompt] > ERROR: Already connected to a database. If you want to connect to a new database, use option 5 to close the existing connection first.");
        }
    }

    // adds an entry to the database.
    // depending on the user input, it calls the appropiate method.
    private void addEntry() {
        if(sh.getConnState() == true) {
            // asks for the type of entry
            System.out.print("[Prompt] > What should be added? (artist / client / artwork / artwork group / cancel): ");
            opt2 = s.nextLine();
            System.out.println("");

            // calls the appropiate method depending on the user input
            if(opt2.equals("artist")) {
                System.out.println("[Prompt] > Adding new artist... ");
                sh.addArtist();
            } else if (opt2.equals("client")) {
                System.out.println("[Prompt] > Adding new client... ");
                sh.addClient();
            } else if (opt2.equals("artwork")) {
                System.out.println("[Prompt] > Adding new artwork... ");
                sh.addArtwork();
            } else if (opt2.equals("artwork group")) {
                System.out.println("[Prompt] > Adding new artwork group... ");
                sh.addArtworkGroup();
            } else if (opt2.equals("cancel")) {
                return;
            } else {
                System.out.println("[Prompt] > Invalid input.");
            }

        } else {
            // if there is no database connection, returns an error message.
            System.err.println("[Prompt] > ERROR: Not connected to a database. Use option 1 to establish a new connection.");
        }
    }

    // removes an entry from the database.
    // depending on the user input, it calls the appropiate method.
    private void removeEntry() {

        if(sh.getConnState() == true) {
            // asks for the type of entry
            System.out.print("[Prompt] > What should be removed? (artist / client / artwork / artwork group / cancel): ");
            opt2 = s.nextLine();
            System.out.println("");

            // calls the appropiate method depending on the user input
            if(opt2.equals("artist")) {
                System.out.println("[Prompt] > Removing an artist... ");
                sh.removeArtist();
            } else if (opt2.equals("client")) {
                sh.removeClient();
            } else if (opt2.equals("artwork")) {
                sh.removeArtwork();
            } else if (opt2.equals("artwork group")) {
                sh.removeArtworkGroup();
            } else if (opt2.equals("cancel")) {
                return;
            } else {
                System.out.println("[Prompt] > Invalid input.");
            }
        } else {
            // if there is no database connection, returns an error message.
            System.err.println("[Prompt] > ERROR: Not connected to a database. Use option 1 to establish a new connection.");
        }
    }

    // displays entries from the database
    private void displayEntries() {
        if(sh.getConnState() == true) {
            // asks for the type of entry
            System.out.print("[Prompt] > What should be displayed? (artists / clients / artworks / artwork groups / cancel): ");
            opt2 = s.nextLine();
            // calls the displayEntities method with the corresponding argument
            if (opt2.equals("artists")) {
                sh.displayEntries("artists");

            } else if (opt2.equals("clients")) {
                sh.displayEntries("clients");

            } else if (opt2.equals("artworks")) {
                sh.displayEntries("artworks");

            } else if (opt2.equals("artwork groups")){
                sh.displayEntries("artwork groups");

            } else if (opt2.equals("cancel")){
                return;
            }
        } else {
            // if there is no database connection, returns an error message.
            System.err.println("[Prompt] > ERROR: Not connected to a database. Use option 1 to establish a new connection.");
        }
    }

    // displays a help menu
    private void showHelp() {
        System.out.println("[Prompt] > Displaying all commands available: ");
        System.out.println("              | ==> open: opens a new database connection");
        System.out.println("              | ==> add: adds a new entry to the database");
        System.out.println("              | ==> remove: removes an entry from the database");
        System.out.println("              | ==> display: displays entries from the database");
        System.out.println("              | ==> close: closes connection to the database");
        System.out.println("              | ==> help: displays this help menu");
        System.out.println("              | ==> credits: displays the program's credits and license");
        System.out.println("              | ==> exit: closes the program");
    }

    // displays the program credits
    private void showCredits() {
        System.out.println("[Prompt] > Showing SQLHandler credits:");
        System.out.println("            | ==> v0.1-PRETESTING");
        System.out.println("            | ==> Made by Jorge Martinez (nothing4free)");
        System.out.println("            | ==> Published under the WTFPL (Do What The Fuck You Want Public License)");
        System.out.println("            | =======> more info: www.wtfpl.net");

    }

    // attempts to end the connection
    private void closeConnection() {
        // if there is an open connection, closes it
        if(sh.getConnState() == true) {
            System.out.println("[Prompt] > Closing connection to database. Goodbye!");
            sh.closeConnection();
        } else {
            // if there is no open connection, returns an error message
            System.err.println("[Prompt] > ERROR: Not connected to a database. Use option 1 to establish a new connection.");
        }
    }
}
