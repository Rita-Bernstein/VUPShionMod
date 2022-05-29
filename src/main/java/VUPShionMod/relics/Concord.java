package VUPShionMod.relics;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.Liyezhu.PsychicPower;
import VUPShionMod.stances.PrayerStance;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class Concord extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID("Concord");
    public static final String IMG_PATH = "img/relics/Inhibitor.png";
    private static final String OUTLINE_PATH = "img/relics/outline/Inhibitor.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public Concord() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.STARTER, LandingSound.CLINK);
        this.counter = 10;
        setDescriptionAfterLoading();
    }

    @Override
    public String getUpdatedDescription() {
        if (50 - this.counter > 0)
            return String.format(DESCRIPTIONS[0], 50 - this.counter, 50 - this.counter);
        else
            return String.format(DESCRIPTIONS[1], this.counter - 50, this.counter - 50);
    }

    public void setDescriptionAfterLoading() {
        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }


    @Override
    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
        if(info.type == DamageInfo.DamageType.NORMAL) {
            if (50 - this.counter > 0)
                return (int) Math.floor(damageAmount * ((100 - (50 - this.counter)) * 0.01f));
            else
                return (int) Math.floor(damageAmount * ((100 + (this.counter - 50))) * 0.01f);
        }
        return damageAmount;
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(info.type == DamageInfo.DamageType.NORMAL) {
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
