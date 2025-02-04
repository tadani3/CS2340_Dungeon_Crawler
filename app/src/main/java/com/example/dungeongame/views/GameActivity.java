package com.example.dungeongame.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dungeongame.R;
import com.example.dungeongame.model.Clock;
import com.example.dungeongame.model.Configuration;
import com.example.dungeongame.model.GameMap;
import com.example.dungeongame.model.MapConfiguration;
import com.example.dungeongame.model.Tickable;
import com.example.dungeongame.model.enemies.Enemy;
import com.example.dungeongame.model.powerups.PowerUpItem;
import com.example.dungeongame.viewmodels.AttackViewModel;
import com.example.dungeongame.viewmodels.GraphicTile;
import com.example.dungeongame.model.Player;
import com.example.dungeongame.viewmodels.TileSampler;
import com.example.dungeongame.viewmodels.ConfigViewModel;
import com.example.dungeongame.viewmodels.GameViewModel;
import com.example.dungeongame.viewmodels.observation.RenderObserver;

import java.util.LinkedList;
import java.util.logging.Logger;


public class GameActivity extends PauseMenu {
    // Singleton
    private static final Clock CLOCK = Clock.getClock();
    private static final Player PLAYER = Player.getPlayer();

    // UI Components
    private RelativeLayout gameView;
    private RelativeLayout gameOverlayView;
    private TextView playerNameText;
    private TextView difficultyText;
    private TextView currentHPText;
    private TextView scoreText;
    private ImageView digitalJoystick;
    private ImageView digitalJoystickBase;
    private ImageView healthIcon;
    private ImageView teleportIcon;
    private ImageView attackIcon;
    private ImageView scoreIcon;

    private Button attackButton;

    // Handlers
    private Tickable scoreHandler;
    private final Handler hpHandler = new Handler();
    private final Handler tickHandler = new Handler();

    // View Models
    private GameViewModel gameViewModel = GameViewModel.getGameViewModel();
    private AttackViewModel attackViewModel;
    private PlayerView playerView;
    private WeaponView weaponView;


    private RenderObserver renderObserver;
    private LinkedList<PowerItemView> powerItemViews;
    private LinkedList<EnemyView> enemyViews = new LinkedList<>();

    private Context context;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     
        setContentView(R.layout.activity_game);
        setUpPauseMenu();

        findUI();
        // Initializing player name, score, difficulty, and health
        ConfigViewModel.populateInterface(this);


        this.gameViewModel.configureDisplay(getResources().getDisplayMetrics());

        // Configuring the maps
        GameMap.setRedStone(new GameMap(getResources().getXml(R.xml.red_stone_map)));
        GameMap.setSpookyHouse(new GameMap(getResources().getXml(R.xml.spooky_house_map)));
        GameMap.setGrayStone(new GameMap(getResources().getXml(R.xml.gray_stone_map)));
        MapConfiguration.setAll();


        this.enemyViews = new LinkedList<>();
        //new power up line
        this.powerItemViews = new LinkedList<>();

        this.gameViewModel.setMap(GameMap.getRedStone());

        // Registering score update callback
        PLAYER.setScoreUpdateCallback(() -> {
            scoreText.setText(getString(R.string.label_score, Player.getPlayer().getScore()));
        });

        // Setting up the handler to update the score every other second
        this.scoreHandler = new Tickable() {
            private int ticksSince = 0;

            @Override
            public void onTick() {
                if (ticksSince < 20) {
                    ticksSince++;
                    return;
                }

                ticksSince = 0;
                if (Player.getPlayer().getScore() > 0) {
                    Player.getPlayer().addScore(-1);
                }
            }
        };
        CLOCK.add(scoreHandler);

        // Setting up a tick handler to tick all tickables every 100ms
        GameActivity activityThisClone = this;
        tickHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                CLOCK.tick();

                // Re-rendering all enemies
                activityThisClone.rerenderEnemies();
                activityThisClone.rerenderPowerUpItems(); //This line may not be needed?

                tickHandler.postDelayed(this, 100);
            }
        }, 100);


        /**
         * Handler to check for updates to HP values and update the screen every 100ms. Will
         * not change the score if Player HP goes beyond 0.
         */
        hpHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Player.getPlayer().getHP() > 0) {
                    activityThisClone.setCurrentHP(Player.getPlayer().getHP());
                    hpHandler.postDelayed(this, 100);
                } else {
                    endGame();
                }
                checkOnPowerUps();
            }
        }, 100);

        // Setting tile set
        TileSampler.getInstance().setTileset(BitmapFactory.decodeResource(getResources(),
                R.drawable.dungeontiles), 16, 16, this.gameViewModel.getTileWidth());

        this.context = this;

        //Rendering the player on the map
        this.playerView = new PlayerView(this, this.gameViewModel);
        this.gameOverlayView.addView(playerView);
        this.renderObserver = new RenderObserver(this);

        this.weaponView = new WeaponView(this, this.gameViewModel);
        this.attackViewModel = new AttackViewModel(this);

        // Rendering the map
        this.gameViewModel.getTiles().observe(this, tiles -> {
            // clearing the canvas
            this.gameView.removeAllViews();

            // drawing the tiles
            for (GraphicTile tile : tiles) {
                TileView tileView = new TileView(this, tile);
                this.gameView.addView(tileView);
            }

            activityThisClone.rerenderEnemies();
            /*
            render the power items first time?
            */
            activityThisClone.rerenderPowerUpItems();
        });

        // Setting an initial camera location
        this.gameViewModel.setCameraCenter(PLAYER.getX(), PLAYER.getY());

        // setting up the joystick
        // get the pixel width of the joystick view
        this.digitalJoystickBase.setOnTouchListener(new JoystickTouchListener(this));

        this.gameViewModel.getJoystickPosition().observe(this, joystickPosition -> {
            // translating the joystick image
            this.digitalJoystick.setTranslationX(joystickPosition.get(0)
                    * this.digitalJoystick.getWidth() / 2);
            this.digitalJoystick.setTranslationY(joystickPosition.get(1)
                    * this.digitalJoystick.getHeight() / 2);
        });

        this.attackButton.setOnClickListener(e -> {
            this.attackViewModel.playerAttacked();
        });
    } // END OF onCREATE()


    /**
     * Helper Method to locate all of the UI components for the GameActivity.
     */
    private void findUI() {
        this.gameView = findViewById(R.id.gameView);
        this.gameOverlayView = findViewById(R.id.gameOverlayView);
        this.playerNameText = findViewById(R.id.gameScreenPlayerNameText);
        this.difficultyText = findViewById(R.id.gameScreenDifficultyText);
        this.currentHPText = findViewById(R.id.gameScreenHPText);
        this.scoreText = findViewById(R.id.gameScreenScoreText);

        this.digitalJoystick = findViewById(R.id.joystickCenter);
        this.digitalJoystickBase = findViewById(R.id.joystickBackground);

        this.attackButton = findViewById(R.id.attack_button);

        this.attackIcon = findViewById(R.id.attack_image_invis);
        this.healthIcon = findViewById(R.id.health_image_invis);
        this.teleportIcon = findViewById(R.id.teleport_image_invis);
        this.scoreIcon = findViewById(R.id.score_image_invis);
    }


    public void rerenderPlayer() {
        this.gameOverlayView.removeView(playerView);
        this.gameOverlayView.addView(playerView);
        this.gameViewModel.recenterCamera();
        if (Configuration.getConfig().isGameOver()) {
            endGame();
        }
    }

    private void rerenderEnemies() {
        for (EnemyView enemyView : enemyViews) {
            gameOverlayView.removeView(enemyView);
        }

        enemyViews = new LinkedList<>();
        for (Enemy enemy : GameMap.getActiveMap().getEnemies()) {
            EnemyView enemyView = new EnemyView(this,
                    this.gameViewModel, enemy);
            gameOverlayView.addView(enemyView);
            enemyViews.add(enemyView);
        }

        gameOverlayView.invalidate();
    }
    private void rerenderPowerUpItems() {
        for (PowerItemView itemView : powerItemViews) {
            gameOverlayView.removeView(itemView);
        }
        powerItemViews = new LinkedList<>();
        for (PowerUpItem item : GameMap.getActiveMap().getPowerUpItems()) {
            PowerItemView itemView = new PowerItemView(this,
                    this.gameViewModel, item);
            gameOverlayView.addView(itemView);
            powerItemViews.add(itemView);
        }

        gameOverlayView.invalidate();
    }
    private void checkOnPowerUps() {
        if ((Player.getPlayer().getActivePowerNames()[0] != null
                && Player.getPlayer().getActivePowerNames()[0].equals("health"))) {
            healthIcon.setVisibility(View.VISIBLE);
        }
        if ((Player.getPlayer().getActivePowerNames()[1] != null
                && Player.getPlayer().getActivePowerNames()[1].equals("teleport"))) {
            teleportIcon.setVisibility(View.VISIBLE);
        }
        if ((Player.getPlayer().getActivePowerNames()[2] != null
                && Player.getPlayer().getActivePowerNames()[2].equals("attack"))) {
            attackIcon.setVisibility(View.VISIBLE);
        }
        if ((Player.getPlayer().getActivePowerNames()[3] != null
                && Player.getPlayer().getActivePowerNames()[3].equals("money"))) {
            scoreIcon.setVisibility(View.VISIBLE);
        }
    }



    public boolean renderWeapon() {
        if (weaponView.getParent() != null) {
            return false;
        }
        this.gameOverlayView.addView(weaponView);
        this.gameViewModel.recenterCamera();
        return true;
    }

    public void unrenderWeapon() {
        this.gameOverlayView.removeView(weaponView);
        this.gameViewModel.recenterCamera();
    }

    public DeathView renderDeath() {
        DeathView deathView = new DeathView(context, this.gameViewModel);
        this.gameOverlayView.addView(deathView);
        return deathView;
    }

    public void unrenderDeath(DeathView deathView) {
        this.gameOverlayView.removeView(deathView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePauseMenu();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Removing all enemies from the maps
        if (GameMap.getRedStone() == null) {
            Logger.getGlobal().info("RedStone map does not exist.");
        } else {
            GameMap.getRedStone().clearEnemies();
        }
        if (GameMap.getSpookyHouse() == null) {
            Logger.getGlobal().info("SpookyHouse map does not exist.");
        } else {
            GameMap.getRedStone().clearEnemies();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Killing the handlers
        CLOCK.remove(this.scoreHandler);
        this.tickHandler.removeCallbacksAndMessages(null);
        this.hpHandler.removeCallbacksAndMessages(null);

        Logger.getGlobal().info("GameActivity is destroyed.");
    }

    public void setPlayerName(String playerName) {
        this.playerNameText.setText(getString(R.string.label_playerName, playerName));
    }

    public void setPlayerScore(int score) {
        this.scoreText.setText(getString(R.string.label_score, score));
    }

    public void setDifficulty(String difficulty) {
        this.difficultyText.setText(getString(R.string.label_difficulty, difficulty));
    }


    public void setCurrentHP(int currentHP) {
        this.currentHPText.setText(getString(R.string.label_hp, currentHP, PLAYER.getMaxHP()));
    }

    /**
     * Method that ends the game by starting the EndScreenActivity.
     */
    public void endGame() {
        this.startActivity(new Intent(this, EndScreenActivity.class));
        finish();
    }

    /**
     * Class that handles the touch events on the joystick.
     */
    private class JoystickTouchListener implements View.OnTouchListener {
        private GameActivity activity;
        private float startX;
        private float startY;
        private long lastTime;

        public JoystickTouchListener(GameActivity activity) {
            super();

            this.activity = activity;
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            long time = System.nanoTime();

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                // start tracking movement
                this.startX = event.getX();
                this.startY = event.getY();
                lastTime = time;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                // stop tracking movement and move joystick back to center
                this.activity.gameViewModel.setJoystickPosition(0, 0,
                        (time - lastTime) * 1e-9f);
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                // move joystick to the position of the touch
                int joystickSize = this.activity.digitalJoystick.getWidth() / 2;

                this.activity.gameViewModel.setJoystickPosition(
                        (event.getX() - this.startX) / joystickSize,
                        (event.getY() - this.startY) / joystickSize,
                        (time - lastTime) * 1e-9f);
            }

            this.lastTime = time;

            return true;
        }
    } // fin

} // FIN
