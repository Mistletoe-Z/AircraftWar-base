package edu.hitsz.FactoryPattern;

import edu.hitsz.aircraft.AbstractAircraft;
/**
 * 敌机工厂接口
 */
public interface EnemyFactory {
    /**
     * 制造一架敌机
     * @return 实例化后的敌机对象
     */
    AbstractAircraft createAircraft();
}