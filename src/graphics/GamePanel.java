package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import logic.*;

/**
 * This class generates the Game Logic class (which handles the game) and displays it.
 **/
public class GamePanel extends JPanel {

    // PROPERTIES

    private GameWindow window;	// The parent window in which this GamePanel is in.
    public GameWindow getWindow() { return window; }

    private Tetris game;		// Where the whole logic for the game is in.
    public Tetris getGame() { return game; }

    JLabel gameOverLabel;
    JLabel pauseLabel;

    // INITIALIZING

    public GamePanel (GameWindow window, float speed, int rows, int columns) {
        this.window = window;

        game = new Tetris(this, speed, rows, columns);

        setPreferredSize(new Dimension((int)window.getSize().getWidth(), game.getGameHeight()));

        addKeyListener(game);
        setFocusable(true);
        setVisible(true);
        window.getContentPane().add(this);
        requestFocusInWindow();

        System.out.println("GamePanel created!");
    }

    // METHODS

    public void doGameOver () {
        int endScore = game.getCurrentScore();
        // TODO: Display "Game Over" and The endScore and which button to restart.
        gameOverLabel = new JLabel("<html>GAME OVER<br>Score: " + endScore + "<br>Space: Restart<br>esc: Main Menu</html>", javax.swing.SwingConstants.CENTER);
        gameOverLabel.setBackground(new Color (200, 200, 200, 180) );
        gameOverLabel.setVisible(true);
        gameOverLabel.setOpaque(true);
        gameOverLabel.setBounds(0, 60, (int)this.getSize().getWidth(), 80);
        this.add(gameOverLabel);
        repaint();
        System.out.println("Game Over!");
    }

    public void restartGame () {
        this.remove(gameOverLabel);
        gameOverLabel = null;
    }

    public void changePauseLabel () {
        if (game.getPaused()) {
            pauseLabel = new JLabel ("Paused", javax.swing.SwingConstants.CENTER);
            pauseLabel.setBackground(new Color (200, 200, 200, 100) );
            pauseLabel.setVisible(true);
            pauseLabel.setOpaque(true);
            pauseLabel.setBounds(0, 60, (int)this.getSize().getWidth(), 60);
            this.add(pauseLabel);
        } else {
            this.remove(pauseLabel);
            pauseLabel = null;
        }
        repaint();
    }

    // PAINTING

    /**
     * This is used to draw everything in the game.
     **/
    public void paintComponent (Graphics g) {
        super.paintComponent(g);

        paintGameBoard(g);
        if (game.getCurrentShape() != null) {
            paintCurrentShape(g);
        }

    }

    /**
     * Paints all the Pieces saved in the Game Board.
     **/
    void paintGameBoard (Graphics g) {

        for (int x = 0; x < game.getRows(); x++) {
            for (int y = 0; y < game.getColumns(); y++) {
                if (game.isPositionOccupied(x, y)) {
                    // draw a rectangle at the appropriate position.
                    drawSinglePiece(g, x, y, game.getPieceAt(x, y).color);
                }
            }
        }

    }

    /**
     * Paints the Pieces currently falling down (the current Shape);
     **/
    void paintCurrentShape (Graphics g) {
        if (game.getCurrentShape() == null) {
            System.out.println("ERROR: GamePanel::paintCurrentShape -- No current Shape to draw!");
            return;
        }

        for (Piece p : game.getCurrentShape().getPieces()) {
            drawSinglePiece(g, p.position.x, p.position.y, p.color);
        }
    }

    /**
     * Paints one single Piece at it's position with the Piece's color and with a small 3d effect (brighter and darker edges).
     **/
    void drawSinglePiece (Graphics g, int x, int y, Color c) {
        // Get the PixelPositions for left, right top and bottom
        int firstPixelX = game.getPieceSize() * x;
        int firstPixelY = game.getPieceSize() * y;
        int lastPixelX = firstPixelX + game.getPieceSize() - 1;
        int lastPixelY = firstPixelY + game.getPieceSize() - 1;

        // Fill the middle of the square.
        g.setColor(c);
        g.fillRect(firstPixelX, firstPixelY, game.getPieceSize() - 1, game.getPieceSize() - 1);
        // Draw the top and left border with a brighter color.
        g.setColor(c.brighter());
        g.drawLine(firstPixelX, firstPixelY, lastPixelX, firstPixelY); 	// top left to top (right-1)
        g.drawLine(firstPixelX, firstPixelY + 1, firstPixelX, lastPixelY);	// (top-1) left to bottom left
        // Draw the bottom and right border with a darker color.
        g.setColor(c.darker());
        g.drawLine(lastPixelX, firstPixelY + 1, lastPixelX, lastPixelY - 1);	// top right to (bottom-1) right
        g.drawLine(firstPixelX + 1, lastPixelY, lastPixelX, lastPixelY);	// bottom (left+1) to bottom right

        //System.out.println("Single Piece drawn at: (" + x + ", " + y + ")");

    }
    
    // TODO: Draw level information.





}







