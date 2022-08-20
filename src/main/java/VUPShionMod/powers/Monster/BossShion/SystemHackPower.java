package VUPShionMod.powers.Monster.BossShion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Unique.SavePlayerPowersAction;
import VUPShionMod.powers.AbstractShionPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class SystemHackPower extends SavePowerPower {
    public static final String POWER_ID = VUPShionMod.makeID(SystemHackPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public SystemHackPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        updateDescription();
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("VUPShionMod/img/powers/DelayAvatarPower128.png"), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("VUPShionMod/img/powers/DelayAvatarPower48.png"), 0, 0, 48, 48);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();

        if(!this.endingEffect)
        addToBot(new SavePlayerPowersAction(this));
    }


    @Override
    public void atStartOfTurnPostDraw() {
        if (justApplied) {
            justApplied = false;

            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    if (!AbstractDungeon.player.hand.group.isEmpty()) {
                        AbstractCard card = AbstractDungeon.player.hand.getRandomCard(AbstractDungeon.miscRng);
                        AbstractDungeon.player.hand.group.remove(card);
                        AbstractDungeon.getCurrRoom().souls.remove(card);
                        card.freeToPlayOnce = true;
                        AbstractDungeon.player.limbo.group.add(card);
                        card.current_y = -200.0F * Settings.scale;
                        card.target_x = Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
                        card.target_y = Settings.HEIGHT / 2.0F;
                        card.targetAngle = 0.0F;
                        card.lighten(false);
                        card.drawScale = 0.12F;
                        card.targetDrawScale = 0.75F;
                        card.applyPowers();
                        addToTop(new NewQueueCardAction(card, (AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(
                                null, true, AbstractDungeon.miscRng), false, true));
                        addToTop(new UnlimboAction(card));
                        if (!Settings.FAST_MODE) {
                            addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                        } else {
                            addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                        }
                    }
                    isDone = true;
                }
            });

            addToBot(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, 1, true));
            addToBot(new ExhaustAction(1, true, false, false));


            return;
        }

        flash();
        this.endingEffect =true;
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if(!playerPowersToSave.isEmpty()){
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
