package VUPShionMod.character;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.MoveFinFunnelSelectedEffectAction;
import VUPShionMod.cards.ShionCard.anastasia.FinFunnelUpgrade;
import VUPShionMod.cards.ShionCard.minami.TacticalLayout;
import VUPShionMod.cards.ShionCard.shion.Defend_Shion;
import VUPShionMod.cards.ShionCard.shion.Strafe;
import VUPShionMod.cards.ShionCard.shion.Strike_Shion;
import VUPShionMod.cards.WangChuan.*;
import VUPShionMod.effects.FinFunnelSelectedEffect;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.GravityFinFunnel;
import VUPShionMod.finfunnels.InvestigationFinFunnel;
import VUPShionMod.finfunnels.PursuitFinFunnel;
import VUPShionMod.modules.EnergyOrbShion;
import VUPShionMod.modules.EnergyOrbWangChuan;
import VUPShionMod.patches.*;
import VUPShionMod.relics.DimensionSplitterAria;
import VUPShionMod.relics.Nebula;
import VUPShionMod.relics.StarQuakes;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbBlue;

import java.util.ArrayList;
import java.util.List;

import static VUPShionMod.VUPShionMod.Shion_Color;
import static VUPShionMod.VUPShionMod.WangChuan_Color;

public class WangChuan extends CustomPlayer {
    public static final CharacterStrings charStrings = CardCrawlGame.languagePack.getCharacterString(VUPShionMod.makeID("WangChuan"));
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
        super(name, setClass, new EnergyOrbWangChuan(orbTextures, "VUPShionMod/img/ui/topPanel/Shion/energyVFX.png"), (String) null, null);
        this.drawX += 5.0F * Settings.scale;
        this.drawY += 7.0F * Settings.scale;

        this.dialogX = this.drawX + 0.0F * Settings.scale;
        this.dialogY = this.drawY + 170.0F * Settings.scale;

        initializeClass(null,
                CharacterSelectScreenPatches.characters[1].skins[CharacterSelectScreenPatches.characters[1].reskinCount].SHOULDER1,
                CharacterSelectScreenPatches.characters[1].skins[CharacterSelectScreenPatches.characters[1].reskinCount].SHOULDER2,
                CharacterSelectScreenPatches.characters[1].skins[CharacterSelectScreenPatches.characters[1].reskinCount].CORPSE,
                getLoadout(), 0.0F, -5.0F, 260.0F, 380.0F, new EnergyManager(ENERGY_PER_TURN));

        loadAnimation(VUPShionMod.assetPath("characters/WangChuan/animation/STANCE_WANGCHUAN_BREAK.atlas"),
                VUPShionMod.assetPath("characters/WangChuan/animation/STANCE_WANGCHUAN_BREAK.json"), 3.0f);


        reloadAnimation();
    }

    public void reloadAnimation() {
        this.loadAnimation(
                CharacterSelectScreenPatches.characters[1].skins[CharacterSelectScreenPatches.characters[1].reskinCount].atlasURL,
                CharacterSelectScreenPatches.characters[1].skins[CharacterSelectScreenPatches.characters[1].reskinCount].jsonURL,
                CharacterSelectScreenPatches.characters[1].skins[CharacterSelectScreenPatches.characters[1].reskinCount].renderscale);


        if (CharacterSelectScreenPatches.characters[1].reskinCount == 0) {
            this.state.setAnimation(0, "idle", true);
            this.state.setAnimation(1, "idle_YOFU", true);
        }

        if (CharacterSelectScreenPatches.characters[1].reskinCount == 1) {
            this.state.setAnimation(0, "idle", true);
        }
    }


    public String getPortraitImageName() {
        return null;
    }

    public ArrayList<String> getStartingRelics() {
        CharacterSelectScreenPatches.characters[1].InitializeReskinCount();
        return CharacterSelectScreenPatches.characters[1].skins[CharacterSelectScreenPatches.characters[1].reskinCount].getStartingRelic();
    }

    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(HiltBash.ID);
        retVal.add(HiltBash.ID);
        retVal.add(HiltBash.ID);
        retVal.add(HiltBash.ID);
        retVal.add(Slide.ID);
        retVal.add(Slide.ID);
        retVal.add(Slide.ID);
        retVal.add(Slide.ID);
//        retVal.add(PreExecution.ID);
        retVal.add(Sheathe.ID);
//        retVal.add(InTheBlink.ID);
        if (CharacterSelectScreenPatches.characters[1].reskinCount == 0)
            retVal.add(GlandesMagicae.ID);

        if (CharacterSelectScreenPatches.characters[1].reskinCount == 1)
            retVal.add(InTheBlink.ID);

        retVal.add(MorsLibraque.ID);


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

        for (AbstractCard c : VUPShionMod.codex_Cards) {
            tmpPool.add(c.makeCopy());
        }

        return super.getCardPool(tmpPool);
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
        return 8;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelperPatches.energyNumFontShion;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {

        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
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
            this.state.addAnimation(0, "idle", true,0.0f);
        }

        super.damage(info);
    }


    @Override
    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList();
        panels.add(new CutscenePanel("VUPShionMod/img/scenes/WangChuanCutScene.png"));
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
}

