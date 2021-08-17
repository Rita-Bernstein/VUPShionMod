package VUPShionMod.character;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.MoveFinFunnelSelectedEffectAction;
import VUPShionMod.cards.anastasia.*;
import VUPShionMod.cards.minami.TacticalLayout;
import VUPShionMod.cards.shion.Strafe;
import VUPShionMod.cards.shion.Strike_Shion;
import VUPShionMod.cards.shion.Defend_Shion;
import VUPShionMod.effects.FinFunnelSelectedEffect;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.GravityFinFunnel;
import VUPShionMod.finfunnels.InvestigationFinFunnel;
import VUPShionMod.finfunnels.PursuitFinFunnel;
import VUPShionMod.modules.EnergyOrbShion;
import VUPShionMod.patches.AbstractPlayerEnum;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.patches.CardColorEnum;
import VUPShionMod.powers.BadgeOfThePaleBlueCrossPower;
import VUPShionMod.powers.BadgeOfTimePower;
import VUPShionMod.powers.DelayAvatarPower;
import VUPShionMod.powers.HyperdimensionalLinksPower;
import VUPShionMod.relics.DimensionSplitterAria;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
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
    private Texture avatar = ImageMaster.loadImage("VUPShionMod/characters/Shion/Avatar.png");

    public static final String[] orbTextures = {
            "VUPShionMod/img/ui/topPanel/Shion/layer1.png",//4
            "VUPShionMod/img/ui/topPanel/Shion/layer2.png",//2
            "VUPShionMod/img/ui/topPanel/Shion/layer3.png",//3
            "VUPShionMod/img/ui/topPanel/Shion/layer4.png",//5
            "VUPShionMod/img/ui/topPanel/Shion/layer5.png",//1
            "VUPShionMod/img/ui/topPanel/Shion/layer6.png",
            "VUPShionMod/img/ui/topPanel/Shion/layer1d.png",//4
            "VUPShionMod/img/ui/topPanel/Shion/layer2d.png",//2
            "VUPShionMod/img/ui/topPanel/Shion/layer3d.png",//3
            "VUPShionMod/img/ui/topPanel/Shion/layer4d.png",//5
            "VUPShionMod/img/ui/topPanel/Shion/layer5d.png",//1
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
                getLoadout(), 0.0F, -5.0F, 240.0F, 480.0F, new EnergyManager(ENERGY_PER_TURN));

        loadAnimation(VUPShionMod.assetPath("characters/Shion/animation/ShionAnimation.atlas"), VUPShionMod.assetPath("characters/Shion/animation/ShionAnimation.json"), 1.0f);

        this.state.setAnimation(0, "Idle_body", true).setTimeScale(2.0f);
        this.state.setAnimation(1, "Idle_Weapon1", true).setTimeScale(2.0f);
        this.state.setAnimation(2, "Idle_Weapon2", true).setTimeScale(2.0f);
        this.state.setAnimation(3, "Idle_Weapon3", true).setTimeScale(2.0f);
    }

    @Override
    public void preBattlePrep() {
        super.preBattlePrep();
        if (AbstractPlayerPatches.AddFields.finFunnelList.get(this).isEmpty()) {
            List<AbstractFinFunnel> funnelList = AbstractPlayerPatches.AddFields.finFunnelList.get(this);
            funnelList.add(new InvestigationFinFunnel(1));
            funnelList.add(new PursuitFinFunnel(1));
            funnelList.add(new GravityFinFunnel(1));
            AbstractPlayerPatches.AddFields.activatedFinFunnel.set(this, funnelList.get(1));
            if (VUPShionMod.finFunnelSaver.data != null) {
                int index = 0;
                for (Integer i : VUPShionMod.finFunnelSaver.data) {
                    if (funnelList.size() > index) {
                        funnelList.get(index).setLevel(i);
                    } else {
                        break;
                    }
                    index++;
                }
            }
        }
        AbstractDungeon.effectList.add(new FinFunnelSelectedEffect());
        AbstractDungeon.actionManager.addToBottom(new MoveFinFunnelSelectedEffectAction(FinFunnelSelectedEffect.instance, AbstractPlayerPatches.AddFields.activatedFinFunnel.get(this)));
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
        retVal.add(Strike_Shion.ID);
        retVal.add(Strike_Shion.ID);
        retVal.add(Strike_Shion.ID);
        retVal.add(Strike_Shion.ID);
        retVal.add(Defend_Shion.ID);
        retVal.add(Defend_Shion.ID);
        retVal.add(Defend_Shion.ID);
        retVal.add(Defend_Shion.ID);
        retVal.add(TacticalLayout.ID);
        retVal.add(FinFunnelUpgrade.ID);
        retVal.add(Strafe.ID);


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
        CardCrawlGame.sound.play("SHION_" + (3 + MathUtils.random(2)));
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
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output - this.currentBlock > 0) {
            this.state.setAnimation(0, "Hit_Body", false).setTimeScale(4.0f);
            this.state.setAnimation(1, "Hit_Weapon1", false).setTimeScale(4.0f);
            this.state.setAnimation(2, "Hit_Weapon2", false).setTimeScale(4.0f);
            this.state.setAnimation(3, "Hit_Weapon3", false).setTimeScale(4.0f);
            this.state.addAnimation(0, "Idle_body", true, 0.0F).setTimeScale(2.0f);
            this.state.addAnimation(1, "Idle_Weapon1", true, 0.0F).setTimeScale(2.0f);
            this.state.addAnimation(2, "Idle_Weapon2", true, 0.0F).setTimeScale(2.0f);
            this.state.addAnimation(3, "Idle_Weapon3", true, 0.0F).setTimeScale(2.0f);
        }

        super.damage(info);
    }


    @Override
    public void renderPlayerImage(SpriteBatch sb) {
        super.renderPlayerImage(sb);
        boolean hasPower = false;
        for (AbstractPower power : this.powers) {
            if (power instanceof DelayAvatarPower)
                hasPower = true;
        }

        if (hasPower) {
            sb.setColor(Color.WHITE);
            sb.setBlendFunction(770, 771);
            sb.draw(this.avatar, this.hb.x - this.avatar.getWidth() * 0.5f + 300.0F * Settings.scale,
                    this.hb.y - this.avatar.getHeight() * 0.5f + 120.0F * Settings.scale,
                    this.avatar.getWidth() * 0.5f, this.avatar.getHeight() * 0.5f, this.avatar.getWidth(), this.avatar.getHeight(),
                    0.6f * Settings.scale, 0.6f * Settings.scale, 0.0F, 0, 0, this.avatar.getWidth(), this.avatar.getHeight(), false, false);

        }
    }

    public void playFinFunnelAnimation(String id) {
        switch (id) {
            case "VUPShionMod:GravityFinFunnel":
                this.state.setAnimation(1, "Attack_Weapon1", false).setTimeScale(3.0f);
                this.state.addAnimation(1, "Idle_Weapon1", true, 0.0F).setTimeScale(2.0f);
                break;
            case "VUPShionMod:InvestigationFinFunnel":
                this.state.setAnimation(2, "Attack_Weapon2", false).setTimeScale(3.0f);
                this.state.addAnimation(2, "Idle_Weapon2", true, 0.0F).setTimeScale(2.0f);
                break;
            case "VUPShionMod:PursuitFinFunnel":
                this.state.setAnimation(3, "Attack_Weapon3", false).setTimeScale(3.0f);
                this.state.addAnimation(3, "Idle_Weapon3", true, 0.0F).setTimeScale(2.0f);
                break;
        }

        this.state.setAnimation(0, "Attack_Body", false).setTimeScale(3.0f);
        this.state.addAnimation(0, "Idle_body", true, 0.0f).setTimeScale(2.0f);
    }

    @Override
    public void playDeathAnimation() {
        int count = MathUtils.random(3);
        switch (count) {
            case 0:
                CardCrawlGame.sound.play("SHION_14");
                break;
            case 1:
                CardCrawlGame.sound.play("SHION_15");
                break;
            case 2:
                CardCrawlGame.sound.play("SHION_17");
                break;
            case 3:
                CardCrawlGame.sound.play("SHION_18");
                break;
        }

        super.playDeathAnimation();
    }

    @Override
    public void addPower(AbstractPower powerToApply) {
        super.addPower(powerToApply);
        if (powerToApply instanceof StrengthPower && powerToApply.amount > 0) {
            int count = MathUtils.random(1);
            switch (count) {
                case 0:
                    CardCrawlGame.sound.play("SHION_7");
                    break;
                case 1:
                    CardCrawlGame.sound.play("SHION_12");
                    break;
            }
        }
    }

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList();
        panels.add(new CutscenePanel("VUPShionMod/img/scenes/ShionCutScene1.png"));
        panels.add(new CutscenePanel("VUPShionMod/img/scenes/ShionCutScene2.png"));
        panels.add(new CutscenePanel("VUPShionMod/img/scenes/ShionCutScene3.png"));
        return panels;
    }
}

