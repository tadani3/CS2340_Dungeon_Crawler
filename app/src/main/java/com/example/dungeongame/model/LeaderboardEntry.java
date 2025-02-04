package com.example.dungeongame.model;

import java.util.Calendar;
import java.util.Date;

/**
 *  Class that specifies a leaderboard entry for the Leaderboard class.
 *
 * @author Ethan Nguyen-Tu
 * @version 1.0.0
 */
public class LeaderboardEntry implements Comparable<LeaderboardEntry> {
    // CLASS VARIABLES
    private String playerName;
    private int score;
    private Date timeStamp;
    private boolean edit = true;


    // CONSTRUCTORS
    public LeaderboardEntry(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
        this.timeStamp = Calendar.getInstance().getTime();
    }


    // PUBLIC METHODS
    /**
     * Override of the toString() method for the LeadershipEntry class
     *
     * @return String specifying the player name, score, and time stamp of the leaderboard entry
     */
    public String toString() {
        return "Player: " + this.playerName + "\nScore: " + this.score + "\nTime: "
                + this.timeStamp;
    }


    /**
     * Score comparison method to determine which LeaderboardEntry object has a higher score.
     *
     * @param o LeaderboardEntry object to compare scores with the current object
     * @return int 1 if current object has a higher score, -1 if lower, and 0 if equal
     */
    public int compare(Object o) {
        if (!(o instanceof LeaderboardEntry)) {
            throw new IllegalArgumentException("The given object to compare is not of type "
                    + "LeaderboardEntry.");
        }
        int compare = Integer.compare(this.score, ((LeaderboardEntry) o).score);
        if (compare == 0) { // Tiebreak comparison by date if scores match.
            compare = this.timeStamp.compareTo(((LeaderboardEntry) o).timeStamp);
        }

        return compare;
    }


    /**
     * Method that compares the current entry to the entry given to it
     * @param entry LeaderboardEntry to compare with the current entry
     * @return int 1 if current object has a higher score, -1 if lower, and 0 if equal
     */
    public int compareTo(LeaderboardEntry entry) {
        return this.compare(entry);
    }


    /**
     * Override of the equals method that checks if the given object is the object it is being
     * compared to.
     *
     * @param o Object of the thing to be compared
     * @return Boolean of if the object is the thing it is being compared to
     */
    public boolean equals(Object o) {
        return o == this;
    }


    // Leaderboard Editing
    /**
     * Getter method to check if a LeaderboardEntry is editable.
     *
     * @return boolean true if editable, else false
     */
    public boolean isEditable() {
        return edit;
    }


    /**
     * Setter for if the leaderboard entry can be edited.
     */
    public void finalize() {
        edit = false;
    }


    // GETTERS AND SETTERS
    // Player Name
    /**
     * Getter for the player name associated with the LeaderboardEntry.
     *
     * @return String of the associated player name
     */
    public String getPlayerName() {
        return playerName;
    }


    /**
     * Sets the player name recorded on the LeaderboardEntry to something else.
     *
     * Use Case: Someone wants to make their "Anonymous" entry to their actual name, "Ethan".
     *
     * @param playerName String name to replace the previous player name on the entry
     * @return boolean true if set succeeds, else false
     */
    public boolean setPlayerName(String playerName) {
        if (edit) {
            this.playerName = playerName;
            return true;
        }
        return false;
    }


    // Score
    /**
     * Getter for the score associated with the LeaderboardEntry.
     *
     * @return int of the associated score of the LeaderboardEntry
     */
    public int getScore() {
        return score;
    }

    /**
     * Setter for the score associated with the LeaderboardEntry.
     *
     * @param score int of the associated score of the LeaderboardEntry
     * @return boolean true if set succeeds, else false
     */
    public boolean setScore(int score) {
        if (edit) {
            this.score = score;
            return true;
        }
        return false;
    }


    // Time Stamp
    /**
     * Getter for the date and time associated with the LeaderboardEntry.
     *
     * @return Date of the associated date and time of the LeaderboardEntry
     */
    public Date getTimeStamp() {
        return timeStamp;
    }

    /**
     * Setter for the date and time associated with the LeaderboardEntry.
     *
     * @param timeStamp Date of the associated date and time of the LeaderboardEntry
     * @return boolean true if set succeeds, else false
     */
    public boolean setTimeStamp(Date timeStamp) {
        if (edit) {
            this.timeStamp = timeStamp;
            return true;
        }
        return false;
    }

} // FIN

