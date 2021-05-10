import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.Scanner;

public class SQLHandler {

    Scanner s = new Scanner(System.in);

    private String driver = "org.mariadb.jdbc.Driver";
    private String address;
    private String db;
    private String url;
    private String user;
    private String pwd;
    private boolean connState;
    private Connection cn = null;
    private Statement st = null;
    private PreparedStatement pst = null;

    public SQLHandler() {
        this.connState = false;
    }

    // attempts a new connection to a given database via user input
    public void startConnection() {
        // asks for user input
        System.out.println("[SQLHandler] > URL (format: IP:port): ");
        address = s.nextLine();
        System.out.println("[SQLHandler] > Database: ");
        db = s.nextLine();
        System.out.println("[SQLHandler] > User: ");
        user = s.nextLine();
        System.out.println("[SQLHandler] > Password: ");
        pwd = s.nextLine();
        // creates the url with the user input provided above
        url = "jdbc:mariadb://" + address + "/" + db;
        try {
            // driver registration and connection attempt
            Class.forName("org.mariadb.jdbc.Driver");
            System.out.println("[SQLHandler] > Attempting connection to the specified database...");
            cn = DriverManager.getConnection(url, user, pwd);
            connState = true;
            System.out.println("[SQLHandler] > Connection successful.");

        } catch (SQLException se) {
            // in case the connection fails, an error message and stack trace are displayed.
            System.err.println("[SQLHandler] > ERROR: Could not connect to the specified database. Details:");
            se.printStackTrace();
        } catch (Exception e) {
            // handles errors for Class.forname
            e.printStackTrace();
        }
    }

    // core method for the display function
    // depending on the option, it calls the proper display method.
    public void displayEntries(String entity) {
        String queryEntity;
        switch (entity) {
            case "artists":
                queryEntity = "Artista";
                System.out.println("[SQLHandler] > Showing all artists: ");
                break;
            case "clients":
                queryEntity = "Cliente";
                System.out.println("[SQLHandler] > Showing all clients: ");
                break;
            case "artworks":
                queryEntity = "ObraDeArte";
                System.out.println("[SQLHandler] > Showing all artworks: ");
                break;
            case "artwork groups":
                queryEntity = "GrupoDeObras";
                System.out.println("[SQLHandler] > Showing all artwork groups: ");
                break;
            default:
                System.out.println("[Prompt] > Invalid option.");
                return;
        }
        try {
            // query creation and execution
            st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + queryEntity);
            ResultSetMetaData meta = rs.getMetaData();
            // prints all data from the entries in the ResultSet
            int columns = meta.getColumnCount();
            while(rs.next()) {
                System.out.print(" >> ");
                for(int i = 1; i <= columns; i++) {
                    System.out.print(rs.getString(i) + " || ");
                }
                System.out.print("\n");
            }
            rs.beforeFirst();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    // adds a new client to the database
    public void addClient() {
        // asks for user input regarding the client
        System.out.println("[SQLHandler] > Adding new client to database.");
        System.out.println("[SQLHandler] > Client name: ");
        String clientName = s.nextLine();

        System.out.println("[SQLHandler] > Client address: ");
        String clientAddress = s.nextLine();

        System.out.println("[SQLHandler] > Client expenses (if any): ");
        int clientExpenses = s.nextInt();
        s.nextLine();

        try {
            // builds and executes the query
            pst = cn.prepareStatement("INSERT INTO " + db + ".Cliente(Nombre, Direccion, Gastos) VALUES (?, ?, ?)");
            pst.setString(1, clientName);
            pst.setString(2, clientAddress);
            pst.setInt(3, clientExpenses);
            pst.executeQuery();
            System.out.println("[SQLHandler] > Client " + clientName + " added successfully.");
        } catch (SQLException se) {
            System.err.println("[SQLHandler] > ERROR: Could not add new client to the database. Details:");
            se.printStackTrace();
        }
    }

    // removes a client from the database
    public void removeClient() {
        // asks for user input regarding the client
        System.out.println("[SQLHandler] > Removing client from database.");
        System.out.println("[SQLHandler] > Client name: ");
        String clientName = s.nextLine();

        try {
            // builds and executes the query
            pst = cn.prepareStatement("DELETE FROM Cliente WHERE Nombre = ?");
            pst.setString(1, clientName);
            pst.executeQuery();
            System.out.println("[SQLHandler] > Client " + clientName + " added successfully.");
        } catch (SQLException se) {
            System.err.println("[SQLHandler] > ERROR: Could not remove client from the database. Details:");
            se.printStackTrace();
        }
    }

    // adds a new artist to the database
    public void addArtist() {
        // asks for user input regarding the artist
        System.out.println("[SQLHandler] > Adding new artist to database.");
        System.out.println("[SQLHandler] > Artist name: ");
        String artistName = s.nextLine();

        System.out.println("[SQLHandler] > Artist birth place: ");
        String birthPlace = s.nextLine();

        System.out.println("[SQLHandler] > Artist birth year: ");
        int birthYear = s.nextInt();
        s.nextLine();

        System.out.println("[SQLHandler] > Artist style: ");
        String style = s.nextLine();

        try {
            // builds and executes the query
            pst = cn.prepareStatement("INSERT INTO " + db + ".Artista(Nombre, Lugar_Nacimiento, Year_Nacimiento, Estilo) VALUES (?, ?, ?, ?)");
            pst.setString(1, artistName);
            pst.setString(2, birthPlace);
            pst.setInt(3, birthYear);
            pst.setString(4, style);
            pst.executeQuery();
            System.out.println("[SQLHandler] > Artist " + artistName + " added successfully.");
        } catch (SQLException se) {
            System.err.println("[SQLHandler] > ERROR: Could not add new artist to the database. Details:");
            se.printStackTrace();
        }
    }

    // removes an artist from the database
    public void removeArtist() {
        // asks for user input regarding the artist
        System.out.println("[SQLHandler] > Removing artist from database.");
        System.out.println("[SQLHandler] > Artist name: ");
        String artistName = s.nextLine();

        try {
            // builds and executes the query
            pst = cn.prepareStatement("DELETE FROM Artista WHERE Nombre = ?");
            pst.setString(1, artistName);
            pst.executeQuery();
            System.out.println("[SQLHandler] > Artist " + artistName + " removed successfully.");
        } catch (SQLException se) {
            System.err.println("[SQLHandler] > ERROR: Could not remove artist from the database. Details:");
            se.printStackTrace();
        }
    }

    //operaciones con artwork
    public void addArtwork() {
        // asks for user input regarding the artwork
        System.out.println("[SQLHandler] > Adding new artwork to database.");
        System.out.println("[SQLHandler] > Artwork ID: ");
        int artworkID = s.nextInt();
        s.nextLine();

        System.out.println("[SQLHandler] > Artwork title: ");
        String artworkTitle = s.nextLine();

        System.out.println("[SQLHandler] > Artwork price: ");
        int artworkPrice = s.nextInt();
        s.nextLine();

        System.out.println("[SQLHandler] > Artwork type: ");
        String artworkType = s.nextLine();

        System.out.println("[SQLHandler] > Artwork date: ");
        int artworkDate = s.nextInt();
        s.nextLine();

        System.out.println("[SQLHandler] > Artwork creator: ");
        String artworkCreator = s.nextLine();

        try {
            // builds and executes the query
            pst = cn.prepareStatement("INSERT INTO " + db + ".ObraDeArte(ID_obra, Titulo, Precio, Tipo, Fecha, Artista) VALUES (?, ?, ?, ?, ?, ?)");
            pst.setInt(1, artworkID);
            pst.setString(2, artworkTitle);
            pst.setInt(3, artworkPrice);
            pst.setString(4, artworkType);
            pst.setInt(5, artworkDate);
            pst.setString(6, artworkCreator);
            pst.executeQuery();
            System.out.println("[SQLHandler] > Artwork " + artworkTitle + " [ID: " + artworkID + "]" + " added successfully.");
        } catch (SQLException se) {
            System.err.println("[SQLHandler] > ERROR: Could not add new artwork to the database. Details:");
            se.printStackTrace();
        }
    }

    // removes an artwork from the database
    public void removeArtwork() {
        // asks for user input regarding the artwork
        System.out.println("[SQLHandler] > Removing artist from database.");
        System.out.println("[SQLHandler] > Artwork ID: ");
        int artworkID = s.nextInt();
        s.nextLine();

        try {
            // builds and executes the query
            pst = cn.prepareStatement("DELETE FROM ObraDeArte WHERE ID_obra = ?");
            pst.setInt(1, artworkID);
            pst.executeQuery();
            System.out.println("[SQLHandler] > Artwork " + artworkID + " removed successfully.");
        } catch (SQLException se) {
            System.err.println("[SQLHandler] > ERROR: Could not remove artwork from the database. Details:");
            se.printStackTrace();
        }
    }

    // adds a new artwork group
    public void addArtworkGroup() {
        // asks for user input regarding the artwork group
        System.out.println("[SQLHandler] > Adding new artwork to database.");
        System.out.println("[SQLHandler] > Artwork ID: ");
        String groupName = s.nextLine();

        try {
            // builds and executes the query
            pst = cn.prepareStatement("INSERT INTO " + db + ".GrupoDeObras(Nombre) VALUES (?)");
            pst.setString(1, groupName);
            pst.executeQuery();
            System.out.println("[SQLHandler] > Art group " + groupName + " added successfully.");
        } catch (SQLException se) {
            System.err.println("[SQLHandler] > ERROR: Could not add new art group to the database. Details:");
            se.printStackTrace();
        }
    }

    // removes an artwork group from the database
    public void removeArtworkGroup() {
        // asks for user input regarding the artwork group
        System.out.println("[SQLHandler] > Removing artwork group from database.");
        System.out.println("[SQLHandler] > Artwork group ID: ");
        String name = s.nextLine();

        try {
            // builds and executes the query
            pst = cn.prepareStatement("DELETE FROM GrupoDeObras WHERE Nombre = ?");
            pst.setString(1, name);
            pst.executeQuery();
            System.out.println("[SQLHandler] > Artwork group " + name + " removed successfully.");
        } catch (SQLException se) {
            System.err.println("[SQLHandler] > ERROR: Could not remove artwork group from the database. Details:");
            se.printStackTrace();
        }
    }

    // ends connection to the database
    public void closeConnection() {
        try {
            if (st != null) {
                cn.close();
                connState = false;
            }
        } catch (SQLException se) {
            System.err.println("[SQLHandler] > Could not close connection. Details:");
            se.printStackTrace();
        }
        try {
            if (cn != null) {
                cn.close();
                connState = false;
            }
        } catch (SQLException se) {
            System.err.println("[SQLHandler] > Could not close connection. Details:");
            se.printStackTrace();
        }
    }

    // returns the connection state (true = open, false = closed)
    public boolean getConnState()
    {
        return connState;
    }
}
