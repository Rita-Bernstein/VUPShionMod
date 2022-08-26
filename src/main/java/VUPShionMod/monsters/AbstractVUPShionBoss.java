package VUPShionMod.monsters;

import VUPShionMod.monsters.Rita.AbstractMonsterIntent;
import basemod.abstracts.CustomMonster;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;

public abstract class AbstractVUPShionBoss extends CustomMonster {
    protected AbstractMonsterIntent monsterIntent;
    public AbstractStance stance = new NeutralStance();
    public float particleTimer = 0.0F, particleTimer2 = 0.0F;


    public AbstractVUPShionBoss(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, offsetX, offsetY);
    }

    public AbstractVUPShionBoss(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY, boolean ignoreBlights) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, offsetX, offsetY, ignoreBlights);
    }

    public AbstractVUPShionBoss(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl);
    }

}
