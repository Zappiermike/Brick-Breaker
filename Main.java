public class Main {
    public static void main(String[] args) {
        BrickBreaker gameInstance = new BrickBreaker();
        gameInstance.startGame();

        while (!gameInstance.isGameOver()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        gameInstance.endGame();
        return;
    }
}
