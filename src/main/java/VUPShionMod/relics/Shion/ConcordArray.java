package VUPShionMod.relics.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.Codex.TwoAttackPower;
import VUPShionMod.relics.AbstractShionRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class ConcordArray extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(ConcordArray.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/ConcordArray.png";
    private static final String OUTLINE_PATH = "img/relics/outline/ConcordArray.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public ConcordArray() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.STARTER, LandingSound.CLINK);
        this.counter = 10;
        setDescriptionAfterLoading();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void setDescriptionAfterLoading() {
        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        if (50 - this.counter > 0)
            this.tips.add(new PowerTip(DESCRIPTIONS[1], String.format(DESCRIPTIONS[2], 50 - this.counter, 50 - this.counter)));
        else
            this.tips.add(new PowerTip(DESCRIPTIONS[1], String.format(DESCRIPTIONS[3], this.counter - 50, this.counter - 50)));

        this.initializeTips();
    }


    @Override
    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.NORMAL) {
            if (50 - this.counter > 0)
                return (int) Math.floor(damageAmount * ((100 - (50 - this.counter)) * 0.01f));
            else
                return (int) Math.floor(damageAmount * ((100 + (this.counter - 50))) * 0.01f);
        }
        return damageAmount;
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.NORMAL) {
            if (50 - this.counter > 0)
                return (int) Math.floor(damageAmount * ((100 + (50 - this.counter)) * 0.01f));
            else
                return (int) Math.floor(damageAmount * ((100 - (this.counter - 50))) * 0.01f);
        }
        return damageAmount;
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        super.onEnterRoom(room);
        this.counter++;
        if (this.counter > 100)
            this.counter = 100;
        setDescriptionAfterLoading();
    }
}
