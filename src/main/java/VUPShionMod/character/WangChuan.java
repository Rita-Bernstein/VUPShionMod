package VUPShionMod.character;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.WangChuan.*;
import VUPShionMod.modules.EnergyOrbWangChuan;
import VUPShionMod.patches.*;
import VUPShionMod.skins.SkinManager;
import VUPShionMod.vfx.victory.WangchuanVictoryEffect;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;
import java.util.List;

import static VUPShionMod.VUPShionMod.WangChuan_Color;

public class WangChuan extends CustomPlayer {
    public static final CharacterStrings charStrings = CardCrawlGame.languagePack.getCharacterString(VUPShionMod.makeID(WangChuan.class.getSimpleName()));
    public static final int ENERGY_PER_TURN = 3;
    public static final int START_HP = 50;
    public static final int START_GOLD = 1;


    public AbstractPlayer shionHelper;

    public static final String[] orbTextures = {
            "VUPShionMod/img/ui/topPanel/Shion/layer1.png",
            "VUPShionMod/img/ui/topPanel/Shion/layer2.png",
            "VUPShionMod/img/ui/topPanel/Shion/layer3.png",
            "VUPShionMod/img/ui/topPanel/Shion/layer4.png",
            "VUPShionMod/img/ui/topPanel/Shion/layer5.png",
            "VUPShionMod/img/ui/topPanel/Shion/layer6.png",
            "VUPShionMod/img/ui/topPanel/Shion/layer7.png",
            "VUPShionMod/img/ui/topPanel/Shion/levelBack.png",
            "VUPShionMod/img/ui/topPanel/Shion/levelBack.png",
            "VUPShionMod/img/ui/topPanel/Shion/level1.png",
            "VUPShionMod/img/ui/topPanel/Shion/level2.png",
            "VUPShionMod/img/ui/topPanel/Shion/level3.png",
            "VUPShionMod/img/ui/topPanel/Shion/level4.png",
            "VUPShionMod/img/ui/topPanel/Shion/level5.png",
            "VUPShionMod/img/ui/topPanel/Shion/levelMax.png"
    };


    public WangChuan(String name, PlayerClass setClass) {
        super(name, setClass, new EnergyOrbWangChuan(orbTextures, "VUPShionMod/img/ui/topPanel/Shion/energyVFX.png"), null, null);
        this.drawX += 5.0F * Settings.scale;
        this.drawY += 7.0F * Settings.scale;

        this.dialogX = this.drawX + 20.0F * Settings.scale;
        this.dialogY = this.drawY + 270.0F * Settings.scale;

        CharacterSelectScreenPatches.AddFields.characterPriority.get(this).setCharacterPriority(2);
        initializeClass(null,
                SkinManager.getSkin(1).SHOULDER1,
                SkinManager.getSkin(1).SHOULDER2,
                SkinManager.getSkin(1).CORPSE,
                getLoadout(), 0.0F, -5.0F, 260.0F, 380.0F, new EnergyManager(ENERGY_PER_TURN));

        loadAnimation(VUPShionMod.assetPath("characters/WangChuan/animation/STANCE_WANGCHUAN_BREAK.atlas"),
                VUPShionMod.assetPath("characters/WangChuan/animation/STANCE_WANGCHUAN_BREAK.json"), 3.0f);


        reloadAnimation();
    }

    public void reloadAnimation() {
        this.loadAnimation(
                SkinManager.getSkin(1).atlasURL,
                SkinManager.getSkin(1).jsonURL,
                SkinManager.getSkin(1).renderScale);


        if (SkinManager.getSkinCharacter(1).reskinCount == 0) {
            this.state.setAnimation(0, "idle", true);
            this.state.setAnimation(1, "idle_YOFU", true);
        }

        if (SkinManager.getSkinCharacter(1).reskinCount == 1) {
            this.state.setAnimation(0, "idle", true);
        }

        if (SkinManager.getSkinCharacter(1).reskinCount == 2) {
            this.state.setAnimation(0, "idle", true);
            this.state.setAnimation(1, "idle_wing", true);
        }

        if (SkinManager.getSkinCharacter(1).reskinCount == 3) {
            this.state.setAnimation(0, "idle", true);
            this.state.setAnimation(1, "weapon_idle", true);
        }
    }


    public String getPortraitImageName() {
        return null;
    }

    public ArrayList<String> getStartingRelics() {
        SkinManager.getSkinCharacter(1).InitializeReskinCount();
        return SkinManager.getSkin(1).getStartingRelic();
    }

    public ArrayList<String> getStartingDeck() {
        SkinManager.getSkinCharacter(1).InitializeReskinCount();
        return SkinManager.getSkin(1).getStartingDeck();
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
        return SkinManager.getSkin(1).getCharacterTiTleName();
    }

    @Override
    public ArrayList<AbstractCard> getCardPool(ArrayList<AbstractCard> tmpPool) {
        ArrayList<AbstractCard> pool = super.getCardPool(tmpPool);

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

        for (AbstractCard c : VUPShionMod.codex_Cards) {
            tmpPool.add(c.makeCopy());
        }

        return pool;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return CardColorEnum.WangChuan_LIME;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Sheathe();
    }

    @Override
    public Color getCardTrailColor() {
        return WangChuan_Color.cpy();
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return START_HP / 10;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelperPatches.energyNumFontShion;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {

//        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_MAGIC_BEAM_SHORT";
    }

    @Override
    public String getLocalizedCharacterName() {
        return charStrings.NAMES[0];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new WangChuan(this.name, AbstractPlayerEnum.WangChuan);
    }

    @Override
    public String getSpireHeartText() {
        return CardCrawlGame.languagePack.getEventString(VUPShionMod.makeID("SpireHeart_WangChuan")).DESCRIPTIONS[0];
    }


    @Override
    public Color getSlashAttackColor() {
        return Color.GOLD;
    }


    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[1];
    }

    @Override
    public Color getCardRenderColor() {
        return Settings.GOLD_COLOR;
    }


    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL
        };
    }


    public void damage(DamageInfo info) {
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output - this.currentBlock > 0) {
            this.state.setAnimation(0, "hurt", false).setTimeScale(1.5f);
            this.state.addAnimation(0, "idle", true, 0.0f);
        }

        super.damage(info);
    }


    @Override
    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList();
        panels.add(new CutscenePanel("VUPShionMod/img/scenes/WangChuanCutScene.sff"));
        return panels;
    }


    @Override
    public void update() {
        super.update();

        if (this.shionHelper != null)
            shionHelper.update();
    }


    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        if (this.shionHelper != null) {
            shionHelper.renderPlayerBattleUi(sb);
            shionHelper.render(sb);
        }

    }


    @Override
    public void updateVictoryVfx(ArrayList<AbstractGameEffect> effects) {
        boolean foundEyeVfx = false;
        for (AbstractGameEffect e : effects) {
            if (e instanceof WangchuanVictoryEffect) {
                foundEyeVfx = true;
                break;
            }
        }

        if (!foundEyeVfx)
            effects.add(new WangchuanVictoryEffect());
    }

}

