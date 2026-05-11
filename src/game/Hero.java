package game;

import java.util.Random;

/**
 * 英雄类 - 继承自GameCharacter
 * 知识点：第11章继承extends、方法重写@Override、super关键字
 */
public class Hero extends GameCharacter {
    private int skillMpCost;    // 技能消耗法力值
    private int totalScore;     // 累计积分

    public Hero() {
    }

    public Hero(String name, int hp, int attack, int defense, int level, int skillMpCost) {
        super(name, hp, attack, defense, level);
        this.skillMpCost = skillMpCost;
        this.totalScore = 0;
    }

    @Override
    public int normalAttack(GameCharacter target) {
        Random r = new Random();
        // 普通攻击：基础攻击力 * (0.8~1.2的随机系数)
        double multiplier = 0.8 + r.nextDouble() * 0.4;
        int damage = (int) (getAttack() * multiplier);
        System.out.println(getName() + " 对 " + target.getName() + " 使用普通攻击！基础伤害: " + damage);
        return damage;
    }

    @Override
    public int skillAttack(GameCharacter target) {
        Random r = new Random();
        // 技能攻击：基础攻击力 * 1.8 * (0.9~1.1随机系数)
        double multiplier = 1.8 * (0.9 + r.nextDouble() * 0.2);
        int damage = (int) (getAttack() * multiplier);
        System.out.println("⚡ " + getName() + " 对 " + target.getName() + " 释放技能【烈焰斩】！伤害: " + damage);
        System.out.println("   (消耗 " + skillMpCost + " 法力值)");
        return damage;
    }

    /**
     * 战斗后恢复：回复30%最大生命值
     */
    public void recoverAfterBattle() {
        int recoverAmount = (int) (getMaxHp() * 0.3);
        int newHp = getHp() + recoverAmount;
        if (newHp > getMaxHp()) {
            newHp = getMaxHp();
        }
        setHp(newHp);
        System.out.println(getName() + " 战后恢复 " + recoverAmount + " 点生命值！");
    }

    /**
     * 属性增强：随机提升攻击力或生命上限
     */
    public void enhanceAttributes() {
        Random r = new Random();
        int type = r.nextInt(3);
        int boost = 5 + r.nextInt(11); // 5~15随机值
        switch (type) {
            case 0:
                setAttack(getAttack() + boost);
                System.out.println(getName() + " 获得攻击力提升 +" + boost + "！当前攻击力: " + getAttack());
                break;
            case 1:
                setMaxHp(getMaxHp() + boost * 5);
                setHp(getHp() + boost * 5); // 同时恢复等量生命
                System.out.println(getName() + " 获得生命上限提升 +" + (boost * 5) + "！当前生命上限: " + getMaxHp());
                break;
            case 2:
                setDefense(getDefense() + boost / 2);
                System.out.println(getName() + " 获得防御力提升 +" + (boost / 2) + "！当前防御力: " + getDefense());
                break;
        }
    }

    public void addScore(int score) {
        this.totalScore += score;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getSkillMpCost() {
        return skillMpCost;
    }

    public void setSkillMpCost(int skillMpCost) {
        this.skillMpCost = skillMpCost;
    }
}
