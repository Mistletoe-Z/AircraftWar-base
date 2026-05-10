package edu.hitsz.application;

import edu.hitsz.FactoryPattern.EnemySpawner;
import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.dao.Record;
import edu.hitsz.dao.RecordDao;
import edu.hitsz.dao.RecordDaoImpl;
import edu.hitsz.observer.Observer;
import edu.hitsz.props.AbstractProp;
import edu.hitsz.props.Bombbonus;
import edu.hitsz.props.Frozenbonus;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 * @author hitsz
 */
public class Game extends JPanel {

    private int backGroundTop = 0;

    //调度器, 用于定时任务调度
    private final Timer timer;
    //时间间隔(ms)，控制刷新频率
    private final int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<AbstractAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    //道具
    private final List<AbstractProp> props;

    //屏幕中出现的敌机最大数量
    private final int enemyMaxNumber = 5;

    //敌机生成周期
    protected double enemySpawnCycle  =  20;
    private int enemySpawnCounter = 0;

    //英雄机和敌机射击周期
    protected double shootCycle = 10;
    private int shootCounter = 0;

    //当前玩家分数
    private int score = 0;

    //游戏结束标志
    private boolean gameOverFlag = false;
    // 🌟 核心新增：记录当前游戏难度
    private String difficulty;
    // 🌟 核心新增：标记是否已经弹出过结束对话框，防止重复弹窗
    private boolean isGameOverDialogShown = false;
    // 🌟 核心新增：用于存放根据难度选定的背景图片
    private Image bgImage;
    //游戏音乐


    public Game(String difficulty) {
        this.difficulty = difficulty;

        if ("EASY".equals(difficulty)) {
            this.bgImage = ImageManager.BACKGROUND_IMAGE;
        } else if ("NORMAL".equals(difficulty)) {
            this.bgImage = ImageManager.BACKGROUND_IMAGE2; // 确保 ImageManager 里有定义这个变量
        } else if ("HARD".equals(difficulty)) {
            this.bgImage = ImageManager.BACKGROUND_IMAGE3;
        } else {
            this.bgImage = ImageManager.BACKGROUND_IMAGE;
        }

        heroAircraft = HeroAircraft.getInstance();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

        this.timer = new Timer("game-action-timer", true);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {
        AudioManager.getInstance().playNormalBGM();

        // 定时任务：绘制、对象产生、碰撞判定、及结束判定
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                enemySpawnCounter++;
                if (enemySpawnCounter >=enemySpawnCycle) {
                    enemySpawnCounter = 0;
                    // 产生普通敌机
                    if (enemyAircrafts.size() < enemyMaxNumber) {
                        enemyAircrafts.add(EnemySpawner.generateRandomEnemy(score));// 工厂模式
                        //enemyAircrafts.add(AircraftFactory.createAircraft(AircraftFactory.RandomType(score))); //简单工厂模式
                    }
                }

                // 飞机发射子弹
                shootAction();
                // 子弹移动
                bulletsMoveAction();
                // 飞机移动
                aircraftsMoveAction();
                // 撞击检测
                crashCheckAction();
                //道具移动
                propsMoveAction();
                // 后处理
                postProcessAction();
                // 重绘界面
                repaint();
                // 游戏结束检查
                checkResultAction();
            }
        };
        // 以固定延迟时间进行执行：本次任务执行完成后，延迟 timeInterval 再执行下一次
        timer.schedule(task,0,timeInterval);

    }

    //***********************
    //      Action 各部分
    //***********************

    private void shootAction() {
        shootCounter++;
        if (shootCounter >= shootCycle) {
            shootCounter = 0;
            //英雄机射击
            heroBullets.addAll(heroAircraft.shoot());
            // TODO 敌机射击
            for(AbstractAircraft enemyAircraft : enemyAircrafts){
                enemyBullets.addAll(enemyAircraft.shoot());
            }
        }
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propsMoveAction(){
        for(AbstractProp prop : props){
            prop.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // TODO 敌机子弹攻击英雄机
        for(BaseBullet bullet: enemyBullets){
            if(bullet.notValid()){
                continue;//如果这颗子弹失效了，那就别管他了，去看下一颗子弹
            }
            //如果撞击到了子弹
            if(heroAircraft.crash(bullet)){
                AudioManager.getInstance().bullethitSound();
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }
        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    AudioManager.getInstance().bullethitSound();
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // TODO 获得分数，产生道具补给
                        score += 10;
                        props.addAll(enemyAircraft.dropProps());
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        for (AbstractProp prop : props) {
            if (prop.notValid()) continue;

            if (heroAircraft.crash(prop)) {
                // 🌟 如果吃到的是炸弹
                if (prop instanceof Bombbonus) {
                    // 把屏幕上的敌机全部拉进粉丝群
                    for (AbstractAircraft enemy : enemyAircrafts) {
                        ((Bombbonus) prop).addObserver(enemy);
                    }
                    // 把屏幕上的敌方子弹也拉进粉丝群
                    for (BaseBullet bullet : enemyBullets) {
                        ((Bombbonus) prop).addObserver((Observer) bullet);
                    }
                }
                if (prop instanceof Frozenbonus) {
                    // 把屏幕上的敌机全部拉进粉丝群
                    for (AbstractAircraft enemy : enemyAircrafts) {
                        ((Frozenbonus) prop).addObserver(enemy);
                    }
                    // 把屏幕上的敌方子弹也拉进粉丝群
                    for (BaseBullet bullet : enemyBullets) {
                        ((Frozenbonus) prop).addObserver((Observer) bullet);
                    }
                }
                // 触发道具生效逻辑，把英雄机传进去
                AudioManager.getInstance().playGetSupplySound();
                prop.active(heroAircraft,enemyAircrafts);
                // 道具被吃掉，标记消失
                prop.vanish();
                // 加分 (可选)
                score += 20;
            }
        }

    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 删除无效的道具
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        // Todo: 删除无效道具
        props.removeIf(AbstractProp::notValid);
    }

    /**
     * 检查游戏是否结束，若结束：关闭线程池
     */
    private void checkResultAction(){
        // 游戏结束检查英雄机是否存活
        if (heroAircraft.getHp() <= 0) {
            AudioManager.getInstance().gameoverSound();
            timer.cancel(); // 取消定时器并终止所有调度任务
            gameOverFlag = true;
            System.out.println("Game Over!");

            if (!isGameOverDialogShown) {
                isGameOverDialogShown = true; // 标记为已弹窗

                // 1. 弹出输入名字对话框
                String userName = JOptionPane.showInputDialog(
                        Main.cardPanel,
                        "游戏结束，你的得分为：" + score + "。\n请输入名字记录得分：",
                        "输入",
                        JOptionPane.QUESTION_MESSAGE
                );

                // 2. 如果玩家点了确定并输入了名字，通过 DAO 存入文件
                if (userName != null && !userName.trim().isEmpty()) {
                    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("MM-dd HH:mm");
                    String time = formatter.format(new java.util.Date());
                    edu.hitsz.dao.Record record = new edu.hitsz.dao.Record(userName, score, time);

                    edu.hitsz.dao.RecordDao recordDao = new edu.hitsz.dao.RecordDaoImpl(this.difficulty);
                    recordDao.addRecord(record);
                }

                // 3. 实例化排行榜界面（把难度传进去读对应文件）
                ScoreBoard scoreBoard = new ScoreBoard(this.difficulty);

                // 4. 将排行榜加到幻灯片幕布里，并切换显示！
                Main.cardPanel.add(scoreBoard.getMainPanel(), "scoreBoard");
                Main.cardLayout.show(Main.cardPanel, "scoreBoard");
            }
            AudioManager.getInstance().stopNormalBGM();
        }
    };

    //***********************
    //      Paint 各部分
    //***********************
    /**
     * 重写 paint方法
     * 通过重复调用paint方法，实现游戏动画
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(this.bgImage, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(this.bgImage, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, enemyAircrafts);

        // Todo: 绘制道具
        paintImageWithPositionRevised(g, props);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.isEmpty()) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(Color.RED);
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE: " + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE: " + this.heroAircraft.getHp(), x, y);
    }

}
