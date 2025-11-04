package nl.tudelft.jpacman.board;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BoardTest {
    /**
     * A valid 1×1 board returns the same square at (0,0).
     */
    @Test
    void validOneByOneBoard() {
        Square s = new BasicSquare();
        Square[][] grid = { { s } };

        Board b = new Board(grid);

        assertThat(b.squareAt(0, 0)).isSameAs(s);
    }

//    /**
//     * 1×1 board whose only cell is null.
//     */
//    @Test
//    void squareAtNullCell() {
//        Board b = new Board(new Square[][]{{ null }});
//        assertThat(b.squareAt(0, 0)).isNull();
//    }
}
