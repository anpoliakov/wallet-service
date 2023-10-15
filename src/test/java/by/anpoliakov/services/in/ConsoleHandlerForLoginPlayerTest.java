package by.anpoliakov.services.in;

import by.anpoliakov.domain.Player;
import org.junit.Test;

public class ConsoleHandlerForLoginPlayerTest {

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionIfNullPlayer(){
        Player player = null;
        new ConsoleHandlerForLoginPlayer(player);
    }

}