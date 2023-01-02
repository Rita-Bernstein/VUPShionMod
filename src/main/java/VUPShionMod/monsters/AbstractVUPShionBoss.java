package VUPShionMod.monsters;

import VUPShionMod.monsters.Rita.AbstractMonsterIntent;
import basemod.abstracts.CustomMonster;
import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;

public abstract class AbstractVUPShionBoss extends CustomMonster {
    protected AbstractMonsterIntent monsterIntent;
    public AbstractStance stance = new NeutralStance();
    public float particleTimer = 0.0F, particleTimer2 = 0.0F;


    public AbstractVUPShionBoss(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, getScale(hb_h), imgUrl, offsetX, offsetY);
    }

    public AbstractVUPShionBoss(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY, boolean ignoreBlights) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, getScale(hb_h), imgUrl, offsetX, offsetY, ignoreBlights);
    }

    public AbstractVUPShionBoss(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, getScale(hb_h), imgUrl);
    }


    public static void attackAction(AbstractMonster m) {
        if (Settings.FAST_MODE)
            AbstractDungeon.actionManager.addToBottom(new AnimateFastAttackAction(m));
        else
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(m));
    }

    protected static float getScale(float scale) {
        if (Settings.isFourByThree)
            return scale * 1.3f;

        if (Settings.isSixteenByTen) {
            return scale * 1.12f;
        }

        return scale;
    }
}
