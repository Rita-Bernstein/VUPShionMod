package VUPShionMod.powers.Monster.TimePortal;

import VUPShionMod.VUPShionMod;
import VUPShionMod.character.WangChuan;
import VUPShionMod.powers.AbstractShionPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ContortTimePower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(ContortTimePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ContortTimePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        this.isTurnBased = false;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/ContortTimePower128.png")), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/ContortTimePower32.png")), 0, 0, 32, 32);

    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        flash();

        AbstractPlayer trueTarget = AbstractDungeon.player;
        if (AbstractDungeon.player instanceof WangChuan) {
            if (((WangChuan) AbstractDungeon.player).shionHelper != null)
                if (!((WangChuan) AbstractDungeon.player).shionHelper.halfDead)
                    trueTarget = ((WangChuan) AbstractDungeon.player).shionHelper;
        }

        addToBot(new DamageAction(trueTarget, new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }


}