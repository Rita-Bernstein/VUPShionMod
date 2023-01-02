package VUPShionMod.powers.Monster.BossShion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.shion.Boss.FinFunnelPursuit;
import VUPShionMod.powers.AbstractShionPower;
import VUPShionMod.powers.Shion.PursuitPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class BossAttackLoaderPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(BossAttackLoaderPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public BossAttackLoaderPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadShionRegion("AttackOrderPower");
    }

    @Override
    public void onShuffle() {
        flash();
        addToBot(new MakeTempCardInHandAction(new FinFunnelPursuit()));
    }


    @Override
    public void updateDescription() {
        this.description = String.format(this.amount > 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[0], this.amount);
    }

}
