package com.example.dungeongame;

// Related Class Imports
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.example.dungeongame.model.Leaderboard;
import com.example.dungeongame.model.LeaderboardEntry;
import com.example.dungeongame.model.Player;

// Testing Imports
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * NOTE: THESE TEST CASES CANNOT BE RUN AT THE SAME TIME AS THE LeaderboardUnitTest FILE DUE
 * TO THE SINGLETON IMPLEMENTATION OF THE LEADERBOARD.
 * ---
 * Java unit tests created for the Leaderboard Singleton Pattern class. These tests are designed to
 * run in the order they are written and cannot be individually run.
 *
 * @author Ethan Nguyen-Tu
 * @version 1.0.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlayerLeaderboardUnitTest {
    // TEST VARIABLES
    public static final int TIMEOUT = 200;
    private Leaderboard leaderboard;
    private Player player;

    @Before
    public void testInitialization() {
        leaderboard = Leaderboard.getLeaderboard();
        player = Player.getPlayer();
    }

    @Test(timeout = TIMEOUT)
    public void aTestLeaderboardReset() {
        leaderboard.reset();
        assertTrue(leaderboard.size() == 0);
        assertTrue(leaderboard.isDescending());
    }

    @Test(timeout = TIMEOUT)
    public void bTestTestPlayer() {
        player.nullPlayer();
        assertTrue(!player.isVictory());
    }

    @Test(timeout = TIMEOUT)
    public void cTestPlayerAdded() {
        leaderboard.addEntry("Ethan", 99999);
        leaderboard.addEntry("Ron", 100);
        leaderboard.addEntry("Phil", 110);
        leaderboard.addEntry("Phil", 90);
        leaderboard.addEntry("Ron", -1);
        leaderboard.addEntry("Ethan", -99999);

        player.setPlayerName("TEST");
        player = Player.getPlayer();
        player.getLeaderboardEntry();

        System.out.println("\nPlayerAdded");
        System.out.println(leaderboard);
        assertTrue(leaderboard.getEntries()[4].getPlayerName().equals("TEST")); // Rank 5
    }

    @Test(timeout = TIMEOUT)
    public void dTestPlayerScoreIncreased() {
        player.clearLeaderboardEntry();
        player.setScore(91);
        assertTrue(player.getLeaderboardEntry().getScore() == 91);
    }

    @Test(timeout = TIMEOUT)
    public void eTestMovedUpLeaderboard() {
        assertTrue(leaderboard.getEntries()[3].getPlayerName().equals("TEST"));
    }

    @Test(timeout = TIMEOUT)
    public void fTestMovedUpLeaderboard() {
        System.out.println("\nMoveUpLeaderboard");
        System.out.println(leaderboard);
        assertTrue(leaderboard.getEntries()[3].getPlayerName().equals("TEST"));
    }


    @Test(timeout = TIMEOUT)
    public void gTestLeaderboardAscendingOrder() {
        leaderboard.reverse();
        System.out.println("\nReverseOrder");
        System.out.println(leaderboard);

        String[] expectedOrder = {"Ethan", "Ron", "Phil", "TEST", "Ron", "Phil", "Ethan"};
        LeaderboardEntry[] entries = leaderboard.getEntries();

        boolean correctOrder = true;
        for (int i = 0; i < leaderboard.size(); i++) {
            if (!(expectedOrder[i].equals(entries[i].getPlayerName()))) {
                correctOrder = false;
                break;
            }
        }

        assertTrue(correctOrder);
    }

    @Test(timeout = TIMEOUT)
    public void hTestAddAscendingOrder() {
        LeaderboardEntry test = leaderboard.addEntry("Ethan", 99999);

        System.out.println(leaderboard);

        assertTrue(leaderboard.getEntries()[7].equals(test));
    }

}  // FIN
