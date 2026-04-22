package ru.nsu.ziabkin.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Core logic of the Snake game with progressive difficulty scaling.
 */
public class GameModel {
    private final int width;
    private final int height;
    private final int winLength;
    private final int foodCount;
    private final List<Snake> snakes;
    private final List<Point> foods;
    private final Random random;
    private GameState state;

    // To track which milestones have already triggered a spawn
    private final Set<Integer> reachedMilestones = new HashSet<>();

    public GameModel(int width, int height, int winLength, int foodCount) {
        this.width = width;
        this.height = height;
        this.winLength = winLength;
        this.foodCount = foodCount;
        this.snakes = new ArrayList<>();
        this.foods = new ArrayList<>();
        this.random = new Random();
        this.state = GameState.PLAYING;
        initGame();
    }

    private void initGame() {
        snakes.clear();
        foods.clear();
        reachedMilestones.clear();
        // Index 0 is player
        snakes.add(new Snake(new Point(width / 2, height / 2), false));
        for (int i = 0; i < foodCount; i++) {
            spawnFood();
        }
    }

    public void update() {
        if (state != GameState.PLAYING) return;

        updateRobots();
        moveSnakes();
        checkCollisions();
        handleProgressiveSpawning(); // New milestone-based logic
        checkWinCondition();
    }

    /**
     * Spawns new bots based on player's progress (25%, 75%, 90% of winLength).
     */
    private void handleProgressiveSpawning() {
        int playerLength = getPlayerSnake().getBody().size();

        // 25% Milestone -> Spawn 1 bot
        checkAndSpawn(0.25, 1);

        // 75% Milestone -> Spawn 2 bots
        checkAndSpawn(0.75, 2);

        // 90% Milestone -> Spawn 1 bot
        checkAndSpawn(0.90, 1);
    }

    private void checkAndSpawn(double percentage, int count) {
        int milestoneScore = (int) (winLength * percentage);
        int playerLength = getPlayerSnake().getBody().size();

        // If player reaches the milestone and we haven't spawned for it yet
        if (playerLength >= milestoneScore && !reachedMilestones.contains(milestoneScore)) {
            for (int i = 0; i < count; i++) {
                spawnNewBot();
            }
            reachedMilestones.add(milestoneScore);
        }
    }

    private void spawnNewBot() {
        Point p = findFreePoint();
        if (p != null) {
            snakes.add(new Snake(p, true));
        }
    }

    private void updateRobots() {
        for (Snake s : snakes) {
            if (s.isRobot() && s.isAlive()) {
                s.setDirection(findSmartDirection(s));
            }
        }
    }

    private Direction findSmartDirection(Snake snake) {
        Direction bestDir = findDirectionToClosestFood(snake);
        if (isSafe(snake, bestDir)) return bestDir;
        for (Direction d : Direction.values()) {
            if (isSafe(snake, d)) return d;
        }
        return bestDir;
    }

    private boolean isSafe(Snake snake, Direction dir) {
        Point head = snake.getHead();
        int nx = head.getX(), ny = head.getY();
        if (dir == Direction.UP) ny--;
        else if (dir == Direction.DOWN) ny++;
        else if (dir == Direction.LEFT) nx--;
        else if (dir == Direction.RIGHT) nx++;

        if (nx < 0 || nx >= width || ny < 0 || ny >= height) return false;
        Point np = new Point(nx, ny);
        for (Snake s : snakes) {
            if (s.isAlive() && s.getBody().contains(np)) return false;
        }
        Direction cur = snake.getDirection();
        return !((dir == Direction.UP && cur == Direction.DOWN) ||
                (dir == Direction.DOWN && cur == Direction.UP) ||
                (dir == Direction.LEFT && cur == Direction.RIGHT) ||
                (dir == Direction.RIGHT && cur == Direction.LEFT));
    }

    private Direction findDirectionToClosestFood(Snake snake) {
        Point head = snake.getHead();
        Point closest = null;
        int minD = Integer.MAX_VALUE;
        for (Point f : foods) {
            int d = Math.abs(head.getX() - f.getX()) + Math.abs(head.getY() - f.getY());
            if (d < minD) { minD = d; closest = f; }
        }
        if (closest != null) {
            if (closest.getX() > head.getX()) return Direction.RIGHT;
            if (closest.getX() < head.getX()) return Direction.LEFT;
            if (closest.getY() > head.getY()) return Direction.DOWN;
            if (closest.getY() < head.getY()) return Direction.UP;
        }
        return snake.getDirection();
    }

    private void moveSnakes() {
        for (Snake s : snakes) {
            if (s.isAlive()) {
                Point next = s.calculateNextHead();
                boolean ate = foods.contains(next);
                s.move(next, ate);
                if (ate) { foods.remove(next); spawnFood(); }
            }
        }
    }

    private void checkCollisions() {
        for (Snake s : snakes) {
            if (!s.isAlive()) continue;
            Point h = s.getHead();
            if (h.getX() < 0 || h.getX() >= width || h.getY() < 0 || h.getY() >= height) s.setAlive(false);
            for (Snake other : snakes) {
                if (!other.isAlive()) continue;
                List<Point> body = other.getBody();
                for (int i = 0; i < body.size(); i++) {
                    if (s == other && i == 0) continue;
                    if (h.equals(body.get(i))) s.setAlive(false);
                }
            }
        }
        if (!getPlayerSnake().isAlive()) state = GameState.LOST;
    }

    private void checkWinCondition() {
        if (getPlayerSnake().getBody().size() >= winLength) {
            state = GameState.WON;
            return;
        }
        boolean botsAlive = false;
        for (int i = 1; i < snakes.size(); i++) {
            if (snakes.get(i).isAlive()) { botsAlive = true; break; }
        }
        if (!botsAlive && snakes.size() > 1) state = GameState.WON;
    }

    private void spawnFood() {
        Point p = findAnyFreePoint();
        if (p != null) foods.add(p);
    }

    public Point findFreePoint() {
        Point playerHead = getPlayerSnake().getHead();
        for (int i = 0; i < 150; i++) {
            int x = random.nextInt(width), y = random.nextInt(height);
            Point p = new Point(x, y);
            if (Math.abs(p.getX() - playerHead.getX()) + Math.abs(p.getY() - playerHead.getY()) < 5) continue;
            if (isPointActuallyFree(p)) return p;
        }
        return findAnyFreePoint();
    }

    private Point findAnyFreePoint() {
        for (int i = 0; i < 100; i++) {
            int x = random.nextInt(width), y = random.nextInt(height);
            Point p = new Point(x, y);
            if (isPointActuallyFree(p)) return p;
        }
        return null;
    }

    private boolean isPointActuallyFree(Point p) {
        for (Snake s : snakes) if (s.getBody().contains(p)) return false;
        return !foods.contains(p);
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public GameState getState() { return state; }
    public List<Snake> getSnakes() { return snakes; }
    public List<Point> getFoods() { return foods; }
    public Snake getPlayerSnake() { return snakes.get(0); }
}