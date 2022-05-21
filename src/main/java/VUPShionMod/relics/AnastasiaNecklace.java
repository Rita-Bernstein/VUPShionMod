package VUPShionMod.relics;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cutscenes.CGlayout;
import VUPShionMod.effects.ShionBossBackgroundEffect;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.monsters.PlagaAMundoMinion;
import VUPShionMod.patches.AbstractPlayerEnum;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.patches.CharacterSelectScreenPatches;
import VUPShionMod.powers.Shion.AttackOrderSpecialPower;
import VUPShionMod.powers.Shion.BleedingPower;
import VUPShionMod.powers.Unique.LifeLinkPower;
import VUPShionMod.powers.Shion.PursuitPower;
import VUPShionMod.skins.sk.Shion.BlueGiantShion;
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
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.FairyPotion;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.LizardTail;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.DieDieDieEffect;

public class AnastasiaNecklace extends AbstractShionRelic implements OnPlayerDeathRelic {
    public static final String ID = VUPShionMod.makeID("AnastasiaNecklace");
    public static final String IMG_PATH = "img/relics/AnastasiaNecklace.png";
    private static final String OUTLINE_PATH = "img/relics/outline/AnastasiaNecklace.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));
    private static final Texture UPGRADE_IMG = new Texture(VUPShionMod.assetPath("img/relics/AnastasiaNecklaceUpgrade.png"));
    private static final RelicStrings relicString = CardCrawlGame.languagePack.getRelicStrings(ID);

    public boolean triggered = false;
    public boolean effectApplied = false;
    private boolean lockHealth = false;

    private CGlayout cg = new CGlayout("Shion");

    public AnastasiaNecklace() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.SPECIAL, LandingSound.CLINK);
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
            this.tips.add(new PowerTip(relicString.DESCRIPTIONS[3], this.description));
        else
            this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    public void triggerRelic() {
        if (!triggered) {
            triggered = true;

            AbstractDungeon.isScreenUp = true;
            GameCursor.hidden = true;
            AbstractDungeon.screen = AbstractDungeon.CurrentScreen.NO_INTERACT;

            ShionBossBackgroundEffect.instance.loadAnimation(
                    "VUPShionMod/img/monsters/PlagaAMundo/Background_idle.atlas",
                    "VUPShionMod/img/monsters/PlagaAMundo/Background_idle.json", 1.0f);
            ShionBossBackgroundEffect.instance.setAnimation(0, "Background_idle1", true);
            ShionBossBackgroundEffect.instance.setAnimation(0, "Background_idle2", true);
            ShionBossBackgroundEffect.instance.setAnimation(0, "Background_idle3", true);


        }
    }

    public void applyEffect() {
        setDescriptionAfterLoading();

        CardCrawlGame.music.playTempBGM("VUPShionMod:Boss_Phase2");

        AbstractDungeon.player.increaseMaxHp(200, true);
        AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
        AbstractDungeon.player.energy.energyMaster++;
        AbstractDungeon.player.energy.energy++;

        if(!AbstractPlayerPatches.AddFields.finFunnelManager.get(AbstractDungeon.player).finFunnelList.isEmpty())
            for (AbstractFinFunnel f : AbstractPlayerPatches.AddFields.finFunnelManager.get(AbstractDungeon.player).finFunnelList) {
                f.upgradeLevel(9);
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
                addToBot(new RemoveSpecificPowerAction(monster, AbstractDungeon.player, LifeLinkPower.POWER_ID));
                addToBot(new RemoveSpecificPowerAction(monster, AbstractDungeon.player, StrengthPower.POWER_ID));
                addToBot(new LoseHPAction(monster, AbstractDungeon.player, 500));
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
        if (this.lockHealth) {
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

        for (AbstractRelic relic : AbstractDungeon.player.relics) {
            if (relic.relicId.equals(LizardTail.ID) && relic.counter != -2) {
                canTrigger = false;
                break;
            }
        }

        if (AbstractDungeon.player.hasPotion(FairyPotion.POTION_ID))
            canTrigger = false;


        if (!triggered && canTrigger) {
            CardCrawlGame.music.justFadeOutTempBGM();
            this.lockHealth = true;
            AbstractDungeon.player.hideHealthBar();
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
            AbstractDungeon.player.showHealthBar();
            this.lockHealth = false;
            (AbstractDungeon.getCurrRoom()).cannotLose = false;
            applyEffect();
        }
    }


    public void renderAbove(SpriteBatch sb) {
        if (this.triggered)
            cg.renderAbove(sb);
    }


}
