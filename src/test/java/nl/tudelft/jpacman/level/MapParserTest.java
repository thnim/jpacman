package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.npc.ghost.Blinky;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import nl.tudelft.jpacman.PacmanConfigurationException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This is a test class for MapParser.
 */
@ExtendWith(MockitoExtension.class)
public class MapParserTest {
    @Mock
    private BoardFactory boardFactory;
    @Mock
    private LevelFactory levelFactory;
    @Mock
    private Blinky blinky;

    /**
     * Test for the parseMap method (good map).
     */
    @Test
    public void testParseMapGood() {
        MockitoAnnotations.initMocks(this);
        assertNotNull(boardFactory);
        assertNotNull(levelFactory);
        Mockito.when(levelFactory.createGhost()).thenReturn(blinky);
        MapParser mapParser = new MapParser(levelFactory, boardFactory);
        ArrayList<String> map = new ArrayList<>();

        map.add("############");
        map.add("#P        G#");
        map.add("############");

        mapParser.parseMap(map);

        final int walls   = countChar(map, '#');
        final int spaces  = countChar(map, ' ');
        final int players = countChar(map, 'P');
        final int ghosts  = countChar(map, 'G');
        final int pellets = countChar(map, '.');


        Mockito.verify(levelFactory, Mockito.times(1)).createGhost();
        Mockito.verify(levelFactory, Mockito.times(ghosts)).createGhost();
        Mockito.verify(levelFactory, Mockito.times(pellets)).createPellet();
        Mockito.verify(boardFactory, Mockito.times(walls)).createWall();
        final int totalGround = spaces + players + ghosts + pellets;
        Mockito.verify(boardFactory, Mockito.times(totalGround))
            .createGround();
    }

    /**
     * Counts occurrences of a character across all lines of the map.
     *
     * @param lines Map lines.
     * @param ch    Character to count.
     * @return Number of occurrences.
     */
    private static int countChar(List<String> lines, char ch) {
        int n = 0;
        for (String s : lines) {
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == ch) {
                    n++;
                }
            }
        }
        return n;
    }

    /**
     * Test for the parseMap method (bad map).
     */
    @Test
    public void testParseMapWrong1() {
        PacmanConfigurationException thrown =
            assertThrows(PacmanConfigurationException.class, () -> {
                MockitoAnnotations.initMocks(this);
                assertNotNull(boardFactory);
                assertNotNull(levelFactory);

                MapParser mapParser = new MapParser(levelFactory, boardFactory);
                ArrayList<String> map = new ArrayList<>();

                map.add("####");
                map.add("###");

                mapParser.parseMap(map);
            });

        assertEquals("Input text lines are not of equal width.", thrown.getMessage());

        Mockito.verifyZeroInteractions(levelFactory, boardFactory);
    }


}
