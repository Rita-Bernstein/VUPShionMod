package VUPShionMod.powers.Monster.BossShion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.character.WangChuan;
import VUPShionMod.powers.AbstractShionPower;
import VUPShionMod.powers.Shion.PursuitPower;
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

public class BossChainPursuitPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(BossChainPursuitPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public BossChainPursuitPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadShionRegion("AttackOrderPower");

    }

    @Override
    public void atEndOfRound() {
        flash();
        addToBot(new ApplyPowerAction(AbstractDungeon.player, this.owner, new PursuitPower(AbstractDungeon.player, this.owner, this.amount)));
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

}
