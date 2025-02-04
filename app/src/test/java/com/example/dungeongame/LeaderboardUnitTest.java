package com.example.dungeongame;

// Related Class Imports
import static org.junit.Assert.assertTrue;

import com.example.dungeongame.model.Leaderboard;
import com.example.dungeongame.model.LeaderboardEntry;

// Testing Imports
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * NOTE: THESE TEST CASES CANNOT BE RUN AT THE SAME TIME AS THE PlayerLeaderboardUnitTest FILE DUE
 * TO THE SINGLETON IMPLEMENTATION OF THE LEADERBOARD.
 * ---
 * Java unit tests created for the Leaderboard Singleton Pattern class. These tests are designed to
 * run in the order they are written and cannot be individually run.
 *
 * @author Ethan Nguyen-Tu
 * @version 1.0.1
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LeaderboardUnitTest {
    // TEST VARIABLES
    public static final int TIMEOUT = 200;
    private Leaderboard leaderboard;

    @Before
    public void testLeaderboardInitialization() {
        leaderboard = Leaderboard.getLeaderboard();
    }

    @Test(timeout = TIMEOUT)
    public void aTestLeaderboardIsSingleton() {
        Leaderboard leaderboard2 = Leaderboard.getLeaderboard();

        assert (leaderboard == leaderboard2);
    }

    @Test(timeout = TIMEOUT)
    public void bTestLeaderboardReset() {
        leaderboard.reset();
        assertTrue(leaderboard.size() == 0);
        assertTrue(leaderboard.isDescending());
    }

    @Test(timeout = TIMEOUT)
    public void cestAdd1ToLeaderboard() {

        int initial = leaderboard.size();
        leaderboard.addEntry(0);

        assert (leaderboard.getEntries()[initial] != null & leaderboard.size() == initial + 1);
    }

    @Test(timeout = TIMEOUT)
    public void dTestExpandLeaderboard() {
        int initialEntries = leaderboard.size();

        leaderboard.addEntry("Ethan", 99999);
        leaderboard.addEntry("Ron", 100);
        leaderboard.addEntry("Phil", 110);
        leaderboard.addEntry("Phil", 90);
        leaderboard.addEntry("Ron", -1);
        leaderboard.addEntry("Ethan", -99999);

        assert (leaderboard.size() == initialEntries + 6);
    }

    @Test(timeout = TIMEOUT)
    public void eTestLeaderboardDescendingOrder() {
        String[] expectedOrder = {"Ethan", "Phil", "Ron", "Phil", "Anonymous", "Ron", "Ethan"};
        LeaderboardEntry[] entries = leaderboard.getEntries();

        boolean correctOrder = true;
        for (int i = 0; i < leaderboard.size(); i++) {
            if (!(expectedOrder[i].equals(entries[i].getPlayerName()))) {
                correctOrder = false;
                break;
            }
        }
        System.out.println("DESCENDING ORDER\n" + leaderboard);

        assert (correctOrder);
    }

    @Test(timeout = TIMEOUT)
    public void fTestSameScore() {
        leaderboard.addEntry("Tommy", 110);

        assert (leaderboard.getEntries()[1].getPlayerName().equals("Tommy"));
    }

    @Test(timeout = TIMEOUT)
    public void gTestLeaderboardAscendingOrder() {
        leaderboard.reverse();
        String[] expectedOrder = {"Ethan", "Ron", "Anonymous", "Phil", "Ron", "Phil", "Tommy",
            "Ethan"};
        LeaderboardEntry[] entries = leaderboard.getEntries();

        boolean correctOrder = true;
        for (int i = 0; i < leaderboard.size(); i++) {
            if (!(expectedOrder[i].equals(entries[i].getPlayerName()))) {
                correctOrder = false;
                break;
            }
        }
        System.out.println("ASCENDING ORDER\n" + leaderboard);

        assert (correctOrder);
    }

}  // FIN
