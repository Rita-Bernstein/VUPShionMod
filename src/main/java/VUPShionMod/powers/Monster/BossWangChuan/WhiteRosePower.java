package VUPShionMod.powers.Monster.BossWangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.character.WangChuan;
import VUPShionMod.powers.AbstractShionPower;
import VUPShionMod.powers.Wangchuan.FlawlessParryPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

public class WhiteRosePower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(WhiteRosePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private final boolean justApplied = true;

    public WhiteRosePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.amount2 = 4;
        updateDescription();
        this.isTurnBased = false;
        loadShionRegion("DefectPower");
    }


    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.NORMAL)
            addToBot(new ApplyPowerAction(this.owner, this.owner, new PlatedArmorPower(this.owner, this.amount * 2)));
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void atStartOfTurn() {
        flash();
        addToBot(new ApplyPowerAction(this.owner, this.owner, new FlawlessParryPower(this.owner, this.amount)));
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount * 2, amount);
    }

}
