package VUPShionMod.powers.Monster.TimePortal;

import VUPShionMod.VUPShionMod;
import VUPShionMod.character.WangChuan;
import VUPShionMod.powers.AbstractShionPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Decay;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class SubspacePursuerPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(SubspacePursuerPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public SubspacePursuerPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.amount2 = 15;
        updateDescription();
        this.isTurnBased = false;
        loadShionRegion("ContortTimePower");

    }


    @Override
    public void preEndOfRound() {
        AbstractPlayer trueTarget = AbstractDungeon.player;
        if (AbstractDungeon.player instanceof WangChuan) {
            if (((WangChuan) AbstractDungeon.player).shionHelper != null)
                if (!((WangChuan) AbstractDungeon.player).shionHelper.halfDead)
                    trueTarget = ((WangChuan) AbstractDungeon.player).shionHelper;
        }

        flash();
        for (int i = 0; i < this.amount2; i++)
            addToBot(new DamageAction(trueTarget, new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS),
                    AbstractGameAction.AttackEffect.BLUNT_HEAVY, true));
    }

    @Override
    public void updateDescription() {
        this.description = String.format(this.amount2 > 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[0], this.amount, amount2);
    }

}
