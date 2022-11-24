package VUPShionMod.character;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.Codex.*;
import VUPShionMod.cards.EisluRen.*;
import VUPShionMod.cards.ShionCard.kuroisu.ReleaseFormKuroisu;
import VUPShionMod.cards.ShionCard.liyezhu.*;
import VUPShionMod.cards.ShionCard.minami.*;
import VUPShionMod.cards.ShionCard.shion.*;
import VUPShionMod.cards.WangChuan.*;
import VUPShionMod.modules.EnergyOrbShion;
import VUPShionMod.patches.*;
import VUPShionMod.powers.Shion.DelayAvatarPower;
import VUPShionMod.powers.Shion.StrikeIntegratedPower;
import VUPShionMod.skins.SkinManager;
import VUPShionMod.vfx.victory.ShionGoldVictoryEffect;
import VUPShionMod.vfx.victory.ShionVictoryEffect;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
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
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static VUPShionMod.VUPShionMod.Shion_Color;

public class Shion extends CustomPlayer {
    public static final CharacterStrings charStrings = CardCrawlGame.languagePack.getCharacterString(VUPShionMod.makeID(Shion.class.getSimpleName()));

    public static final int ENERGY_PER_TURN = 3;
    public static final int START_HP = 70;
    public static final int START_GOLD = 99;
    public static boolean firstAttackAnimation = true;
    private Texture avatar = ImageMaster.loadImage("VUPShionMod/characters/Shion/Avatar.png");

    public static final String[] orbTextures = {
            "VUPShionMod/img/ui/topPanel/Shion/layer1.png",
            "VUPShionMod/img/ui/topPanel/Shion/layer2.png",
            "VUPShionMod/img/ui/topPanel/Shion/layer3.png",
            "VUPShionMod/img/ui/topPanel/Shion/layer4.png",
            "VUPShionMod/img/ui/topPanel/Shion/layer5.png",
            "VUPShionMod/img/ui/topPanel/Shion/layer6.png",
            "VUPShionMod/img/ui/topPanel/Shion/layer7.png",
            "VUPShionMod/img/ui/topPanel/Shion/levelBack.png",//中间
            "VUPShionMod/img/ui/topPanel/Shion/levelBack.png",
            "VUPShionMod/img/ui/topPanel/Shion/level1.png",
            "VUPShionMod/img/ui/topPanel/Shion/level2.png",
            "VUPShionMod/img/ui/topPanel/Shion/level3.png",
            "VUPShionMod/img/ui/topPanel/Shion/level4.png",
            "VUPShionMod/img/ui/topPanel/Shion/level5.png",
            "VUPShionMod/img/ui/topPanel/Shion/levelMax.png"
    };

    public Shion(String name, PlayerClass setClass) {
        super(name, setClass, new EnergyOrbShion(orbTextures, "VUPShionMod/img/ui/topPanel/Shion/energyVFX.png"), (String) null, null);
        this.drawX += 5.0F * Settings.scale;
        this.drawY += 7.0F * Settings.scale;

        this.dialogX = this.drawX + 20.0F * Settings.scale;
        this.dialogY = this.drawY + 270.0F * Settings.scale;

        CharacterSelectScreenPatches.AddFields.characterPriority.get(this).setCharacterPriority(1);

        initializeClass(null,
                SkinManager.getSkin(0).SHOULDER1,
                SkinManager.getSkin(0).SHOULDER2,
                SkinManager.getSkin(0).CORPSE,
                getLoadout(), 0.0F, -5.0F, 260.0F, 460.0F, new EnergyManager(ENERGY_PER_TURN));

        reloadAnimation();
    }

    public void reloadAnimation() {
        this.loadAnimation(
                SkinManager.getSkin(0).atlasURL,
                SkinManager.getSkin(0).jsonURL,
                SkinManager.getSkin(0).renderScale);


        this.state.setAnimation(0, "idle", true);
        if (SkinManager.getSkinCharacter(0).reskinCount != 3)
            this.state.setAnimation(4, "wing_idle", true);

    }


    public String getPortraitImageName() {
        return null;
    }

    public ArrayList<String> getStartingRelics() {
        SkinManager.getSkinCharacter(0).InitializeReskinCount();
        return SkinManager.getSkin(0).getStartingRelic();
    }

    public ArrayList<String> getStartingDeck() {
        SkinManager.getSkinCharacter(0).InitializeReskinCount();
        return SkinManager.getSkin(0).getStartingDeck();
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
        return SkinManager.getSkin(0).getCharacterTiTleName();
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

        tmpPool.add(new BlockThis());
        tmpPool.add(new SupportShion());
        tmpPool.add(new Replenish());
        tmpPool.add(new OffensiveAdvance());
        tmpPool.add(new PhysicalMagic());
        tmpPool.add(new SupportTargetedSniping());
        tmpPool.add(new Anticoagulation());
        tmpPool.add(new StrikeIntegrated());
        tmpPool.add(new CollaborativeInvestigation());

        tmpPool.add(new AquaConstans());
        tmpPool.add(new AquaRapida());
        tmpPool.add(new VentusRapidus());
        tmpPool.add(new VentusNimius());
        tmpPool.add(new TonitrusConstans());
        tmpPool.add(new TonitrusRapidus());


        if(!tmpPool.isEmpty()) {
            removeCardPool(tmpPool,

                    pr -> SkinManager.getSkinCharacter(0).reskinCount == 3 &&
                            pr instanceof HyperDimensionalMatrix
                            || pr instanceof ArmedToTheTeeth
                            || pr instanceof SantaCroce
                            || pr instanceof SacredAdvice
                            || pr instanceof ReleaseFormLiyezhu
                            || pr instanceof HolyCoffinSinkingSpirit
                            || pr instanceof HolyCoffinRelease
                            || pr instanceof HolyCharge
                            || pr instanceof BlueBlade
                            || pr instanceof ReleaseFormKuroisu
                            || pr instanceof SuperCharge
                            || pr instanceof StrengthPray,

                    pr -> SkinManager.getSkinCharacter(0).reskinCount == 1 &&
                            pr instanceof LockIndication,

                    pr -> pr instanceof GravityRepression
                    || pr instanceof TrackingAnalysis
                    || pr instanceof CollaborativeInvestigation
                    );
        }

        return super.getCardPool(tmpPool);
    }


    public static void removeCardPool(ArrayList<AbstractCard> oriCards, Predicate<AbstractCard>... filters) {
        ArrayList<AbstractCard> cards = new ArrayList<>();

        for (AbstractCard c : oriCards) {
            for (Predicate<AbstractCard> filter : filters)
                if (filter.test(c)) {
                    cards.add(c);
                }
        }

        if (!cards.isEmpty()) {
            oriCards.removeAll(cards);
        }
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
        return START_HP / 10;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelperPatches.energyNumFontShion;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        SkinManager.getSkin(0).doCharSelectScreenSelectEffect();
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
            if (SkinManager.getSkinCharacter(0).reskinCount != 3) {
                this.state.setAnimation(0, "hurt", false);
                this.state.addAnimation(0, "idle", true, 0.0F);
            } else {
                this.state.setAnimation(0, "hurt", false).setTimeScale(3.0f);
                this.state.addAnimation(0, "idle", true, 0.0F);
            }
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
        panels.add(new CutscenePanel("VUPShionMod/img/scenes/ShionCutScene1.sff"));
        panels.add(new CutscenePanel("VUPShionMod/img/scenes/ShionCutScene2.sff"));
        panels.add(new CutscenePanel("VUPShionMod/img/scenes/ShionCutScene3.sff"));
        return panels;
    }

    @Override
    public void useCard(AbstractCard c, AbstractMonster monster, int energyOnUse) {
        super.useCard(c, monster, energyOnUse);

        if (c.hasTag(CardTagsEnum.LOADED)) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
        }
    }


    @Override
    public void updateVictoryVfx(ArrayList<AbstractGameEffect> effects) {
        boolean foundEyeVfx = false;

        if (SkinManager.getSkinCharacter(0).reskinCount == 2) {
            for (AbstractGameEffect e : effects) {
                if (e instanceof ShionVictoryEffect) {
                    foundEyeVfx = true;
                    break;
                }
            }

            if (!foundEyeVfx)
                effects.add(new ShionVictoryEffect());
        } else {
            for (AbstractGameEffect e : effects) {
                if (e instanceof ShionGoldVictoryEffect) {
                    foundEyeVfx = true;
                    break;
                }
            }

            if (!foundEyeVfx)
                effects.add(new ShionGoldVictoryEffect());
        }


    }
}

