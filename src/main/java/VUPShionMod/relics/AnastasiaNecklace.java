package VUPShionMod.relics;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cutscenes.CGlayout;
import VUPShionMod.effects.ShionBossBackgroundEffect;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.monsters.PlagaAMundoMinion;
import VUPShionMod.patches.AbstractPlayerEnum;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.powers.AttackOrderSpecialPower;
import VUPShionMod.powers.BleedingPower;
import VUPShionMod.powers.LifeLinkPower;
import VUPShionMod.powers.PursuitPower;
import VUPShionMod.util.ChargeHelper;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.relics.OnPlayerDeathRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.powers.BerserkPower;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.DieDieDieEffect;


public class AnastasiaNecklace extends CustomRelic implements OnPlayerDeathRelic {
    public static final String ID = VUPShionMod.makeID("AnastasiaNecklace");
    public static final String IMG_PATH = "img/relics/AnastasiaNecklace.png";
    private static final String OUTLINE_PATH = "img/relics/outline/AnastasiaNecklace.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));
    private static final Texture UPGRADE_IMG = new Texture(VUPShionMod.assetPath("img/relics/AnastasiaNecklaceUpgrade.png"));
    private static final RelicStrings relicString = CardCrawlGame.languagePack.getRelicStrings(ID);

    private boolean triggered = false;
    public boolean effectApplied = false;

    private CGlayout cg = new CGlayout();

    public AnastasiaNecklace() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.SPECIAL, LandingSound.CLINK);
        getUpdatedDescription();
        UPGRADE_IMG.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public String getUpdatedDescription() {
        if (this.triggered) {
            return this.DESCRIPTIONS[1] + DESCRIPTIONS[2];
        } else {
            return this.DESCRIPTIONS[0];
        }
    }

    public void setDescriptionAfterLoading() {
        this.description = getUpdatedDescription();
        this.tips.clear();
        if (this.triggered)
            this.tips.add(new PowerTip(this.name, this.description));
        else
            this.tips.add(new PowerTip(relicString.DESCRIPTIONS[3], this.description));
        this.initializeTips();
    }

    public void triggerRelic() {
        if (!triggered) {
            triggered = true;

            AbstractDungeon.isScreenUp = true;
            GameCursor.hidden = true;
            AbstractDungeon.screen = AbstractDungeon.CurrentScreen.NO_INTERACT;

        }
    }

    public void applyEffect() {
        setDescriptionAfterLoading();

        AbstractDungeon.player.increaseMaxHp(200, true);
        AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
        AbstractDungeon.player.energy.energyMaster++;

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                ShionBossBackgroundEffect.instance.loadAnimation(
                        "VUPShionMod/img/monsters/PlagaAMundo/Background_idle.atlas",
                        "VUPShionMod/img/monsters/PlagaAMundo/Background_idle.json", 1.0f);
                ShionBossBackgroundEffect.instance.setAnimation(0, "Background_idle1", true);
                ShionBossBackgroundEffect.instance.setAnimation(0, "Background_idle2", true);
                ShionBossBackgroundEffect.instance.setAnimation(0, "Background_idle3", true);
                isDone = true;
            }
        });

        if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion) {
            for (AbstractFinFunnel f : AbstractPlayerPatches.AddFields.finFunnelList.get(AbstractDungeon.player)) {
                f.upgradeLevel(9);
            }
        }
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new AttackOrderSpecialPower(AbstractDungeon.player)));
        addToBot(new GainEnergyAction(1));
        this.img = UPGRADE_IMG;

        AbstractPlayerPatches.AddFields.chargeHelper.get(AbstractDungeon.player).active = true;

        addToBot(new VFXAction(new BorderLongFlashEffect(Color.LIGHT_GRAY)));
        addToBot(new VFXAction(new DieDieDieEffect(), 0.7F));
        addToBot(new ShakeScreenAction(0.0F, ScreenShake.ShakeDur.MED, ScreenShake.ShakeIntensity.HIGH));
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!monster.isDeadOrEscaped()) {
                addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, LifeLinkPower.POWER_ID));
                addToBot(new LoseHPAction(monster,AbstractDungeon.player,500));
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        monster.decreaseMaxHealth(500);
                        isDone = true;
                    }
                });

            }
        }

    }

    @Override
    public void atBattleStartPreDraw() {
        super.atBattleStartPreDraw();
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BarricadePower(AbstractDungeon.player)));

    }


    @Override
    public void atTurnStart() {
        super.atTurnStart();
        if (AbstractDungeon.player.halfDead) {
            triggerRelic();
        }

        if (this.triggered)
            addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 200));
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        if (triggered && m.hasPower(PursuitPower.POWER_ID)) {
            int amount = (m.getPower(PursuitPower.POWER_ID)).amount;
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                flash();
                addToBot(new RelicAboveCreatureAction(m, this));
                addToBot(new ApplyPowerToRandomEnemyAction(AbstractDungeon.player, new PursuitPower(null, amount), amount, false, AbstractGameAction.AttackEffect.NONE));
            }

            if (triggered && m.hasPower(BleedingPower.POWER_ID)) {
                int amount2 = (m.getPower(BleedingPower.POWER_ID)).amount;
                if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    flash();
                    addToBot(new RelicAboveCreatureAction(m, this));
                    addToBot(new ApplyPowerToRandomEnemyAction(AbstractDungeon.player, new BleedingPower(null, AbstractDungeon.player, amount2), amount2, false, AbstractGameAction.AttackEffect.NONE));
                }
            }
        }
    }

    @Override
    public boolean onPlayerDeath(AbstractPlayer abstractPlayer, DamageInfo damageInfo) {
        boolean canTrigger = false;
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (m.id.equals(PlagaAMundoMinion.ID)) {
                canTrigger = true;
                break;
            }
        }

        if (!triggered && canTrigger) {
            AbstractDungeon.player.halfDead = true;
            (AbstractDungeon.getCurrRoom()).cannotLose = true;
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        super.onUseCard(targetCard, useCardAction);
        if (AbstractPlayerPatches.AddFields.chargeHelper.get(AbstractDungeon.player).active)
            if (targetCard.hasTag(CardTagsEnum.TRIGGER_FIN_FUNNEL) || targetCard.hasTag(CardTagsEnum.FIN_FUNNEL)) {
                AbstractPlayerPatches.AddFields.chargeHelper.get(AbstractDungeon.player).addCount(1);
            }
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        if (this.triggered) {
            cg.render(sb);
        }
    }

    @Override
    public void update() {
        super.update();
        if (this.triggered) {
            cg.update();
        }

        if (cg.isDone && !effectApplied) {
            effectApplied = true;
            AbstractDungeon.isScreenUp = false;
            GameCursor.hidden = false;
            AbstractDungeon.screen = AbstractDungeon.CurrentScreen.NONE;
            AbstractDungeon.player.halfDead = false;
            applyEffect();
        }
    }

    public void renderAbove(SpriteBatch sb) {
        if (this.triggered)
            cg.renderAbove(sb);
    }


}
