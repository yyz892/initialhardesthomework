package game;

import java.util.Random;

/**
 * 敌人类 - 继承自GameCharacter
 * 知识点：第11章继承、方法重写、多态
 *        第10章枚举
 */
public class Enemy extends GameCharacter {
    // 敌人类型枚举
    public enum EnemyType {
        GOBLIN("哥布林", 80, 20, 5, 1),
        SKELETON("骷髅兵", 100, 25, 8, 2),
        DARK_KNIGHT("暗黑骑士", 150, 30, 10, 3),
        DRAGON("恶龙", 200, 40, 15, 4),
        BOSS("魔王", 300, 50, 20, 5);

        private String displayName;
        private int baseHp;
        private int baseAttack;
        private int baseDefense;
        private int baseLevel;

        EnemyType(String displayName, int baseHp, int baseAttack, int baseDefense, int baseLevel) {
            this.displayName = displayName;
            this.baseHp = baseHp;
            this.baseAttack = baseAttack;
            this.baseDefense = baseDefense;
            this.baseLevel = baseLevel;
        }

        public String getDisplayName() { return displayName; }
        public int getBaseHp() { return baseHp; }
        public int getBaseAttack() { return baseAttack; }
        public int getBaseDefense() { return baseDefense; }
        public int getBaseLevel() { return baseLevel; }
    }

    private EnemyType type;
    private int scoreValue;  // 击败该敌人获得的积分

    public Enemy(EnemyType type) {
        super(type.getDisplayName(),
              type.getBaseHp(),
              type.getBaseAttack(),
              type.getBaseDefense(),
              type.getBaseLevel());
        this.type = type;
        // 积分 = 等级 * 100
        this.scoreValue = type.getBaseLevel() * 100;
    }

    @Override
    public int normalAttack(GameCharacter target) {
        Random r = new Random();
        double multiplier = 0.8 + r.nextDouble() * 0.4;
        int damage = (int) (getAttack() * multiplier);
        System.out.println(getName() + " 对 " + target.getName() + " 使用普通攻击！基础伤害: " + damage);
        return damage;
    }

    @Override
    public int skillAttack(GameCharacter target) {
        Random r = new Random();
        // 敌人的技能攻击更强一些
        double multiplier = 1.5 + r.nextDouble() * 0.5;
        int damage = (int) (getAttack() * multiplier);

        // 不同类型敌人有不同的技能名称
        String skillName;
        switch (type) {
            case GOBLIN:
                skillName = "【偷袭】";
                break;
            case SKELETON:
                skillName = "【骨刺攻击】";
                break;
            case DARK_KNIGHT:
                skillName = "【暗影斩】";
                break;
            case DRAGON:
                skillName = "【龙息】";
                break;
            case BOSS:
                skillName = "【末日审判】";
                break;
            default:
                skillName = "【技能攻击】";
                break;
        }
        System.out.println("💀 " + getName() + " 对 " + target.getName() + " 释放" + skillName + "！伤害: " + damage);
        return damage;
    }

    /**
     * 敌人AI：决定使用普通攻击还是技能攻击
     * 知识点：第3章if判断、Random随机
     */
    public int aiAttack(GameCharacter target) {
        Random r = new Random();
        // 40%概率使用技能攻击，60%概率普通攻击
        if (r.nextInt(100) < 40) {
            return skillAttack(target);
        } else {
            return normalAttack(target);
        }
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public EnemyType getType() {
        return type;
    }
}
