package VUPShionMod.powers.Monster.BossShion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Unique.SavePlayerPowersAction;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class MagicDisorderPower extends SavePowerPower {
    public static final String POWER_ID = VUPShionMod.makeID(MagicDisorderPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public MagicDisorderPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        updateDescription();
        loadShionRegion("DelayAvatarPower");
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();

        if (!this.endingEffect)
            addToBot(new SavePlayerPowersAction(this));
    }


    @Override
    public void atStartOfTurnPostDraw() {
        if (justApplied) {
            justApplied = false;

            flash();
            addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(null, 30, DamageInfo.DamageType.THORNS),
                    AbstractGameAction.AttackEffect.FIRE));

            return;
        }

        flash();
        this.endingEffect = true;
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (!playerPowersToSave.isEmpty()) {
                    AbstractDungeon.player.powers.addAll(playerPowersToSave);
                    playerPowersToSave.clear();
                }
                if (!playerPowers.isEmpty()) {
                    for (int i = playerPowers.size() - 1; i >= 0; i--) {
                        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, playerPowers.get(i)));
                    }
                }
                playerPowers.clear();
                isDone = true;
            }
        });


        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount2, this.amount);
    }

}
