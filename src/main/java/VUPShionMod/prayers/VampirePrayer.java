package VUPShionMod.prayers;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.AbstractShionPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Reaper;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.ReaperEffect;

public class VampirePrayer extends AbstractPrayer {
    public static final String Prayer_ID = VUPShionMod.makeID(VampirePrayer.class.getSimpleName());
    private static final OrbStrings prayerStrings = CardCrawlGame.languagePack.getOrbString(Prayer_ID);
    public static final String NAME = prayerStrings.NAME;
    public static final String[] DESCRIPTIONS = prayerStrings.DESCRIPTION;


    private final AbstractCard card = new Reaper();

    public VampirePrayer(int turns, int amount) {
        this.ID = Prayer_ID;
        this.name = NAME;
        this.turns = turns;
        this.amount = amount;
        updateDescription();
        this.region48 = AbstractShionPower.shionAtlas.findRegion("48/VampireFormPower");
        this.region128 = AbstractShionPower.shionAtlas.findRegion("128/VampireFormPower");
        card.baseDamage = amount;
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

    @Override
    public void use() {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                addToTop(new AbstractGameAction() {
                    @Override
                    public void update() {
                        card.applyPowers();
                        addToTop(new VampireDamageAllEnemiesAction(AbstractDungeon.player,
                                card.multiDamage, card.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
                        isDone = true;
                    }
                });
                addToTop(new VFXAction(new ReaperEffect()));
                isDone = true;
            }
        });
    }

    @Override
    public AbstractPrayer makeCopy() {
        return new VampirePrayer(this.turns, this.amount);
    }
}
