package DB_Music;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class DBProj {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, InterruptedException {

        Class.forName("org.mariadb.jdbc.Driver").newInstance();
        String url = "jdbc:mariadb://localhost:3306/music";
        String username = "jjong";
        String password = "jjong";
        Connection con = DriverManager.getConnection(url,username,password);
        Statement stmt = con.createStatement();
        ResultSet rs;

        Scanner keyboard = new Scanner(System.in);
        int input;
        String MgrOrUser;
        String ID;
        String pass;
        String nickname;
        String fname;
        String lname;
        String sex;
        String birthDate;
        String preferGenre;

        int artistNum = 0;
        String artistFname;
        String artistLname;
        String artistDebutYear;
        String soloOrGroup;
        String artistSex;
        String artistGenre;

        int albumNum;
        String albumName;
        String albumPublishDate;
        int album_artistNum;
        String albumGenre;
        int howManyMusic;

        int musicNum;
        String musicTitle;
        String musicIsTitle;
        String musicGenre;
        String musicMood;
        String musicSit;
        String[] genres = {"POP", "Rock", "Ballad", "Hip-Hop", "R&B"};
        String[] moods = {"Exciting", "Calm", "Groovy", "Trendy", "Dreamlike"};
        String[] situations = {"Driving", "Studying", "Travel", "Party", "Cafe"};

        while(true){

            System.out.println();
            System.out.println("0. EXIT");
            System.out.println("1. Register");
            System.out.println("2. Manager LogIn");
            System.out.println("3. User LogIn");
            System.out.println();
            System.out.print("Input: ");
            input = keyboard.nextInt();
            keyboard.nextLine();

            if(input == 0){ // EXIT; Program finish
                break;
            }
            else if(input == 1){ // Register
                System.out.println();
                System.out.print("Manager or User?(M or U): ");
                MgrOrUser = keyboard.nextLine();

                if(MgrOrUser.equals("M") || MgrOrUser.equals("m")){ //Manager Register

                    while(true) {
                        System.out.print("ID: ");
                        ID = keyboard.nextLine();
                        rs = stmt.executeQuery("SELECT ID FROM Manager WHERE ID = " + "'" + ID + "'");
                        if (rs.next()) {
                            System.out.println("This ID already exists.");
                            continue;
                        }
                        break;
                    }
                    System.out.print("password: ");
                    pass = keyboard.nextLine();
                    while(true) {
                        System.out.print("nickname: ");
                        nickname = keyboard.nextLine();
                        rs = stmt.executeQuery("SELECT nickname FROM Manager WHERE nickname = " + "'" + nickname + "'");
                        if(rs.next()){
                            System.out.println("This nickname already exists.");
                            continue;
                        }
                        break;
                    }
                    System.out.print("first name: ");
                    fname = keyboard.nextLine();
                    System.out.print("last name: ");
                    lname = keyboard.nextLine();

                    stmt.executeUpdate("INSERT INTO Manager VALUES (" +"'"+ nickname + "' ," + "'" + ID
                            + "'," + "'" + pass + "'," + "'" + fname + "'," +"'" +  lname + "')");

                    System.out.println("Register complete!");
                }
                else if(MgrOrUser.equals("U") || MgrOrUser.equals("u")){ // User Register
                    while(true) {
                        System.out.print("ID: ");
                        ID = keyboard.nextLine();
                        rs = stmt.executeQuery("SELECT ID FROM Subscriber WHERE ID = " + "'" + ID + "'");
                        if (rs.next()) {
                            System.out.println("This ID already exists.");
                            continue;
                        }
                        break;
                    }
                    System.out.print("password: ");
                    pass = keyboard.nextLine();
                    while(true) {
                        System.out.print("nickname: ");
                        nickname = keyboard.nextLine();
                        rs = stmt.executeQuery("SELECT nickname FROM Subscriber WHERE nickname = " + "'" + nickname + "'");
                        if(rs.next()){
                            System.out.println("This nickname already exists.");
                            continue;
                        }
                        break;
                    }
                    System.out.print("first name: ");
                    fname = keyboard.nextLine();
                    System.out.print("last name: ");
                    lname = keyboard.nextLine();
                    System.out.print("sex(M or F): ");
                    sex = keyboard.nextLine();
                    System.out.print("birth date(XXXX-XX-XX): ");
                    birthDate = keyboard.nextLine();

                    stmt.executeUpdate("INSERT INTO Subscriber VALUES ("+"'"+ nickname + "','"  + ID + "','" + pass + "','"
                            + fname + "','" +  lname + "','" + sex + "','" + birthDate + "','" + "jjong" + "')");

                    System.out.println("What are your preferred genres?(Separate with commas)");
                    System.out.println("1. POP");
                    System.out.println("2. Rock");
                    System.out.println("3. Ballad");
                    System.out.println("4. Hip-Hop");
                    System.out.println("5. R&B");
                    System.out.println();
                    System.out.print("Input: ");
                    preferGenre = keyboard.nextLine();

                    String[] preferGenres = preferGenre.split(",");
                    for(int i = 0; i < preferGenres.length; i++){
                        stmt.executeUpdate("INSERT INTO Subscriber_genre VALUES (" + "'"
                                + genres[Integer.parseInt(preferGenres[i])-1] + "','" + nickname + "')");
                    }

                    rs = stmt.executeQuery("SELECT m.mnum FROM Music m");
                    while(rs.next()){
                        int n = rs.getInt(1);
                        stmt.executeUpdate("INSERT INTO Plays VALUES (" + n + ", " + "'" + nickname + "'," + 0 + ")");
                    }
                    System.out.println("Register complete!");
                }

            }
            else if(input == 2){ // Manager Login
                System.out.print("ID: ");
                ID = keyboard.nextLine();
                System.out.print("password: ");
                pass = keyboard.nextLine();
                rs = stmt.executeQuery("SELECT ID, password FROM Manager WHERE ID = " + "'" + ID + "'");

                String mgrID = null;
                String mgrPassword = null;
                if(rs.next()) {
                    mgrID = rs.getString(1);
                    mgrPassword = rs.getString(2);
                }
                if(!ID.equals(mgrID) || !pass.equals(mgrPassword)){
                    System.out.println("Your ID or password is wrong.");
                    continue;
                }
                rs = stmt.executeQuery("SELECT nickname FROM Manager WHERE ID = " + "'" + ID + "'");
                String mgr_nickname = null;
                if(rs.next())mgr_nickname = rs.getString(1);

                while(true) {

                    int managerInput;

                    System.out.println();
                    System.out.println("0. Return to previous menu");
                    System.out.println("1. Music management");
                    System.out.println("2. User management");
                    System.out.print("Input: ");
                    managerInput = keyboard.nextInt();
                    keyboard.nextLine();

                    if (managerInput == 0) break;

                    else if (managerInput == 1) { // Music management
                        System.out.println();
                        System.out.println("1. Add artist");
                        System.out.println("2. Add music");
                        System.out.println("3. Delete music");
                        System.out.print("Input: ");
                        managerInput = keyboard.nextInt();
                        keyboard.nextLine();

                        if (managerInput == 1) { // Add artist
                            System.out.println();
                            while(true) {
                                System.out.print("Artist num: ");
                                artistNum = keyboard.nextInt();
                                rs = stmt.executeQuery("SELECT * FROM Artist WHERE artist_num = " + artistNum);
                                if(rs.next()){
                                    System.out.println("This artist num already exists.");
                                    continue;
                                }
                                break;
                            }
                            keyboard.nextLine();
                            System.out.print("Artist first name: ");
                            artistFname = keyboard.nextLine();
                            System.out.print("Artist last name: ");
                            artistLname = keyboard.nextLine();
                            System.out.print("Debut year: ");
                            artistDebutYear = keyboard.nextLine();
                            System.out.print("Solo or Group?: ");
                            soloOrGroup = keyboard.nextLine();
                            System.out.print("Sex(M or F): ");
                            artistSex = keyboard.nextLine();

                            stmt.executeUpdate("INSERT INTO Artist VALUES (" + "'" + artistNum + "','" + artistFname + "','"
                                    + artistLname + "','" + artistDebutYear + "','" + soloOrGroup + "','" + artistSex + "')");

                            System.out.println("What are the artist genres?(Separate with commas)");
                            System.out.println("1. POP");
                            System.out.println("2. Rock");
                            System.out.println("3. Ballad");
                            System.out.println("4. Hip-Hop");
                            System.out.println("5. R&B");
                            System.out.println();
                            System.out.print("Input: ");
                            artistGenre = keyboard.nextLine();

                            String[] artistGenres = artistGenre.split(",");
                            for (int i = 0; i < artistGenres.length; i++) {
                                stmt.executeUpdate("INSERT INTO Artist_genre VALUES (" + "'"
                                        + genres[Integer.parseInt(artistGenres[i]) - 1] + "','" + artistNum + "')");
                            }
                        }
                        else if (managerInput == 2) { //Add music
                            System.out.println();
                            System.out.println("Add music by including it in album. Please input album info first.");
                            System.out.println();
                            while(true) {
                                System.out.print("Album number: ");
                                albumNum = keyboard.nextInt();
                                rs = stmt.executeQuery("SELECT * FROM Album WHERE album_num = " + albumNum);
                                if(rs.next()){
                                    System.out.println("This album num already exists.");
                                    continue;
                                }
                                break;
                            }
                            keyboard.nextLine();
                            System.out.print("Album name: ");
                            albumName = keyboard.nextLine();
                            System.out.print("Publish date(XXXX-XX-XX): ");
                            albumPublishDate = keyboard.nextLine();
                            while(true) {
                                System.out.print("Artist number of this album: ");
                                album_artistNum = keyboard.nextInt();
                                keyboard.nextLine();

                                rs = stmt.executeQuery("SELECT * FROM Artist WHERE artist_num = " + album_artistNum);
                                if(!rs.next()){
                                    System.out.println("Such artist num doesn't exist.");
                                    continue;
                                }
                                break;
                            }

                            stmt.executeUpdate("INSERT INTO Album VALUES (" + "'" + albumNum + "','" + albumName
                                    + "','" + albumPublishDate + "','" + album_artistNum + "')");

                            stmt.executeUpdate("INSERT INTO Releases Values (" + albumNum + "," + album_artistNum + ")");

                            System.out.println("What are the album genres?(Separate with commas)");
                            System.out.println("1. POP");
                            System.out.println("2. Rock");
                            System.out.println("3. Ballad");
                            System.out.println("4. Hip-Hop");
                            System.out.println("5. R&B");
                            System.out.println();
                            System.out.print("Input: ");
                            albumGenre = keyboard.nextLine();

                            String[] albumGenres = albumGenre.split(",");
                            for (int i = 0; i < albumGenres.length; i++) {
                                stmt.executeUpdate("INSERT INTO Album_genre VALUES (" + "'"
                                        + genres[Integer.parseInt(albumGenres[i]) - 1] + "','" + albumNum + "')");
                            }

                            System.out.println();
                            System.out.println("Now add music into the album.");
                            System.out.print("How many songs in this album?: ");
                            howManyMusic = keyboard.nextInt();
                            keyboard.nextLine();

                            for(int i = 0; i < howManyMusic; i++){
                                int trackNum = i+1;
                                System.out.println();
                                while(true) {
                                    System.out.print("Music number of track " + trackNum + ": ");
                                    musicNum = keyboard.nextInt();
                                    keyboard.nextLine();
                                    rs = stmt.executeQuery("SELECT * FROM Music WHERE mnum = " + musicNum);
                                    if(rs.next()){
                                        System.out.println("This music num already exists.");
                                        continue;
                                    }
                                    break;
                                }
                                System.out.print("Title of music track " + trackNum + ": ");
                                musicTitle = keyboard.nextLine();
                                System.out.print("Is this song a title song?(Y or N): ");
                                musicIsTitle = keyboard.nextLine();

                                stmt.executeUpdate("INSERT INTO Music VALUES (" + "'" + musicNum + "','"
                                        + musicTitle + "','" + 0 + "','" + mgr_nickname + "')");

                                stmt.executeUpdate("INSERT INTO Contains VALUES (" + "'" + musicNum + "','"
                                        + albumNum + "','" + trackNum + "','" + musicIsTitle + "')");

                                stmt.executeUpdate("INSERT INTO Makes VALUES (" + "'"
                                        + musicNum + "','" + album_artistNum + "')");

                                System.out.println("What are the music genres?(Separate with commas)");
                                System.out.println("1. POP");
                                System.out.println("2. Rock");
                                System.out.println("3. Ballad");
                                System.out.println("4. Hip-Hop");
                                System.out.println("5. R&B");
                                System.out.println();
                                System.out.print("Input: ");
                                musicGenre = keyboard.nextLine();

                                String[] musicGenres = musicGenre.split(",");
                                for (int j = 0; j < musicGenres.length; j++) {
                                    stmt.executeUpdate("INSERT INTO Music_genre VALUES (" + "'"
                                            + genres[Integer.parseInt(musicGenres[j]) - 1] + "','" + musicNum + "')");
                                }

                                System.out.println("What are the music moods?(Separate with commas)");
                                System.out.println("1. Exciting");
                                System.out.println("2. Calm");
                                System.out.println("3. Groovy");
                                System.out.println("4. Trendy");
                                System.out.println("5. Dreamlike");
                                System.out.println();
                                System.out.print("Input: ");
                                musicMood = keyboard.nextLine();

                                String[] musicMoods = musicMood.split(",");
                                for (int j = 0; j < musicMoods.length; j++) {
                                    stmt.executeUpdate("INSERT INTO Music_mood VALUES (" + "'"
                                            + moods[Integer.parseInt(musicMoods[j]) - 1] + "','" + musicNum + "')");
                                }

                                System.out.println("What are the music situations?(Separate with commas)");
                                System.out.println("1. Driving");
                                System.out.println("2. Studying");
                                System.out.println("3. Travel");
                                System.out.println("4. Party");
                                System.out.println("5. Cafe");
                                System.out.println();
                                System.out.print("Input: ");
                                musicSit = keyboard.nextLine();

                                String[] musicSits = musicSit.split(",");
                                for (int j = 0; j < musicSits.length; j++) {
                                    stmt.executeUpdate("INSERT INTO Music_sit VALUES (" + "'"
                                            + situations[Integer.parseInt(musicSits[j]) - 1] + "','" + musicNum + "')");
                                }

                                rs = stmt.executeQuery("SELECT s.nickname FROM Subscriber s");
                                while(rs.next()){
                                    String userNickname = rs.getString(1);
                                    stmt.executeUpdate("INSERT INTO Plays VALUES ( "
                                            + musicNum + ", " + "'" + userNickname + "', " + 0 + ")");
                                }
                            }
                            System.out.println("Add complete!");
                        }
                        else { // Delete music
                            int deletenum;

                            System.out.println();
                            rs = stmt.executeQuery("SELECT * FROM Music");
                            while(rs.next()){
                                int mnum = rs.getInt(1);
                                String title = rs.getString(2);
                                int playnum = rs.getInt(3);
                                String manager_nickname = rs.getString(4);
                                System.out.println("No." + mnum + "/ Title: " + title
                                        + "/ Play count: " + playnum + "/ Manager nickname: " + manager_nickname);
                            }
                            System.out.println();
                            System.out.print("Choose song number to delete: ");
                            deletenum = keyboard.nextInt();
                            keyboard.nextLine();

                            stmt.executeUpdate("DELETE FROM Music WHERE mnum = " + deletenum);

                            System.out.println("Delete complete!");
                        }
                    }
                    else if (managerInput == 2) { // User management
                        System.out.println();
                        rs = stmt.executeQuery("SELECT * FROM Subscriber");
                        while(rs.next()){
                            String user_nickname = rs.getString(1);
                            String user_ID = rs.getString(2);
                            String user_pass = rs.getString(3);
                            String user_fname = rs.getString(4);
                            String user_lname = rs.getString(5);
                            String user_sex = rs.getString(6);
                            Date user_date = rs.getDate(7);
                            String manager_nickname = rs.getString(8);
                            System.out.println("Nickname: " + user_nickname + "/ ID: " + user_ID + "/ Password: " + user_pass +"/ First name: " + user_fname + "/ Last name: "
                                    + user_lname + "/ Sex: " + user_sex + "/ Birth date: " + user_date + "/ Manager nickname: " + manager_nickname);
                        }

                        System.out.println();
                        System.out.println("0. Return to previous menu");
                        System.out.println("1. Delete user");
                        System.out.println("2. Update user info");
                        System.out.print("Input: ");
                        managerInput = keyboard.nextInt();
                        keyboard.nextLine();

                        if(managerInput == 0); // Return to manager menu

                        else if(managerInput == 1){ // Delete user
                            boolean userExists = false;
                            String user_nickname = null;

                            while(!userExists) {
                                System.out.print("Choose user nickname to delete: ");
                                user_nickname = keyboard.nextLine();
                                rs = stmt.executeQuery("SELECT nickname FROM Subscriber");

                                if (rs.next()) userExists = true;
                                if (!userExists) System.out.println("Such nickname doesn't exist.");
                            }
                            stmt.executeUpdate("DELETE FROM Subscriber WHERE nickname = " + "'" + user_nickname + "'");
                            System.out.println("Delete Complete!");
                        }
                        else { // Update user info
                            String[] attributes = {"nickname", "ID", "password", "fName", "lName", "sex", "bDate", "mgr_nickname"};
                            String update;
                            boolean userExists = false;
                            String user_nickname = null;

                            while(!userExists) {
                                System.out.print("Choose user nickname to update:");
                                user_nickname = keyboard.nextLine();
                                rs = stmt.executeQuery("SELECT nickname FROM Subscriber WHERE nickname = " + "'" + user_nickname + "'");

                                if(rs.next())userExists = true;
                                if(!userExists) System.out.println("Such nickname doesn't exist.");
                            }
                            System.out.println("1. Nickname");
                            System.out.println("2. ID");
                            System.out.println("3. Password");
                            System.out.println("4. First name");
                            System.out.println("5. Last name");
                            System.out.println("6. Sex");
                            System.out.println("7. Birth date");
                            System.out.println("8. Manager nickname");
                            System.out.print("Choose information to update(Separate with commas): ");

                            update = keyboard.nextLine();
                            String[] updates = update.split(",");

                            System.out.println();

                            for(int i = 0; i < updates.length; i++){

                                System.out.print(attributes[Integer.parseInt(updates[i])-1] + ": ");
                                String newValue = keyboard.nextLine();

                                stmt.executeUpdate("UPDATE Subscriber SET " + attributes[Integer.parseInt(updates[i]) - 1]
                                        + "= '" + newValue + "' WHERE nickname = '" + user_nickname + "'" );
                            }
                            System.out.println("Update complete!");
                        }
                    }
                }
            }
            else { // User Login
                System.out.print("ID: ");
                ID = keyboard.nextLine();
                System.out.print("password: ");
                pass = keyboard.nextLine();
                rs = stmt.executeQuery("SELECT ID, password FROM Subscriber WHERE ID = " + "'" + ID + "'");

                String userID = null;
                String userPassword = null;
                if(rs.next()){
                    userID = rs.getString(1);
                    userPassword = rs.getString(2);
                }
                if(!ID.equals(userID) || !pass.equals(userPassword)){
                    System.out.println("Your ID or password is wrong.");
                    continue;
                }

                rs = stmt.executeQuery("SELECT nickname FROM Subscriber WHERE ID = " + "'" + ID + "'");
                String user_nickname = null;
                if(rs.next()) user_nickname = rs.getString(1);

                while(true){
                    int userInput;

                    System.out.println();
                    System.out.println("0. Return to previous menu");
                    System.out.println("1. Search music");
                    System.out.println("2. Make Playlist");
                    System.out.println("3. Statistics");
                    System.out.println("4. Music recommendation");
                    System.out.print("Input: ");
                    userInput = keyboard.nextInt();
                    keyboard.nextLine();

                    if(userInput == 0) break; // Return to first menu

                    else if(userInput == 1){ // Search music

                        System.out.println();
                        System.out.println("1. Album search");
                        System.out.println("2. Title search");
                        System.out.println("3. Artist search");
                        System.out.print("Input: ");
                        userInput = keyboard.nextInt();
                        keyboard.nextLine();

                        if(userInput == 1){ // Album search
                            System.out.println();
                            System.out.print("Album title: ");
                            String searchAlbumTitle = keyboard.nextLine();
                            rs = stmt.executeQuery("SELECT * FROM Album WHERE album_name = " + "'" + searchAlbumTitle + "'");
                            if(!rs.next()){
                                System.out.println("Such album doesn't exist.");
                                continue;
                            }

                            HashSet<Integer> similarAlbums = new HashSet<>();
                            int[] songsInAlbum = new int[100];
                            int numOfSongs = 0;

                            rs = stmt.executeQuery("SELECT ag.genre FROM Album al, Album_genre ag " +
                                    "WHERE al.album_num = ag.albumnum AND al.album_name = " + "'" + searchAlbumTitle + "'");

                            while(rs.next()){
                                String similarGenre = rs.getString(1);
                                ResultSet rs2 = stmt.executeQuery("SELECT al.album_num FROM Album al, Album_genre ag " +
                                        "WHERE al.album_num = ag.albumnum AND al.album_name != " + "'"
                                        + searchAlbumTitle + "'" + " AND ag.genre = " + "'" + similarGenre +"'");
                                while(rs2.next()){
                                    similarAlbums.add(rs2.getInt(1));
                                }
                            }

                            rs = stmt.executeQuery("SELECT al.album_name, ar.fName, ar.lName, mu.title, mu.mnum " +
                                    "FROM Album al, Artist ar, Music mu, Contains co, Releases re " +
                                    "WHERE co.music_num = mu.mnum AND co.album_num = al.album_num " +
                                    "AND re.album_num = al.album_num AND re.artist_num = ar.artist_num " +
                                    "AND al.album_name = " + "'" + searchAlbumTitle + "'");

                            System.out.println();
                            System.out.println("Album " + searchAlbumTitle);
                            while(rs.next()){
                                String searchFName = rs.getString(2);
                                String searchLName = rs.getString(3);
                                String searchTitle = rs.getString(4);
                                songsInAlbum[numOfSongs++] = rs.getInt(5);
                                if(searchLName == null) System.out.println(searchFName + " - " + searchTitle);
                                else System.out.println(searchFName + " " + searchLName + " - " + searchTitle);
                            }
                            System.out.println();
                            System.out.println("Similar albums");

                            int k = 0;
                            Iterator<Integer> it = similarAlbums.iterator();
                            while(it.hasNext()){
                                rs = stmt.executeQuery("SELECT al.album_name, ar.fName, ar.lName FROM Album al, Releases re, Artist ar " +
                                        "WHERE re.album_num = al.album_num AND re.artist_num = ar.artist_num AND al.album_num = " + it.next());
                                while(rs.next()){
                                    k++;
                                    String simAlbumName = rs.getString(1);
                                    String simArtistFName = rs.getString(2);
                                    String simArtistLName = rs.getString(3);
                                    if(simArtistLName == null ) System.out.println(k + ". Album name: " + simAlbumName + " / Artist name: " + simArtistFName);
                                    else System.out.println(k + ". Album name: " + simAlbumName + " / Artist name: " + simArtistFName + " " + simArtistLName);
                                }
                            }

                            System.out.print("Add playlist?(Y or N): ");
                            String addPlaylist = keyboard.nextLine();
                            if(addPlaylist.equals("Y")){
                                int listNumber;
                                String[] listNames = new String[100];
                                k = 0;

                                rs = stmt.executeQuery("SELECT p.list_name FROM Playlist p " +
                                        "WHERE p.sub_nickname = " + "'" + user_nickname + "'");
                                while(rs.next()){
                                    k++;
                                    listNames[k] = rs.getString(1);
                                    System.out.println(k + ". " + listNames[k]);
                                }
                                if(k == 0){
                                    System.out.println("You don't have any playlist. Create it first.");
                                    continue;
                                }
                                while(true) {
                                    System.out.println();
                                    System.out.print("Which playlist?: ");
                                    listNumber = keyboard.nextInt();
                                    if(listNumber > k){
                                        System.out.println("Choose again.");
                                        continue;
                                    }
                                    break;
                                }
                                for(int i = 0; i < numOfSongs; i++){
                                    rs = stmt.executeQuery("SELECT co.music_num FROM Consist_of co " +
                                            "WHERE co.music_num = " + songsInAlbum[i] + " AND co.nickname = " + "'" + user_nickname + "' " +
                                            "AND co.playlist_name = " + "'" + listNames[listNumber] + "'");
                                    if(!rs.next()) {
                                        int maxOrder = 0;
                                        rs = stmt.executeQuery("SELECT MAX(co.music_order) FROM Consist_of co " +
                                                "WHERE co.nickname = " + "'" + user_nickname + "' "
                                                + "AND co.playlist_name = " + "'" + listNames[listNumber] + "'");

                                        while (rs.next()) maxOrder = rs.getInt(1);
                                        stmt.executeUpdate("INSERT INTO Consist_of VALUES " +
                                                "(" + songsInAlbum[i] + ", " + "'" + user_nickname + "', "
                                                + "'" + listNames[listNumber] + "', " + (maxOrder + 1) + ")");

                                        rs = stmt.executeQuery("SELECT COUNT(*) FROM Consist_of WHERE nickname = " + "'" + user_nickname + "'"
                                                + " AND playlist_name = " + "'" + listNames[listNumber] + "'");
                                        int numOfMusic = 0;
                                        while(rs.next()){
                                            numOfMusic = rs.getInt(1);
                                        }

                                        stmt.executeUpdate("UPDATE Playlist SET music_num = " + numOfMusic
                                                + " WHERE sub_nickname = " + "'" + user_nickname + "'" + " AND list_name = " + "'" + listNames[listNumber] + "'");
                                    }
                                }
                                System.out.println("Add complete except for duplicated songs.");
                            }
                        }

                        else if(userInput == 2){ // Title search
                            System.out.println();
                            System.out.print("Music title: ");
                            String searchMusicTitle = keyboard.nextLine();

                            rs = stmt.executeQuery("SELECT * FROM Music WHERE title = " + "'" + searchMusicTitle + "'");
                            if(!rs.next()){
                                System.out.println("Such song doesn't exist");
                                continue;
                            }

                            HashSet<Integer> similarMusics = new HashSet<>();
                            int mnum = 0;

                            rs = stmt.executeQuery("SELECT mg.genre FROM Music mu, Music_genre mg " +
                                    "WHERE mu.mnum = mg.g_mnum AND mu.title = " + "'" + searchMusicTitle + "'");

                            while(rs.next()){
                                String similarGenre = rs.getString(1);
                                ResultSet rs2 = stmt.executeQuery("SELECT mu.mnum " +
                                        "FROM Music mu, Music_genre mg " +
                                        "WHERE mu.mnum = mg.g_mnum AND mu.title != " + "'" + searchMusicTitle + "'"
                                        + " AND mg.genre = " + "'" + similarGenre +"'");
                                while(rs2.next()){
                                    similarMusics.add(rs2.getInt(1));
                                }
                            }

                            rs = stmt.executeQuery("SELECT mu.title, ar.fName, ar.lName, p.playnum, mu.mnum " +
                                    "FROM Music mu, Artist ar, Makes m, Subscriber su, Plays p " +
                                    "WHERE m.music_num = mu.mnum AND m.artist_num = ar.artist_num " +
                                    "AND su.nickname = p.nickname AND p.music_num = mu.mnum " +
                                    "AND su.nickname = " + "'"+ user_nickname + "' " +
                                    "AND mu.title = " + "'" + searchMusicTitle + "'");

                            System.out.println();
                            while(rs.next()){
                                String searchTitle = rs.getString(1);
                                String searchFName = rs.getString(2);
                                String searchLName = rs.getString(3);
                                int searchPlaynum = rs.getInt(4);
                                mnum = rs.getInt(5);

                                if(searchLName == null) System.out.println(searchFName + " - " + searchTitle + " / Your play count: " + searchPlaynum);
                                else System.out.println(searchFName + " " + searchLName + " - " + searchTitle + " / Your play count: " + searchPlaynum);
                            }
                            System.out.println();
                            System.out.println("Similar songs");

                            int k = 0;
                            Iterator<Integer> it = similarMusics.iterator();
                            while(it.hasNext()){
                                rs = stmt.executeQuery("SELECT mu.title, ar.fName, ar.lName " +
                                        "FROM Music mu, Makes m, Artist ar " +
                                        "WHERE m.music_num = mu.mnum AND m.artist_num = ar.artist_num AND mu.mnum = " + it.next());
                                while(rs.next()){
                                    k++;
                                    String simMusicTitle = rs.getString(1);
                                    String simArtistFName = rs.getString(2);
                                    String simArtistLName = rs.getString(3);
                                    if(simArtistLName == null ) System.out.println(k + ". " + simMusicTitle + " - " + simArtistFName);
                                    else System.out.println(k + ". " + simMusicTitle + " - " + simArtistFName + " " + simArtistLName);
                                }
                            }

                            System.out.println();
                            System.out.println("0. Return to previous menu");
                            System.out.println("1. Play");
                            System.out.println("2. Add playlist");
                            System.out.print("Input: ");
                            userInput = keyboard.nextInt();
                            System.out.println();

                            if(userInput == 0);

                            else if(userInput == 1){
                                for(int i = 0; i < 5; i++) {
                                    System.out.print("#");
                                    Thread.sleep(500);
                                }

                                int playnum = 0;
                                rs = stmt.executeQuery("SELECT p.playnum FROM Plays p WHERE p.music_num = " + mnum +
                                        " AND nickname = " + "'" + user_nickname + "'");
                                while(rs.next()){
                                    playnum = rs.getInt(1);
                                }
                                stmt.executeUpdate("UPDATE Plays SET playnum = " + (playnum + 1) +
                                        " WHERE music_num = " + mnum + " AND nickname = " + "'" + user_nickname + "'");

                                rs = stmt.executeQuery("SELECT mu.playnum FROM Plays p, Music mu " +
                                        "WHERE p.music_num = mu.mnum AND mu.mnum = " + mnum + " AND p.nickname = " + "'" + user_nickname + "'");
                                while(rs.next()){
                                    playnum = rs.getInt(1);
                                }
                                stmt.executeUpdate("UPDATE Music SET playnum = " + (playnum + 1) +
                                        " WHERE mnum = " + mnum);

                                System.out.println();
                                System.out.println("Complete!");
                            }
                            else{
                                int listNumber;
                                String[] listNames = new String[100];
                                k = 0;

                                rs = stmt.executeQuery("SELECT p.list_name FROM Playlist p " +
                                        "WHERE p.sub_nickname = " + "'" + user_nickname + "'");
                                while(rs.next()){
                                    k++;
                                    listNames[k] = rs.getString(1);
                                    System.out.println(k + ". " + listNames[k]);
                                }
                                if(k == 0){
                                    System.out.println("You don't have any playlist. Create it first.");
                                    continue;
                                }
                                while(true) {
                                    System.out.println();
                                    System.out.print("Which playlist?: ");
                                    listNumber = keyboard.nextInt();
                                    if(listNumber > k){
                                        System.out.println("Choose again.");
                                        continue;
                                    }
                                    break;
                                }

                                rs = stmt.executeQuery("SELECT co.music_num FROM Consist_of co " +
                                        "WHERE co.music_num = " + mnum + " AND co.nickname = " + "'" + user_nickname + "' " +
                                        "AND co.playlist_name = " + "'" + listNames[listNumber] + "'");

                                if(rs.next()){
                                    System.out.println("This song already exists in your playlist " + listNames[listNumber]);
                                }
                                else {
                                    int maxOrder = 0;
                                    rs = stmt.executeQuery("SELECT MAX(co.music_order) FROM Consist_of co " +
                                            "WHERE co.nickname = " + "'" + user_nickname + "' "
                                            + "AND co.playlist_name = " + "'" + listNames[listNumber] + "'");

                                    while (rs.next()) {
                                        maxOrder = rs.getInt(1);
                                    }
                                    stmt.executeUpdate("INSERT INTO Consist_of VALUES (" + mnum + ", " + "'" + user_nickname + "', "
                                            + "'" + listNames[listNumber] + "', " + (maxOrder + 1) + ")");

                                    rs = stmt.executeQuery("SELECT COUNT(*) FROM Consist_of WHERE nickname = " + "'" + user_nickname + "'"
                                            + " AND playlist_name = " + "'" + listNames[listNumber] + "'");
                                    int numOfMusic = 0;
                                    while(rs.next()){
                                        numOfMusic = rs.getInt(1);
                                    }

                                    stmt.executeUpdate("UPDATE Playlist SET music_num = " + numOfMusic
                                            + " WHERE sub_nickname = " + "'" + user_nickname + "'" + " AND list_name = " + "'" + listNames[listNumber] + "'");

                                    System.out.println("Add complete!");
                                }
                            }
                        }
                        else{ // Artist search
                            System.out.println();
                            System.out.print("Artist name: ");
                            String searchArtistName = keyboard.nextLine();

                            HashSet<Integer> similarArtists = new HashSet<>();

                            String[] temp = searchArtistName.split(" ");
                            String searchFName;
                            String searchLName = null;

                            searchFName = temp[0];

                            if(temp.length == 1){
                                rs = stmt.executeQuery("SELECT ag.genre FROM Artist ar, Artist_genre ag " +
                                        "WHERE ar.artist_num = ag.artistnum AND ar.fName = " + "'" + searchFName + "' UNION " +
                                        "SELECT ag.genre FROM Artist ar, Artist_genre ag " +
                                        "WHERE ar.artist_num = ag.artistnum AND ar.lName = " + "'" + searchFName + "'" );
                            }
                            else{
                                searchLName = temp[1];
                                rs = stmt.executeQuery("SELECT ag.genre FROM Artist ar, Artist_genre ag " +
                                        "WHERE ar.artist_num = ag.artistnum AND ar.fName = " + "'" + searchFName + "'"
                                        + " AND ar.lName = " + "'" + searchLName + "'");
                            }

                            if(!rs.next()){
                                System.out.println("Such artist doesn't exist.");
                                continue;
                            }

                            while(rs.next()){
                                String similarGenre = rs.getString(1);
                                ResultSet rs2 = stmt.executeQuery("SELECT ar.artist_num FROM Artist ar, Artist_genre ag " +
                                        "WHERE ar.artist_num = ag.artistnum AND ar.fName != " + "'" + searchFName
                                        + "'" + " AND ag.genre = " + "'" + similarGenre +"'");
                                while(rs2.next()){
                                    similarArtists.add(rs2.getInt(1));
                                }
                            }

                            if(temp.length == 1) {
                                rs = stmt.executeQuery("(SELECT ar.fName, ar.lName, ar.debut_yr, ar.solo_group, ar.sex, al.album_name " +
                                        "FROM Artist ar, Releases re, Album al " +
                                        "WHERE re.artist_num = ar.artist_num AND re.album_num = al.album_num " +
                                        "AND ar.fName = " + "'" + searchFName + "' " +
                                        ")UNION (" +
                                        "SELECT ar.fName, ar.lName, ar.debut_yr, ar.solo_group, ar.sex, al.album_name " +
                                        "FROM Artist ar, Releases re, Album al " +
                                        "WHERE re.artist_num = ar.artist_num AND re.album_num = al.album_num " +
                                        "AND ar.lName = " + "'" + searchFName + "')");
                            }
                            else{
                                rs = stmt.executeQuery("SELECT ar.fName, ar.lName, ar.debut_yr, ar.solo_group, ar.sex, al.album_name " +
                                        "FROM Artist ar, Releases re, Album al " +
                                        "WHERE re.artist_num = ar.artist_num AND re.album_num = al.album_num " +
                                        "AND ar.fName = " + "'" + searchFName + "' " + "AND ar.lName = " + "'" + searchLName + "'");
                            }

                            System.out.println();
                            while(rs.next()){
                                searchFName = rs.getString(1);
                                searchLName = rs.getString(2);
                                int searchDebutYr = rs.getInt(3);
                                String soloGroup = rs.getString(4);
                                String searchSex = rs.getString(5);
                                String searchAlbum = rs.getString(6);

                                if(searchLName == null) System.out.println(searchFName + " - Debut year: " + searchDebutYr
                                        + " / " + soloGroup + " / " + searchSex + " / Released album: " + searchAlbum);

                                else System.out.println(searchFName + " " + searchLName + " - Debut year: " + searchDebutYr
                                        + " / " + soloGroup + " / " + searchSex + " / Released album: " + searchAlbum);
                            }
                            System.out.println();
                            System.out.println("Similar artists");

                            int k = 0;
                            Iterator<Integer> it = similarArtists.iterator();
                            while(it.hasNext()){
                                rs = stmt.executeQuery("SELECT ar.fName, ar.lName, ar.debut_yr, ar.solo_group, ar.sex " +
                                        "FROM Artist ar " +
                                        "WHERE ar.artist_num = " + it.next());

                                while(rs.next()){
                                    k++;
                                    String simArtistFName = rs.getString(1);
                                    String simArtistLName = rs.getString(2);
                                    int simDebut = rs.getInt(3);
                                    String simSoloGroup = rs.getString(4);
                                    String simSex = rs.getString(5);

                                    if(simArtistLName == null ) System.out.println(k + ". " + simArtistFName
                                            + " - Debut year: " + simDebut  + " / " + simSoloGroup + " / " + simSex);

                                    else System.out.println(k + ". " + simArtistFName + " " + simArtistLName
                                            + " - Debut year: " + simDebut  + " / " + simSoloGroup + " / " + simSex);
                                }
                            }
                        }
                    }
                    else if(userInput == 2){ // Make Playlist

                        String listName = null;
                        Boolean playlistDuplicated = false;

                        while(!playlistDuplicated) {
                            System.out.print("Playlist name: ");
                            listName = keyboard.nextLine();

                            rs = stmt.executeQuery("SELECT p.list_name FROM Playlist p " +
                                    "WHERE p.sub_nickname = " + "'" + user_nickname + "' " +
                                    "AND p.list_name = " + "'" + listName + "'");

                            if (rs.next()) {
                                System.out.println("Duplicated playlist name.");
                                continue;
                            }
                            playlistDuplicated = true;
                        }
                        stmt.executeUpdate("INSERT INTO Playlist VALUES ( " + "'" + user_nickname + "', " + "'" + listName + "', " + 0 + ")");
                        System.out.println("Playlist " + listName + " is created. You can add music in the search menu.");
                    }
                    else if(userInput == 3) { // Statistics
                        System.out.println();
                        System.out.println("1. Genre statistic");
                        System.out.println("2. Male/Female statistic");
                        System.out.println("3. Age statistic");
                        System.out.print("Input: ");
                        userInput = keyboard.nextInt();
                        keyboard.nextLine();

                        if (userInput == 1) { //genre
                            System.out.println();
                            for (int i = 0; i < 5; i++) {
                                int j = 0;
                                rs = stmt.executeQuery("SELECT m.title, m.playnum FROM Music m, Music_genre mg " +
                                        "WHERE m.mnum = mg.g_mnum AND mg.genre = " + "'" + genres[i] + "'" + "ORDER BY m.playnum DESC ");
                                System.out.println(genres[i] + " statistic");
                                System.out.println("----------------------");
                                while (rs.next()) {
                                    j++;
                                    String genreTitle = rs.getString(1);
                                    int genrePlaynum = rs.getInt(2);
                                    System.out.println(j + ". " + genreTitle + ": " + genrePlaynum);
                                }
                                System.out.println();
                            }
                        }
                        else if (userInput == 2) { //sex statistic
                            System.out.println();

                            rs = stmt.executeQuery("SELECT sb.sex, m.title, SUM(p.playnum) FROM Music m, Subscriber sb, Plays p " +
                                    "WHERE p.music_num = m.mnum AND p.nickname = sb.nickname AND sb.sex = 'M' " +
                                    "GROUP BY m.mnum HAVING (SUM(p.playnum) > 1000) ORDER BY SUM(p.playnum) DESC");

                            System.out.println("Male statistic");
                            System.out.println("-------------------");

                            int j = 0;
                            while (rs.next()) {
                                j++;
                                String sexTitle = rs.getString(2);
                                int sexPlaynum = rs.getInt(3);
                                System.out.println(j + ". " + sexTitle + ": " + sexPlaynum);
                            }
                            System.out.println();

                            rs = stmt.executeQuery("SELECT sb.sex, m.title, SUM(p.playnum) FROM Music m, Subscriber sb, Plays p " +
                                    "WHERE p.music_num = m.mnum AND p.nickname = sb.nickname AND sb.sex = 'F' " +
                                    "GROUP BY m.mnum HAVING (SUM(p.playnum) > 1000) ORDER BY SUM(p.playnum) DESC");

                            System.out.println("Female statistic");
                            System.out.println("---------------------");

                            j = 0;
                            while (rs.next()) {
                                j++;
                                String sexTitle = rs.getString(2);
                                int sexPlaynum = rs.getInt(3);
                                System.out.println(j + ". " + sexTitle + ": " + sexPlaynum);
                            }
                        }
                        else if (userInput == 3) { // age statistic

                            String[] bDates = {"200%", "199%", "198%", "197%"};
                            System.out.println();
                            for (int i = 0; i < 4; i++) {

                                int j = 0;
                                int age = (i + 1) * 10;
                                System.out.println(age + "'s statistic");
                                System.out.println("-------------------");

                                rs = stmt.executeQuery("SELECT sb.bDate, m.title, SUM(p.playnum) FROM Music m, Subscriber sb, Plays p " +
                                        "WHERE p.music_num = m.mnum AND p.nickname = sb.nickname AND sb.bDate LIKE " + "'" + bDates[i] + "'" +
                                        "GROUP BY m.mnum HAVING (SUM(p.playnum) > 1000) ORDER BY SUM(p.playnum) DESC");

                                while (rs.next()) {
                                    j++;
                                    String ageTitle = rs.getString(2);
                                    int agePlaynum = rs.getInt(3);
                                    System.out.println(j + ". " + ageTitle + ": " + agePlaynum);
                                }
                                System.out.println();
                            }
                        }
                    }
                    else{ // Music recommendation
                        System.out.println();
                        System.out.println("1. Recommendation by mood");
                        System.out.println("2. Recommendation by situation");
                        System.out.println("3. Recommend for you");
                        System.out.print("Input: ");
                        userInput = keyboard.nextInt();
                        keyboard.nextLine();

                        if(userInput == 1){ // Recommendation by mood
                            System.out.println();
                            System.out.println("1. Exciting");
                            System.out.println("2. Calm");
                            System.out.println("3. Groovy");
                            System.out.println("4. Trendy");
                            System.out.println("5. Dreamlike");
                            userInput = keyboard.nextInt();
                            keyboard.nextLine();

                            System.out.println();
                            int j = 0;

                            rs = stmt.executeQuery("SELECT mu.title, a.fName, a.lName " +
                                    "FROM Music mu, Music_mood mm, Makes m, Artist a " +
                                    "WHERE mu.mnum = mm.M_mnum AND mu.mnum = m.music_num AND a.artist_num = m.artist_num " +
                                    "AND (mu.playnum > 7000) AND mm.mood = " + "'" + moods[userInput-1] + "'");

                            System.out.println(moods[userInput-1] + " mood recommendation");
                            System.out.println("-----------------------------");

                            while(rs.next()){
                                j++;
                                String moodTitle = rs.getString(1);
                                String moodFName = rs.getString(2);
                                String moodLName = rs.getString(3);
                                if(moodLName == null) System.out.println(j + ". Title: " + moodTitle + " / Artist: " + moodFName);
                                else System.out.println(j + ". Title: " + moodTitle + " / Artist: " + moodFName + " " + moodLName);
                            }
                            System.out.println();
                        }
                        else if(userInput == 2){ //Recommendation by situation
                            System.out.println();
                            System.out.println("1. Driving");
                            System.out.println("2. Studying");
                            System.out.println("3. Travel");
                            System.out.println("4. Party");
                            System.out.println("5. Cafe");
                            System.out.print("Input: ");
                            userInput = keyboard.nextInt();
                            keyboard.nextLine();

                            System.out.println();
                            int j = 0;

                            rs = stmt.executeQuery("SELECT mu.title, a.fName, a.lName " +
                                    "FROM Music mu, Music_sit ms, Makes m, Artist a " +
                                    "WHERE mu.mnum = ms.S_mnum AND mu.mnum = m.music_num AND a.artist_num = m.artist_num " +
                                    "AND (mu.playnum > 7000) AND ms.sit = " + "'" + situations[userInput-1] + "'");

                            System.out.println(situations[userInput-1] + " situation recommendation");
                            System.out.println("----------------------------------");

                            while(rs.next()){
                                j++;
                                String situationTitle = rs.getString(1);
                                String situationFName = rs.getString(2);
                                String situationLName = rs.getString(3);
                                if(situationLName == null) System.out.println(j + ". Title: " + situationTitle + " / Artist: " + situationFName);
                                else System.out.println(j + ". Title: " + situationTitle + " / Artist: " + situationFName + " " + situationLName);
                            }
                            System.out.println();
                        }

                        else{ //Recommend for you
                            rs = stmt.executeQuery("SELECT sg.genre FROM Subscriber s, Subscriber_genre sg " +
                                    "WHERE s.nickname = sg.S_nickname AND s.nickname = " + "'" + user_nickname + "'");
                            while(rs.next()){
                                int i = 0;
                                String genreRecommend = rs.getString(1);
                                ResultSet rs2 = stmt.executeQuery("SELECT mu.title, a.fName, a.lName FROM Music mu, Music_genre mg, Makes m, Artist a " +
                                        "WHERE mu.mnum = mg.g_mnum AND mu.mnum = m.music_num AND a.artist_num = m.artist_num AND mu.playnum > 7000 " +
                                        "AND mg.genre = " + "'" + genreRecommend + "'" + "GROUP BY mu.mnum ORDER BY mu.playnum DESC");
                                System.out.println();
                                System.out.println("You may like these songs - " + genreRecommend + " genre");
                                System.out.println("-----------------------------------------------");
                                while(rs2.next()){
                                    i++;
                                    String recoTitle = rs2.getString(1);
                                    String recoFName = rs2.getString(2);
                                    String recoLName = rs2.getString(3);

                                    if(recoLName == null) System.out.println(i + ". Title: " + recoTitle + " / Artist: " + recoFName);
                                    else System.out.println(i + ". Title: " + recoTitle + " / Artist: " + recoFName + " " + recoLName);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}