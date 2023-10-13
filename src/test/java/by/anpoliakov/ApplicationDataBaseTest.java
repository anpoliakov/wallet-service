package by.anpoliakov;

import by.anpoliakov.domain.Player;
import by.anpoliakov.infrastructure.ApplicationDataBase;
import org.junit.Before;

/**
 * Не успеваю покрыть тестами код, прошу ещё немного времени :)
 * Знаю что хорошая практика - это идти от тестов к основному коду
 * */
public class ApplicationDataBaseTest {
    ApplicationDataBase applicationDataBase;

    @Before
    public void getInstance(){
        applicationDataBase = ApplicationDataBase.getInstance();
    }

    public void shouldAddedPlayer(){
        Player player = new Player("TestLogin", "TestPassword");
        applicationDataBase.add(player);
    }
}
