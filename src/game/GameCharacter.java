package game;

/**
 * 游戏角色抽象父类
 * 知识点：第11章继承、抽象类、抽象方法、权限修饰符
 *        第4章数组（装备）
 */
public abstract class GameCharacter {
    private String name;       // 角色名称
    private int hp;            // 生命值
    private int maxHp;         // 最大生命值
    private int attack;        // 攻击力
    private int defense;       // 防御力
    private int level;         // 等级

    public GameCharacter() {
    }

    public GameCharacter(String name, int hp, int attack, int defense, int level) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;  // 初始化时maxHp = hp
        this.attack = attack;
        this.defense = defense;
        this.level = level;
    }

    /**
     * 普通攻击 - 子类可以重写
     * @param target 攻击目标
     * @return 造成的伤害值
     */
    public abstract int normalAttack(GameCharacter target);

    /**
     * 技能攻击 - 子类必须实现
     * @param target 攻击目标
     * @return 造成的伤害值
     */
    public abstract int skillAttack(GameCharacter target);

    /**
     * 承受伤害
     * @param damage 伤害值
     */
    public void takeDamage(int damage) {
        // 实际伤害 = 攻击伤害 - 防御力（最小为1）
        int actualDamage = damage - defense;
        if (actualDamage < 1) {
            actualDamage = 1;
        }
        this.hp -= actualDamage;
        if (this.hp < 0) {
            this.hp = 0;
        }
        System.out.println(this.name + " 受到 " + actualDamage + " 点伤害，剩余血量: " + this.hp + "/" + this.maxHp);
    }

    /**
     * 判断是否死亡
     */
    public boolean isDead() {
        return hp <= 0;
    }

    /**
     * 打印血条
     * 知识点：第3章for循环、第12章StringBuilder
     */
    public void printHealthBar() {
        int barLength = 20; // 血条总长度20个字符
        int filled = (int) ((double) hp / maxHp * barLength);
        if (filled < 0) filled = 0;
        if (filled > barLength) filled = barLength;

        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < barLength; i++) {
            if (i < filled) {
                bar.append("█");
            } else {
                bar.append(" ");
            }
        }
        bar.append("]");
        System.out.println(name + " HP:" + hp + "/" + maxHp + " " + bar.toString());
    }

    // ========== get/set方法 ==========
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
