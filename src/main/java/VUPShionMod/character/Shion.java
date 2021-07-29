package VUPShionMod.character;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.shion.Cannonry;
import VUPShionMod.cards.shion.Defend_Shion;
import VUPShionMod.cards.shion.FinFunnelUpgrade;
import VUPShionMod.effects.FinFunnelSelectedEffect;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.GravityFinFunnel;
import VUPShionMod.finfunnels.InvestigationFinFunnel;
import VUPShionMod.finfunnels.PursuitFinFunnel;
import VUPShionMod.modules.EnergyOrbShion;
import VUPShionMod.patches.*;
import VUPShionMod.powers.BadgeOfTimePower;
import VUPShionMod.powers.MarkOfThePaleBlueCrossPower;
import VUPShionMod.powers.SupportArmamentPower;
import VUPShionMod.relics.DimensionSplitterAria;
import basemod.abstracts.CustomPlayer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;
import java.util.List;

import static VUPShionMod.VUPShionMod.Shion_Color;

public class Shion extends CustomPlayer {
    public static final CharacterStrings charStrings = CardCrawlGame.languagePack.getCharacterString(VUPShionMod.makeID("Shion"));

    public static final int ENERGY_PER_TURN = 3;
    public static final int START_HP = 88;
    public static final int START_GOLD = 99;
    public static boolean firstAttackAnimation = true;

    public static final String[] orbTextures = {
            "VUPShionMod/img/ui/topPanel/Shion/1.png",//4
            "VUPShionMod/img/ui/topPanel/Shion/2.png",//2
            "VUPShionMod/img/ui/topPanel/Shion/3.png",//3
            "VUPShionMod/img/ui/topPanel/Shion/4.png",//5
            "VUPShionMod/img/ui/topPanel/Shion/5.png",//1
            "VUPShionMod/img/ui/topPanel/Shion/border.png",
            "VUPShionMod/img/ui/topPanel/Shion/1d.png",//4
            "VUPShionMod/img/ui/topPanel/Shion/2d.png",//2
            "VUPShionMod/img/ui/topPanel/Shion/3d.png",//3
            "VUPShionMod/img/ui/topPanel/Shion/4d.png",//5
            "VUPShionMod/img/ui/topPanel/Shion/5d.png",//1
    };

    public Shion(String name, PlayerClass setClass) {
        super(name, setClass, new EnergyOrbShion(orbTextures, "VUPShionMod/img/ui/topPanel/Shion/energyVFX.png"), (String) null, null);
        this.drawX += 5.0F * Settings.scale;
        this.drawY += 7.0F * Settings.scale;

        this.dialogX = this.drawX + 0.0F * Settings.scale;
        this.dialogY = this.drawY + 170.0F * Settings.scale;

        initializeClass(null,
                "VUPShionMod/characters/Shion/shoulder2.png",
                "VUPShionMod/characters/Shion/shoulder.png",
                "VUPShionMod/characters/Shion/corpse.png",
                getLoadout(), 0.0F, -5.0F, 240.0F, 320.0F, new EnergyManager(ENERGY_PER_TURN));

        loadAnimation(VUPShionMod.assetPath("characters/Shion/animation/ShionAnimation.atlas"), VUPShionMod.assetPath("characters/Shion/animation/ShionAnimation.json"), 3.2f);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
//        this.stateData.setMix("Hit", "Idle", 0.1F);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    @Override
    public void preBattlePrep() {
        super.preBattlePrep();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BadgeOfTimePower(this, 1)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SupportArmamentPower(this, 1)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MarkOfThePaleBlueCrossPower(this, 1)));
        if (AbstractPlayerPatches.AddFields.finFunnelList.get(this).isEmpty()) {
            List<AbstractFinFunnel> funnelList = AbstractPlayerPatches.AddFields.finFunnelList.get(this);
            funnelList.add(new InvestigationFinFunnel().setPosition(this.hb.cX - 288.0F * Settings.scale, this.hb.cY - 60.0F * Settings.scale, false));
            funnelList.add(new PursuitFinFunnel().setPosition(this.hb.cX + 128.0F * Settings.scale, this.hb.cY - 120.0F * Settings.scale, true));
            funnelList.add(new GravityFinFunnel().setPosition(this.hb.cX - 164.0F * Settings.scale, this.hb.cY - 120.0F * Settings.scale, false));
            AbstractPlayerPatches.AddFields.activatedFinFunnel.set(this, funnelList.get(1));
            AbstractDungeon.effectList.add(new FinFunnelSelectedEffect());
        }
    }

    public String getPortraitImageName() {
        return null;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(DimensionSplitterAria.ID);
        return retVal;
    }

    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Cannonry.ID);
        retVal.add(Cannonry.ID);
        retVal.add(Cannonry.ID);
        retVal.add(Cannonry.ID);
        retVal.add(Defend_Shion.ID);
        retVal.add(Defend_Shion.ID);
        retVal.add(Defend_Shion.ID);
        retVal.add(Defend_Shion.ID);
        retVal.add(FinFunnelUpgrade.ID);

        return retVal;
    }

    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(
                getLocalizedCharacterName(),
                charStrings.TEXT[0],
                START_HP,
                START_HP,
                0,
                START_GOLD,
                5,
                this,
                getStartingRelics(),
                getStartingDeck(),
                false);
    }


    @Override
    public String getTitle(PlayerClass playerClass) {
        return charStrings.NAMES[1];
    }

    @Override
    public ArrayList<AbstractCard> getCardPool(ArrayList<AbstractCard> tmpPool) {

        if (ModHelper.isModEnabled("Red Cards")) {
            CardLibrary.addRedCards(tmpPool);
        }
        if (ModHelper.isModEnabled("Green Cards")) {
            CardLibrary.addGreenCards(tmpPool);
        }

        if (ModHelper.isModEnabled("Blue Cards")) {
            CardLibrary.addBlueCards(tmpPool);
        }

        if (ModHelper.isModEnabled("Purple Cards")) {
            CardLibrary.addPurpleCards(tmpPool);
        }

        return super.getCardPool(tmpPool);
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return CardColorEnum.VUP_Shion_LIME;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Defend_Shion();
    }

    @Override
    public Color getCardTrailColor() {
        return Shion_Color.cpy();
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 8;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontPurple;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_DAGGER_1", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_DAGGER_1";
    }

    @Override
    public String getLocalizedCharacterName() {
        return charStrings.NAMES[0];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new Shion(this.name, AbstractPlayerEnum.VUP_Shion);
    }

    @Override
    public String getSpireHeartText() {
        return CardCrawlGame.languagePack.getEventString(VUPShionMod.makeID("SpireHeart_Shion")).DESCRIPTIONS[0];
    }


    @Override
    public Color getSlashAttackColor() {
        return Color.PURPLE;
    }


    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[1];
    }

    @Override
    public Color getCardRenderColor() {
        return Settings.PURPLE_COLOR;
    }


    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL
        };
    }

    public void damage(DamageInfo info) {
//        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output - this.currentBlock > 0) {
//            AnimationState.TrackEntry e = this.state.setAnimation(0, "Hit", false);
//            this.state.addAnimation(0, "Idle", true, 0.0F);
//            e.setTimeScale(1.0F);
//        }

        super.damage(info);
    }
}

