package com.example.dungeongame.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.Collections;

/**
 * A Leaderboard implementation that keeps a history of previous player scores in the current game
 * instance with each entry displaying the player's name, score, date and time of the attempt.
 * ---
 * Design Pattern: Singleton
 * @author Ethan Nguyen-Tu
 * @version 1.2.0
 */
public class Leaderboard implements Resettable {
    // CLASS VARIABLES
    private static volatile Leaderboard leaderboard;

    private boolean descending = true;  // false = Ascending Order
    private ArrayList<LeaderboardEntry> entries;  // The score history of the leaderboard
    private int count;  // Number of leaderboard entries currently in existence


    // CONSTRUCTORS
    private Leaderboard() {
        this.entries = new ArrayList<>(5);
        this.count = 0;
    }


    // PUBLIC METHODS
    /**
     * Singleton implementation for the Leaderboard class.
     *
     * @return Leaderboard singleton instance
     */
    public static Leaderboard getLeaderboard() {
        if (leaderboard == null) {
            synchronized (Leaderboard.class) {
                if (leaderboard == null) {
                    leaderboard = new Leaderboard();
                }
            }
        }
        return leaderboard;
    }


    /**
     * Method that adds in a new entry to the leaderboard and sorts it.
     *
     * @param playerName String name of the player whose owns the leaderboard entry
     * @param score Integer of the player's score to be recorded on the leaderboard
     * @return LeaderboardEntry of the entry that was added
     */
    public LeaderboardEntry addEntry(String playerName, int score) {
        this.count += 1;
        LeaderboardEntry added = new LeaderboardEntry(playerName, score);
        this.entries.add(added);

        sort();

        Logger.getGlobal().info(toString());
        return added;
    }


    /**
     * Overload of the addEntry() method that only requires a score and puts "Anonymous" for the
     * player name.
     *
     * @param score Integer score of the player
     * @return LeaderboardEntry of the entry that was added
     */
    public LeaderboardEntry addEntry(int score) {
        return addEntry("Anonymous", score);
    }


    /**
     * Method that takes in a leaderboard entry and updates its position on the leaderboard.
     *
     * @param entry LeaderboardEntry whose position should be updated
     * @return Leaderboard entry updated
     */
    public LeaderboardEntry updateEntry(LeaderboardEntry entry) {
        if (count < 2) {
            return entry;
        }
        sort();

        return entry;
    }


    /**
     * Method that sorts the leaderboard entries in descending or ascending order depending on the
     * Leaderboard's descending variable.
     */
    private void sort() {
        if (descending) {
            Collections.sort(entries, Collections.reverseOrder());
        } else {
            Collections.sort(entries);
        }
    }


    /**
     * Method that flips the order of the Leaderboard.
     */
    public void reverse() {
        if (this.count == 0) {
            return;
        }
        if (descending) {
            Collections.sort(entries);
            descending = false;
        } else {
            Collections.sort(entries, Collections.reverseOrder());
            descending = true;
        }
    }


    /**
     * Override of the toString() method for the Leaderboard class. Prints the title, key, and
     * leaderboard entries in descending order if descending is true, else prints in ascending
     * order.
     *
     * @return String print out of the Leaderboard
     */
    @NonNull
    public String toString() {
        return toString(true);
    }


    /**
     * Overload of the toString() method that gives the option of including the title and header.
     *
     * @param header Boolean true if the title and header should be included
     * @return String print out of the Leaderboard
     */
    public String toString(boolean header) {
        String out = "";
        if (header) {
            out += "\n=====[ Leaderboard ]=====\n" + String.format("%-11s", "Rank.")
                    + String.format("%-24s", "Player Name") + String.format("%-13s", "Score")
                    + "Date-Time\n";
        }
        for (int i = 1; i < this.count + 1; i++) {
            LeaderboardEntry entry = this.entries.get(i - 1);
            out += String.format("%-11.11s", i + ".")
                    + String.format("%-24.24s", entry.getPlayerName())
                    + String.format("%-13.13s", entry.getScore())
                    + entry.getTimeStamp();
            if (i < this.count) {
                out += "\n";
            }
        }
        return out;
    }


    // GETTERS AND SETTER
    /**
     * Getter for the rank of a leaderboard entry.
     *
     * @param entry LeaderboardEntry to get the rank of.
     * @return int of the LeaderboardEntry's rank or -1 if the LeaderboardEntry is not found
     */
    public int getRank(LeaderboardEntry entry) {
        for (int i = 0; i < entries.size(); i++) {
            if (entry.equals(entries.get(i))) {
                return i + 1;
            }
        }
        return -1;
    }


    /**
     * Getter method for the Leaderboard entries.
     *
     * @return LeaderboardEntry[] Array of leaderboard entries of type LeaderboardEntry.
     */
    public LeaderboardEntry[] getEntries() {
        LeaderboardEntry[] array = new LeaderboardEntry[entries.size()];
        for (int i = 0; i < entries.size(); i++) {
            array[i] = entries.get(i);
        }
        return array;
    }


    /**
     * Getter method for the number of entries in the Leaderboard.
     *
     * @return int number of entries in the leaderboard
     */
    public int size() {
        return this.count;
    }


    /**
     * Getter method for if the Leaderboard is in descending order or not.
     *
     * @return Boolean true if the leaderboard is in descending order or false if it is ascending
     */
    public boolean isDescending() {
        return descending;
    }

    // TEST CASE METHODS
    /**
     * Should never be called outside of unit tests.
     *
     * @return boolean true if the reset succeeds, else false
     */
    public boolean reset() {
        this.entries = new ArrayList<>(5);
        this.count = 0;
        this.descending = true;
        return true;
    }

    /**
     * Method that removes the given LeaderboardEntry.
     * @param e LeaderboardEntry to remove
     * @return boolean true if the entry was successfully removed, else false
     */
    public boolean remove(LeaderboardEntry e) {
        return entries.remove(e);
    }

} // FIN
