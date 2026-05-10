package edu.hitsz.aircraft;

import edu.hitsz.basic.AbstractFlyingObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest extends AbstractFlyingObject {
    private HeroAircraft hero;
    @BeforeEach
    void setUp() {
        hero = HeroAircraft.getInstance();
    }

    @Test
    void upHp() {
        hero.decreaseHp(50);
        hero.UpHp(20);
        assertEquals(70, hero.getHp()); // 假设初始100，减50加20应为70
    }

    @Test
    void testCrash() {
        // 创建一个位置重叠的敌机
        AbstractAircraft enemy = new MobEnemy(hero.getLocationX(), hero.getLocationY(), 0, 0, 10);
        assertTrue(hero.crash(enemy)); // 坐标重叠，预期返回 true [cite: 885]
    }

    @Test
    void decreaseHp() {
        int initialHp = hero.getHp();
        hero.decreaseHp(30);
        assertEquals(initialHp - 30, hero.getHp());
    }
}