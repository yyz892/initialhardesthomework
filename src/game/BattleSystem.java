package game;

import java.util.Random;

/**
 * 战斗系统 - 处理回合制战斗逻辑
 * 知识点：第3章循环(while)、判断(if/else)、switch
 *        第8章面向对象、方法调用
 */
public class BattleSystem {

    /**
     * 开始一场战斗：英雄 vs 敌人
     * 回合制：英雄先手，然后敌人攻击，交替进行
     * @param hero 英雄对象
     * @param enemy 敌人对象
     * @return 英雄是否获胜
     */
    public static boolean startBattle(Hero hero, Enemy enemy) {
        System.out.println("\n========================================");
        System.out.println("⚔️  战斗开始！");
        System.out.println("========================================");
        System.out.println("【" + hero.getName() + "】 VS 【" + enemy.getName() + "】");
        System.out.println();

        // 战前展示血条
        hero.printHealthBar();
        enemy.printHealthBar();
        System.out.println();

        int round = 1;  // 回合计数

        // 回合制战斗循环
        while (!hero.isDead() && !enemy.isDead()) {
            System.out.println("---------- 第 " + round + " 回合 ----------");

            // ===== 英雄回合 =====
            System.out.println("\n>> 你的回合 <<");
            int heroChoice = getHeroAction();
            int heroDamage;
            if (heroChoice == 1) {
                heroDamage = hero.normalAttack(enemy);
            } else {
                heroDamage = hero.skillAttack(enemy);
            }
            enemy.takeDamage(heroDamage);

            // 检查敌人是否死亡
            if (enemy.isDead()) {
                System.out.println("\n🎉 " + enemy.getName() + " 被击败了！");
                break;
            }

            // ===== 敌人回合 =====
            System.out.println("\n>> " + enemy.getName() + " 的回合 <<");
            int enemyDamage = enemy.aiAttack(hero);
            hero.takeDamage(enemyDamage);

            // 检查英雄是否死亡
            if (hero.isDead()) {
                System.out.println("\n💀 你被 " + enemy.getName() + " 击败了...");
                break;
            }

            // 每回合结束展示血条
            System.out.println();
            hero.printHealthBar();
            enemy.printHealthBar();
            System.out.println();

            round++;
        }

        // 战斗结果
        if (enemy.isDead()) {
            int score = enemy.getScoreValue();
            hero.addScore(score);
            System.out.println("获得积分: +" + score + " (当前总积分: " + hero.getTotalScore() + ")");
            hero.recoverAfterBattle();
            return true; // 英雄获胜
        } else {
            System.out.println("战斗失败！最终积分: " + hero.getTotalScore());
            return false; // 英雄失败
        }
    }

    /**
     * 获取英雄行动选择
     * 知识点：第2章Scanner键盘录入、第3章switch判断
     */
    private static int getHeroAction() {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        int choice;
        while (true) {
            System.out.println("请选择行动：");
            System.out.println("  1. 普通攻击");
            System.out.println("  2. 技能攻击【烈焰斩】(攻击力×1.8)");
            System.out.print("请输入选择(1或2)：");
            choice = sc.nextInt();
            if (choice == 1 || choice == 2) {
                break;
            } else {
                System.out.println("输入无效，请重新选择！");
            }
        }
        return choice;
    }

    /**
     * 从敌人列表中随机选择一个敌人
     * 知识点：第4章数组、Random类
     */
    public static Enemy selectEnemy(java.util.ArrayList<Enemy> enemyList) {
        if (enemyList == null || enemyList.size() == 0) {
            System.out.println("没有可挑战的敌人！");
            return null;
        }

        // 展示所有可挑战的敌人
        System.out.println("\n===== 可挑战的敌人 =====");
        for (int i = 0; i < enemyList.size(); i++) {
            Enemy e = enemyList.get(i);
            System.out.println((i + 1) + ". " + e.getName()
                    + " | HP:" + e.getHp()
                    + " | 攻击:" + e.getAttack()
                    + " | 防御:" + e.getDefense()
                    + " | 等级:" + e.getLevel()
                    + " | 积分:" + e.getScoreValue());
        }
        System.out.println(enemyList.size() + 1 + ". 随机挑战");

        java.util.Scanner sc = new java.util.Scanner(System.in);
        System.out.print("请选择要挑战的敌人编号：");
        int choice = sc.nextInt();

        if (choice >= 1 && choice <= enemyList.size()) {
            return enemyList.get(choice - 1);
        } else {
            // 随机选择
            Random r = new Random();
            int randomIndex = r.nextInt(enemyList.size());
            return enemyList.get(randomIndex);
        }
    }
}
