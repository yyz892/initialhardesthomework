package game;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 游戏主类 - 游戏入口、主页面、游戏流程控制
 * 知识点：第13章项目实战综合运用（所有章节知识的综合）
 *
 * 项目依赖的所有知识点回顾：
 * 第1章: JDK/IDEA环境、注释、HelloWorld
 * 第2章: 变量、数据类型、运算符、Scanner键盘录入、类型转换、字符串拼接
 * 第3章: if/else、switch、for/while循环、break/continue
 * 第4章: 数组
 * 第5章: 方法定义、调用、重载
 * 第6章: Java运行机制、内存分配原理
 * 第8章: 面向对象、类与对象、private、this、构造方法、JavaBean
 * 第10章: static静态、final常量、枚举enum
 * 第11章: 继承extends、super、方法重写@Override、抽象类abstract
 * 第12章: String方法、StringBuilder、ArrayList集合
 */
public class Game {
    private static Scanner sc = new Scanner(System.in);
    private static Hero hero;                      // 当前登录的英雄
    private static ArrayList<Enemy> enemyList;     // 敌人列表

    public static void main(String[] args) {
        // 初始化敌人列表
        initEnemies();

        // 主循环
        while (true) {
            showMainMenu();
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    // 注册
                    UserManager.register();
                    break;

                case 2:
                    // 登录
                    User user = UserManager.login();
                    if (user != null) {
                        // 登录成功，创建英雄
                        hero = createHero(user);
                        // 进入游戏主界面
                        gameLobby();
                    }
                    break;

                case 3:
                    // 退出游戏
                    System.out.println("感谢游玩，再见！");
                    return;

                default:
                    System.out.println("输入无效，请重新选择！");
            }
        }
    }

    /**
     * 初始化敌人列表
     * 知识点：第12章ArrayList、第10章枚举
     */
    private static void initEnemies() {
        enemyList = new ArrayList<>();
        // 使用枚举创建不同类型的敌人
        enemyList.add(new Enemy(Enemy.EnemyType.GOBLIN));
        enemyList.add(new Enemy(Enemy.EnemyType.SKELETON));
        enemyList.add(new Enemy(Enemy.EnemyType.DARK_KNIGHT));
        enemyList.add(new Enemy(Enemy.EnemyType.DRAGON));
        enemyList.add(new Enemy(Enemy.EnemyType.BOSS));
    }

    /**
     * 显示主页面菜单
     * 知识点：第2章字符串拼接、第12章StringBuilder思想
     */
    private static void showMainMenu() {
        System.out.println("\n========================================");
        System.out.println("    🏰 勇者斗恶龙 - 文字RPG游戏");
        System.out.println("========================================");
        System.out.println("  1. 用户注册");
        System.out.println("  2. 用户登录");
        System.out.println("  3. 退出游戏");
        System.out.println("========================================");
        System.out.print("请输入选择：");
    }

    /**
     * 创建英雄角色
     * 知识点：第8章构造方法、对象创建
     */
    private static Hero createHero(User user) {
        System.out.println("\n===== 创建英雄 =====");
        System.out.println("玩家 " + user.getNickname() + "，请创建你的英雄！");

        System.out.print("请输入英雄名称：");
        String heroName = sc.next();

        // 初始属性：HP=150, 攻击=30, 防御=10, 等级=1, 技能消耗=20
        Hero newHero = new Hero(heroName, 150, 30, 10, 1, 20);
        System.out.println("\n英雄创建成功！");
        System.out.println("【英雄信息】");
        System.out.println("  名称: " + newHero.getName());
        System.out.println("  生命: " + newHero.getHp() + "/" + newHero.getMaxHp());
        System.out.println("  攻击: " + newHero.getAttack());
        System.out.println("  防御: " + newHero.getDefense());
        System.out.println("  等级: " + newHero.getLevel());
        System.out.println();
        return newHero;
    }

    /**
     * 游戏大厅 - 登录后进入
     */
    private static void gameLobby() {
        while (true) {
            System.out.println("\n========================================");
            System.out.println("    🏰 游戏大厅");
            System.out.println("========================================");
            System.out.println("  英雄: " + hero.getName() + " | HP:" + hero.getHp() + "/" + hero.getMaxHp()
                    + " | 攻击:" + hero.getAttack() + " | 防御:" + hero.getDefense()
                    + " | 积分:" + hero.getTotalScore());
            System.out.println("----------------------------------------");
            System.out.println("  1. 挑战敌人（开始战斗）");
            System.out.println("  2. 属性增强");
            System.out.println("  3. 查看英雄详情");
            System.out.println("  4. 当前战绩");
            System.out.println("  5. 返回主菜单");
            System.out.println("========================================");
            System.out.print("请输入选择：");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    // 选择敌人并战斗
                    Enemy enemy = BattleSystem.selectEnemy(enemyList);
                    if (enemy != null) {
                        // 战前属性增强
                        System.out.println("\n>> 战前准备：获得随机属性增强！");
                        hero.enhanceAttributes();

                        boolean win = BattleSystem.startBattle(hero, enemy);
                        if (win) {
                            // 如果敌人被击败，生成新敌人替换
                            refreshEnemy(enemy);
                        } else {
                            // 战斗失败，回到主菜单
                            System.out.println("\n按Enter键返回主菜单...");
                            sc.nextLine();
                            sc.nextLine();
                            return;
                        }
                    }
                    break;

                case 2:
                    // 属性增强
                    hero.enhanceAttributes();
                    break;

                case 3:
                    // 查看英雄详情
                    showHeroDetail();
                    break;

                case 4:
                    // 当前战绩
                    showScoreBoard();
                    break;

                case 5:
                    // 返回主菜单
                    System.out.println("返回主菜单...");
                    return;

                default:
                    System.out.println("输入无效，请重新选择！");
            }
        }
    }

    /**
     * 击败敌人后刷新新敌人
     */
    private static void refreshEnemy(Enemy defeated) {
        // 创建一个等级+3的更强敌人替换
        Enemy.EnemyType[] types = Enemy.EnemyType.values();
        int currentIndex = defeated.getType().ordinal();

        // 如果打的不是最后一个BOSS，则生成同级别或更高级的敌人
        if (currentIndex < types.length - 1) {
            enemyList.remove(defeated);
            enemyList.add(currentIndex, new Enemy(types[currentIndex + 1]));
            System.out.println("\n⚠️ 新的敌人出现了：" + types[currentIndex + 1].getDisplayName() + "！");
        } else {
            System.out.println("\n🏆 你已经击败了所有敌人！");
        }
    }

    /**
     * 查看英雄详情
     */
    private static void showHeroDetail() {
        System.out.println("\n===== 英雄详情 =====");
        System.out.println("名称: " + hero.getName());
        System.out.println("生命: " + hero.getHp() + "/" + hero.getMaxHp());
        hero.printHealthBar();
        System.out.println("攻击力: " + hero.getAttack());
        System.out.println("防御力: " + hero.getDefense());
        System.out.println("等级: " + hero.getLevel());
        System.out.println("累计积分: " + hero.getTotalScore());
    }

    /**
     * 查看排行榜（当前只有一个英雄，展示战绩）
     */
    private static void showScoreBoard() {
        System.out.println("\n===== 当前战绩 =====");
        System.out.println("英雄: " + hero.getName());
        System.out.println("积分: " + hero.getTotalScore());

        // 根据积分评定段位
        String rank;
        if (hero.getTotalScore() >= 1000) {
            rank = "💎 传说勇者";
        } else if (hero.getTotalScore() >= 600) {
            rank = "🥇 黄金勇士";
        } else if (hero.getTotalScore() >= 300) {
            rank = "🥈 白银战士";
        } else if (hero.getTotalScore() >= 100) {
            rank = "🥉 青铜冒险者";
        } else {
            rank = "🌱 新手冒险者";
        }
        System.out.println("段位: " + rank);

        // 五星评分
        int stars = hero.getTotalScore() / 200 + 1;
        if (stars > 5) stars = 5;
        System.out.print("星级: ");
        for (int i = 0; i < stars; i++) {
            System.out.print("⭐");
        }
        System.out.println();
    }
}
