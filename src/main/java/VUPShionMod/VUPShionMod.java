package VUPShionMod;

import VUPShionMod.cards.Liyezhu.*;
import VUPShionMod.cards.ShionCard.*;
import VUPShionMod.cards.ShionCard.anastasia.*;
import VUPShionMod.cards.ShionCard.kuroisu.*;
import VUPShionMod.cards.ShionCard.liyezhu.*;
import VUPShionMod.cards.ShionCard.minami.*;
import VUPShionMod.cards.ShionCard.optionCards.*;
import VUPShionMod.cards.ShionCard.shion.*;
import VUPShionMod.cards.ShionCard.tempCards.*;
import VUPShionMod.cards.WangChuan.*;
import VUPShionMod.cards.Codex.*;
import VUPShionMod.character.EisluRen;
import VUPShionMod.character.Liyezhu;
import VUPShionMod.character.Shion;
import VUPShionMod.character.WangChuan;
import VUPShionMod.events.*;
import VUPShionMod.helpers.SecondaryMagicVariable;
import VUPShionMod.monsters.Story.PlagaAMundo;
import VUPShionMod.monsters.RitaShop;
import VUPShionMod.patches.*;
import VUPShionMod.potions.*;
import VUPShionMod.relics.EisluRen.*;
import VUPShionMod.relics.Event.*;
import VUPShionMod.relics.Liyezhu.*;
import VUPShionMod.relics.Share.*;
import VUPShionMod.relics.Shion.*;
import VUPShionMod.relics.Wangchuan.*;
import VUPShionMod.skins.AbstractSkin;
import VUPShionMod.skins.AbstractSkinCharacter;
import VUPShionMod.ui.SansMeterSave;
import VUPShionMod.util.SaveHelper;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.abstracts.CustomCard;
import basemod.eventUtil.AddEventParams;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@SpireInitializer
public class VUPShionMod implements
        PostInitializeSubscriber,
        EditCharactersSubscriber,
        EditCardsSubscriber,
        EditRelicsSubscriber,
        AddAudioSubscriber,
        EditKeywordsSubscriber,
        EditStringsSubscriber,
        PostDungeonInitializeSubscriber,
        StartActSubscriber,
        PostCreateStartingRelicsSubscriber {


    public static String MOD_ID = "VUPShionMod";
    public static Properties VUPShionDefaults = new Properties();
    public static final String MODNAME = "VUPShionMod";
    public static final String AUTHOR = "Rita";
    public static final String DESCRIPTION = "";


    public static final Color Shion_Color = new Color(0.418F, 0.230F, 0.566F, 1.0F);
    public static final Color WangChuan_Color = new Color(0.203F, 0.176F, 0.168F, 1.0F);
    public static final Color Liyezhu_Color = new Color(0.250F, 0.286F, 0.541F, 1.0F);
    public static final Color EisluRen_Color = new Color(0.043F, 0.875F, 0.195F, 1.0F);


    public static final Color PotionPlaceHolderColor = new Color(0.250F, 0.286F, 0.541F, 1.0F);
    public static final Color ShionPotion_Color = new Color(0.360F, 0.780F, 0.760F, 1.0F);
    public static final Color WangChuanPotion_Color = new Color(1.0F, 1.0F, 0.210F, 1.0F);
    public static final Color LiyezhuPotion_Color = new Color(0.5F, 0.5F, 1.0F, 1.0F);


    public static ArrayList<AbstractGameEffect> effectsQueue = new ArrayList<>();

    public static List<CustomCard> an_Cards = new ArrayList<>();
    public static List<CustomCard> ku_Cards = new ArrayList<>();
    public static List<CustomCard> li_Cards = new ArrayList<>();
    public static List<CustomCard> mi_Cards = new ArrayList<>();
    public static List<CustomCard> shi_Cards = new ArrayList<>();
    public static List<CustomCard> chuan_Cards = new ArrayList<>();
    public static List<CustomCard> codex_Cards = new ArrayList<>();

    public static ModLabeledToggleButton useSimpleOrbSwitch;
    public static ModLabeledToggleButton notReplaceTitleSwitch;
    public static ModLabeledToggleButton safeCampfireSwitch;
    public static ModLabeledToggleButton safePortraitSwitch;

    public static Color transparent = Color.WHITE.cpy();


    public VUPShionMod() {
        BaseMod.subscribe(this);
        transparent.a = 0.0f;
        BaseMod.addColor(CardColorEnum.VUP_Shion_LIME,
                Shion_Color, Shion_Color, Shion_Color, Shion_Color, Shion_Color, Shion_Color, Shion_Color,
                assetPath("img/cardui/Shion/512/bg_attack_lime.png"),
                assetPath("img/cardui/Shion/512/bg_skill_lime.png"),
                assetPath("img/cardui/Shion/512/bg_power_lime.png"),
                assetPath("img/cardui/Shion/512/card_lime_orb_w.png"),
                assetPath("img/cardui/Shion/1024/bg_attack_lime.png"),
                assetPath("img/cardui/Shion/1024/bg_skill_lime.png"),
                assetPath("img/cardui/Shion/1024/bg_power_lime.png"),
                assetPath("img/cardui/Shion/1024/card_lime_orb_w.png"),
                assetPath("img/cardui/Shion/512/card_lime_small_orb.png"));

        BaseMod.addColor(CardColorEnum.WangChuan_LIME,
                WangChuan_Color, WangChuan_Color, WangChuan_Color, WangChuan_Color, WangChuan_Color, WangChuan_Color, WangChuan_Color,
                assetPath("img/cardui/WangChuan/512/bg_attack_lime.png"),
                assetPath("img/cardui/WangChuan/512/bg_skill_lime.png"),
                assetPath("img/cardui/WangChuan/512/bg_power_lime.png"),
                assetPath("img/cardui/WangChuan/512/card_lime_orb.png"),
                assetPath("img/cardui/WangChuan/1024/bg_attack_lime.png"),
                assetPath("img/cardui/WangChuan/1024/bg_skill_lime.png"),
                assetPath("img/cardui/WangChuan/1024/bg_power_lime.png"),
                assetPath("img/cardui/Shion/1024/card_lime_orb_w.png"),
                assetPath("img/cardui/WangChuan/512/card_lime_small_orb.png"));

        BaseMod.addColor(CardColorEnum.Codex_LIME,
                WangChuan_Color, WangChuan_Color, WangChuan_Color, WangChuan_Color, WangChuan_Color, WangChuan_Color, WangChuan_Color,
                assetPath("img/cardui/WangChuan/512/bg_attack_lime.png"),
                assetPath("img/cardui/WangChuan/512/bg_skill_lime.png"),
                assetPath("img/cardui/WangChuan/512/bg_power_lime.png"),
                assetPath("img/cardui/WangChuan/512/card_lime_orb.png"),
                assetPath("img/cardui/WangChuan/1024/bg_attack_lime.png"),
                assetPath("img/cardui/WangChuan/1024/bg_skill_lime.png"),
                assetPath("img/cardui/WangChuan/1024/bg_power_lime.png"),
                assetPath("img/cardui/Shion/1024/card_lime_orb_w.png"),
                assetPath("img/cardui/WangChuan/512/card_lime_small_orb.png"));

        BaseMod.addColor(CardColorEnum.Liyezhu_LIME,
                Liyezhu_Color, Liyezhu_Color, Liyezhu_Color, Liyezhu_Color, Liyezhu_Color, Liyezhu_Color, Liyezhu_Color,
                assetPath("img/cardui/Liyezhu/512/bg_attack_lime.png"),
                assetPath("img/cardui/Liyezhu/512/bg_skill_lime.png"),
                assetPath("img/cardui/Liyezhu/512/bg_power_lime.png"),
                assetPath("img/cardui/Liyezhu/512/card_lime_orb.png"),
                assetPath("img/cardui/Liyezhu/1024/bg_attack_lime.png"),
                assetPath("img/cardui/Liyezhu/1024/bg_skill_lime.png"),
                assetPath("img/cardui/Liyezhu/1024/bg_power_lime.png"),
                assetPath("img/cardui/Shion/1024/card_lime_orb_w.png"),
                assetPath("img/cardui/Liyezhu/512/card_lime_small_orb.png"));

        BaseMod.addColor(CardColorEnum.EisluRen_LIME,
                EisluRen_Color, EisluRen_Color, EisluRen_Color, EisluRen_Color, EisluRen_Color, EisluRen_Color, EisluRen_Color,
                assetPath("img/cardui/Liyezhu/512/bg_attack_lime.png"),
                assetPath("img/cardui/Liyezhu/512/bg_skill_lime.png"),
                assetPath("img/cardui/Liyezhu/512/bg_power_lime.png"),
                assetPath("img/cardui/Liyezhu/512/card_lime_orb.png"),
                assetPath("img/cardui/Liyezhu/1024/bg_attack_lime.png"),
                assetPath("img/cardui/Liyezhu/1024/bg_skill_lime.png"),
                assetPath("img/cardui/Liyezhu/1024/bg_power_lime.png"),
                assetPath("img/cardui/Shion/1024/card_lime_orb_w.png"),
                assetPath("img/cardui/Liyezhu/512/card_lime_small_orb.png"));
    }

    public static String makeID(String id) {
        return MOD_ID + ":" + id;
    }

    public static String assetPath(String path) {
        return MOD_ID + "/" + path;
    }


    public static void unlockAllReskin() {
        CharacterSelectScreenPatches.skinManager.unlockAllSkin();
    }


    public static void initialize() {
        new VUPShionMod();
    }


    @Override
    public void receivePostCreateStartingRelics(AbstractPlayer.PlayerClass playerClass, ArrayList<String> arrayList) {
        if (SaveHelper.liyezhuRelic) {
            arrayList.add(FragmentsOfFaith.ID);
        }
    }

    @Override
    public void receivePostInitialize() {
        SaveHelper.loadSettings();
//        unlockAllReskin();
        Texture badgeTexture = new Texture(assetPath("/img/badge.png"));
        ModPanel settingsPanel = new ModPanel();
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        useSimpleOrbSwitch = new ModLabeledToggleButton(CardCrawlGame.languagePack.getUIString(makeID("ModSettings")).TEXT[0], 400.0f, 720.0f - 0 * 50.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, SaveHelper.useSimpleOrb, settingsPanel,
                (label) -> {
                }, (button) -> {
            SaveHelper.useSimpleOrb = button.enabled;
            SaveHelper.saveSettings();
        });

        notReplaceTitleSwitch = new ModLabeledToggleButton(CardCrawlGame.languagePack.getUIString(makeID("ModSettings")).TEXT[1], 400.0f, 720.0f - 1 * 50.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, SaveHelper.notReplaceTitle, settingsPanel,
                (label) -> {
                }, (button) -> {
            SaveHelper.notReplaceTitle = button.enabled;
            SaveHelper.saveSettings();
        });

        safeCampfireSwitch = new ModLabeledToggleButton(CardCrawlGame.languagePack.getUIString(makeID("ModSettings")).TEXT[2], 400.0f, 720.0f - 2 * 50.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, SaveHelper.safeCampfire, settingsPanel,
                (label) -> {
                }, (button) -> {
            SaveHelper.safeCampfire = button.enabled;
            SaveHelper.saveSettings();
            safeSwitch();
        });

        safePortraitSwitch = new ModLabeledToggleButton(CardCrawlGame.languagePack.getUIString(makeID("ModSettings")).TEXT[3], 400.0f, 720.0f - 3 * 50.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, SaveHelper.safePortrait, settingsPanel,
                (label) -> {
                }, (button) -> {
            SaveHelper.safePortrait = button.enabled;
            SaveHelper.saveSettings();
        });


        settingsPanel.addUIElement(useSimpleOrbSwitch);
        settingsPanel.addUIElement(notReplaceTitleSwitch);
        settingsPanel.addUIElement(safeCampfireSwitch);
        settingsPanel.addUIElement(safePortraitSwitch);


        ArrayList<AbstractPlayer.PlayerClass> list = new ArrayList<>();
        list.add(AbstractPlayerEnum.VUP_Shion);
        list.add(AbstractPlayerEnum.WangChuan);
        list.add(AbstractPlayerEnum.Liyezhu);

//        mod公共事件

        BaseMod.addEvent(new AddEventParams.Builder(CroissantEvent.ID, CroissantEvent.class) //Event ID//
                //Event Character//
                .spawnCondition(() -> !AbstractDungeon.id.equals(TheEnding.ID) && EnergyPanelPatches.isShionModChar())
                .create());


        BaseMod.addEvent(new AddEventParams.Builder(LostEquipment.ID, LostEquipment.class) //Event ID//
                //Event Character//

                .spawnCondition(() -> !AbstractDungeon.id.equals(TheEnding.ID) && EnergyPanelPatches.isShionModChar())
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(DaysGoneBy.ID, DaysGoneBy.class) //Event ID//
                .spawnCondition(() -> EnergyPanelPatches.isShionModChar())
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(FruitStall.ID, FruitStall.class) //Event ID//
                .spawnCondition(() -> AbstractDungeon.id.equals(TheCity.ID) && EnergyPanelPatches.isShionModChar())
                .create());


//        紫音事件

        BaseMod.addEvent(new AddEventParams.Builder(BreakAppointment.ID, BreakAppointment.class) //Event ID//
                //Event Character//
                .playerClass(AbstractPlayerEnum.VUP_Shion)
                .spawnCondition(() -> !AbstractDungeon.id.equals(TheEnding.ID))
                .create());


        BaseMod.addEvent(new AddEventParams.Builder(HolyJudgement.ID, HolyJudgement.class) //Event ID//
                //Event Character//
                .playerClass(AbstractPlayerEnum.VUP_Shion)
                .spawnCondition(() -> AbstractDungeon.id.equals(TheCity.ID))
                .create());


//        忘川事件

        BaseMod.addEvent(new AddEventParams.Builder(LakeAmidst.ID, LakeAmidst.class) //Event ID//
                .playerClass(AbstractPlayerEnum.WangChuan)
                .spawnCondition(() -> AbstractDungeon.id.equals(TheCity.ID))
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(VacuumRipples.ID, VacuumRipples.class) //Event ID//
                .playerClass(AbstractPlayerEnum.WangChuan)
                .spawnCondition(() -> AbstractDungeon.id.equals(TheCity.ID))
                .create());


        BaseMod.addEvent(new AddEventParams.Builder(BoundaryOfChaos.ID, BoundaryOfChaos.class) //Event ID//
                .playerClass(AbstractPlayerEnum.WangChuan)
                .spawnCondition(() -> AbstractDungeon.id.equals(TheBeyond.ID)
                        && (BoundaryOfChaos.hasCodex() || BoundaryOfChaos.hasCodex1() || BoundaryOfChaos.hasCodex2()))
                .create());


//      蓝宝事件
        BaseMod.addEvent(new AddEventParams.Builder(MentalBreakdown.ID, MentalBreakdown.class) //Event ID//
                .playerClass(AbstractPlayerEnum.Liyezhu)
                .spawnCondition(() -> AbstractDungeon.id.equals(TheBeyond.ID))
                .create());


        BaseMod.addEvent(new AddEventParams.Builder(DistantMemory.ID, DistantMemory.class) //Event ID//
                .playerClass(AbstractPlayerEnum.Liyezhu)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(SpiritSinkingRampage.ID, SpiritSinkingRampage.class) //Event ID//
                .playerClass(AbstractPlayerEnum.Liyezhu)
                .spawnCondition(() -> !AbstractDungeon.id.equals(Exordium.ID))
                .create());


//      深空
        BaseMod.addEvent(new AddEventParams.Builder(Newborn.ID, Newborn.class) //Event ID//
                //Event Character//
                .spawnCondition(() -> AbstractDungeon.id.equals(TheEnding.ID))
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(Contact.ID, Contact.class) //Event ID//
                //Event Character//
                .spawnCondition(() -> AbstractDungeon.id.equals(TheEnding.ID))
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(Prophesy.ID, Prophesy.class) //Event ID//
                //Event Character//
                .spawnCondition(() -> AbstractDungeon.id.equals(TheEnding.ID))
                .create());

//      添加boss
        BaseMod.addMonster(PlagaAMundo.ID, () -> new PlagaAMundo());
        BaseMod.addMonster(RitaShop.ID, () -> new RitaShop());


//        加药水
        BaseMod.addPotion(PlanedModify.class, PotionPlaceHolderColor, PotionPlaceHolderColor, null, PlanedModify.POTION_ID, AbstractPlayerEnum.VUP_Shion);
        BaseMod.addPotion(CorGladiiFragment.class, PotionPlaceHolderColor, PotionPlaceHolderColor, null, CorGladiiFragment.POTION_ID, AbstractPlayerEnum.WangChuan);
        BaseMod.addPotion(WordFragment.class, PotionPlaceHolderColor, PotionPlaceHolderColor, null, WordFragment.POTION_ID, AbstractPlayerEnum.Liyezhu);
//        BaseMod.addPotion(TimeFragment.class, PotionPlaceHolderColor, PotionPlaceHolderColor, null, TimeFragment.POTION_ID);
        BaseMod.addPotion(FlashBang.class, PotionPlaceHolderColor, PotionPlaceHolderColor, null, FlashBang.POTION_ID);

        BaseMod.addPotion(MinamiDepository.class, PotionPlaceHolderColor, PotionPlaceHolderColor, null, MinamiDepository.POTION_ID);
        BaseMod.addPotion(MinamiHand.class, PotionPlaceHolderColor, PotionPlaceHolderColor, null, MinamiHand.POTION_ID);
        BaseMod.addPotion(MinamiReserve.class, PotionPlaceHolderColor, PotionPlaceHolderColor, null, MinamiReserve.POTION_ID);

//        BaseMod.addPotion(ShoppingVoucher.class, PotionPlaceHolderColor, PotionPlaceHolderColor, null, ShoppingVoucher.POTION_ID);

        BaseMod.addPotion(WorldLeaf.class, PotionPlaceHolderColor, PotionPlaceHolderColor, null, WorldLeaf.POTION_ID);
        BaseMod.addPotion(Bento.class, PotionPlaceHolderColor, PotionPlaceHolderColor, null, Bento.POTION_ID);
        BaseMod.addPotion(HolyWater.class, PotionPlaceHolderColor, PotionPlaceHolderColor, null, HolyWater.POTION_ID);
        BaseMod.addPotion(MagiaMagazine.class, PotionPlaceHolderColor, PotionPlaceHolderColor, null, MagiaMagazine.POTION_ID);
        BaseMod.addPotion(Cola.class, PotionPlaceHolderColor, PotionPlaceHolderColor, null, Cola.POTION_ID);
        BaseMod.addPotion(Claymore.class, PotionPlaceHolderColor, PotionPlaceHolderColor, null, Claymore.POTION_ID);
        BaseMod.addPotion(UAV.class, PotionPlaceHolderColor, PotionPlaceHolderColor, null, UAV.POTION_ID);
        BaseMod.addPotion(TransitionGenerator.class, PotionPlaceHolderColor, PotionPlaceHolderColor, null, TransitionGenerator.POTION_ID);


    }

    public static void safeSwitch() {
        AbstractScenePatches.campfire_Wc = ImageMaster.loadImage("VUPShionMod/characters/WangChuan/" + (SaveHelper.safeCampfire ? "Campfire2.png" : "Campfire.png"));
        AbstractScenePatches.campfire_Li = ImageMaster.loadImage("VUPShionMod/characters/Liyezhu/" + (SaveHelper.safeCampfire ? "Campfire2.png" : "Campfire.png"));

        for (AbstractSkinCharacter character : CharacterSelectScreenPatches.skinManager.skinCharacters) {
            for (AbstractSkin skin : character.skins)
                skin.safeSwitch();
        }

    }

    @Override
    public void receiveAddAudio() {
        for (int i = 1; i <= 18; i++) {
            BaseMod.addAudio("SHION_" + i, assetPath("audio/sfx/shion" + i + ".ogg"));
        }

        BaseMod.addAudio(makeID("RitaB_Attack0"), assetPath("/audio/sound/Rita/VO/RitaB_Attack0.wav"));
        BaseMod.addAudio(makeID("RitaB_Carnage"), assetPath("/audio/sound/Rita/VO/RitaB_Carnage.wav"));
        BaseMod.addAudio(makeID("RitaB_CharacterSelect"), assetPath("/audio/sound/Rita/VO/RitaB_CharacterSelect.wav"));
        BaseMod.addAudio(makeID("RitaB_CombatStart0"), assetPath("/audio/sound/Rita/VO/RitaB_CombatStart0.wav"));
        BaseMod.addAudio(makeID("RitaB_CombatStart1"), assetPath("/audio/sound/Rita/VO/RitaB_CombatStart1.wav"));
        BaseMod.addAudio(makeID("RitaB_CombatStart2"), assetPath("/audio/sound/Rita/VO/RitaB_CombatStart2.wav"));
        BaseMod.addAudio(makeID("RitaB_CombatStart3"), assetPath("/audio/sound/Rita/VO/RitaB_CombatStart3.wav"));
        BaseMod.addAudio(makeID("RitaB_DarkBarrier0"), assetPath("/audio/sound/Rita/VO/RitaB_DarkBarrier0.wav"));
        BaseMod.addAudio(makeID("RitaB_DarkBarrier1"), assetPath("/audio/sound/Rita/VO/RitaB_DarkBarrier1.wav"));
        BaseMod.addAudio(makeID("RitaB_Die"), assetPath("/audio/sound/Rita/VO/RitaB_Die.wav"));
        BaseMod.addAudio(makeID("RitaB_Expunger"), assetPath("/audio/sound/Rita/VO/RitaB_Expunger.wav"));
        BaseMod.addAudio(makeID("RitaB_FiendFire0"), assetPath("/audio/sound/Rita/VO/RitaB_FiendFire0.wav"));
        BaseMod.addAudio(makeID("RitaB_FiendFire1"), assetPath("/audio/sound/Rita/VO/RitaB_FiendFire1.wav"));
        BaseMod.addAudio(makeID("RitaB_GenocideCutter0"), assetPath("/audio/sound/Rita/VO/RitaB_GenocideCutter0.wav"));
        BaseMod.addAudio(makeID("RitaB_GenocideCutter1"), assetPath("/audio/sound/Rita/VO/RitaB_GenocideCutter1.wav"));
        BaseMod.addAudio(makeID("RitaB_HellPress"), assetPath("/audio/sound/Rita/VO/RitaB_HellPress.wav"));
        BaseMod.addAudio(makeID("RitaB_Hit0"), assetPath("/audio/sound/Rita/VO/RitaB_Hit0.wav"));
        BaseMod.addAudio(makeID("RitaB_Hit1"), assetPath("/audio/sound/Rita/VO/RitaB_Hit1.wav"));
        BaseMod.addAudio(makeID("RitaB_Hit2"), assetPath("/audio/sound/Rita/VO/RitaB_Hit2.wav"));
        BaseMod.addAudio(makeID("RitaB_Hit3"), assetPath("/audio/sound/Rita/VO/RitaB_Hit3.wav"));
        BaseMod.addAudio(makeID("RitaB_Hit4"), assetPath("/audio/sound/Rita/VO/RitaB_Hit4.wav"));
        BaseMod.addAudio(makeID("RitaB_Hit5"), assetPath("/audio/sound/Rita/VO/RitaB_Hit5.wav"));
        BaseMod.addAudio(makeID("RitaB_MeteorStrike"), assetPath("/audio/sound/Rita/VO/RitaB_MeteorStrike.wav"));
        BaseMod.addAudio(makeID("RitaB_Ragnarok"), assetPath("/audio/sound/Rita/VO/RitaB_Ragnarok.wav"));
        BaseMod.addAudio(makeID("RitaB_Recover0"), assetPath("/audio/sound/Rita/VO/RitaB_Recover0.wav"));
        BaseMod.addAudio(makeID("RitaB_Recover1"), assetPath("/audio/sound/Rita/VO/RitaB_Recover1.wav"));
        BaseMod.addAudio(makeID("RitaB_Recover2"), assetPath("/audio/sound/Rita/VO/RitaB_Recover2.wav"));
        BaseMod.addAudio(makeID("RitaB_Recover3"), assetPath("/audio/sound/Rita/VO/RitaB_Recover3.wav"));
        BaseMod.addAudio(makeID("RitaB_Recover4"), assetPath("/audio/sound/Rita/VO/RitaB_Recover4.wav"));
        BaseMod.addAudio(makeID("RitaB_Shockwave"), assetPath("/audio/sound/Rita/VO/RitaB_Shockwave.wav"));
        BaseMod.addAudio(makeID("RitaB_Skill0"), assetPath("/audio/sound/Rita/VO/RitaB_Skill0.wav"));
        BaseMod.addAudio(makeID("RitaB_TrueMod"), assetPath("/audio/sound/Rita/VO/RitaB_TrueMod.wav"));
        BaseMod.addAudio(makeID("RitaB_Victory0"), assetPath("/audio/sound/Rita/VO/RitaB_Victory0.wav"));
        BaseMod.addAudio(makeID("RitaB_Victory1"), assetPath("/audio/sound/Rita/VO/RitaB_Victory1.wav"));

    }

    @Override
    public void receivePostDungeonInitialize() {
    }

    @Override
    public void receiveStartAct() {
        if (AbstractDungeon.floorNum == 0) {
            if (AbstractDungeon.player.hasRelic(ConcordArray.ID)) {
                SaveHelper.gravityFinFunnelLevel = 2;
                SaveHelper.investigationFinFunnelLevel = 2;
                SaveHelper.pursuitFinFunnelLevel = 2;
                SaveHelper.dissectingFinFunnelLevel = 2;
                SaveHelper.matrixFinFunnelLevel = 2;
            } else {
                SaveHelper.gravityFinFunnelLevel = 1;
                SaveHelper.investigationFinFunnelLevel = 1;
                SaveHelper.pursuitFinFunnelLevel = 1;
                SaveHelper.dissectingFinFunnelLevel = 1;
                SaveHelper.matrixFinFunnelLevel = 1;
            }
            SaveHelper.activeFinFunnel = "GravityFinFunnel";
            SaveHelper.saveFinFunnels();

            SaveHelper.fightSpecialBoss = false;
            SaveHelper.fightSpecialBossWithout = false;
            SaveHelper.isHardMod = false;

            SansMeterSave.sansMeterSaveAmount = 100;

            SaveHelper.saveSkins();

            SaveHelper.liyezhuRelic = false;
            SaveHelper.saveSettings();
        }

        if (AbstractDungeon.actNum == 3) {
            for (AbstractRelic relic : AbstractDungeon.player.relics) {
                if (relic instanceof FragmentsOfFaith) {
                    ((FragmentsOfFaith) relic).upgrade();
                }
            }
        }

        if (AbstractDungeon.player.hasRelic(DimensionSplitterAria.ID)) {
            AbstractRelic relic = AbstractDungeon.player.getRelic(DimensionSplitterAria.ID);
            relic.flash();
            relic.counter++;
            ((DimensionSplitterAria) relic).setDescriptionAfterLoading();
        }

        if (AbstractDungeon.player.hasRelic(FragmentsOfFaith.ID) && AbstractDungeon.actNum == 3) {
            FragmentsOfFaith relic = (FragmentsOfFaith) AbstractDungeon.player.getRelic(FragmentsOfFaith.ID);
            relic.upgrade();
        }
    }


    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new Shion(Shion.charStrings.NAMES[1], AbstractPlayerEnum.VUP_Shion),
                assetPath("characters/Shion/Button.png"),
                assetPath("characters/Shion/portrait.png"),
                AbstractPlayerEnum.VUP_Shion);

        BaseMod.addCharacter(new WangChuan(WangChuan.charStrings.NAMES[1], AbstractPlayerEnum.WangChuan),
                assetPath("characters/WangChuan/Button.png"),
                assetPath("characters/Shion/portrait.png"),
                AbstractPlayerEnum.WangChuan);

        BaseMod.addCharacter(new Liyezhu(Liyezhu.charStrings.NAMES[1], AbstractPlayerEnum.Liyezhu),
                assetPath("characters/Liyezhu/Button.png"),
                assetPath("characters/Shion/portrait.png"),
                AbstractPlayerEnum.Liyezhu);

        BaseMod.addCharacter(new EisluRen(EisluRen.charStrings.NAMES[1], AbstractPlayerEnum.EisluRen),
                assetPath("characters/EisluRen/Button.png"),
                assetPath("characters/EisluRen/portrait.png"),
                AbstractPlayerEnum.EisluRen);

        BaseMod.addSaveField("SansMeterSave", new SansMeterSave());
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new SecondaryMagicVariable());
        List<CustomCard> cards = new ArrayList<>();

//        紫音
//        cards.add(new Strike_Shion());
        cards.add(new Strike_Shion2());
//        cards.add(new Defend_Shion());
        cards.add(new Defend_Shion2());
        cards.add(new DefenseSystemCharging());
        cards.add(new DeploymentOfDefenseSystem());
        cards.add(new AttackSystemPreload());
        cards.add(new DefenseSystemPreload());
        cards.add(new AnalyseSystemPreload());
        cards.add(new QuickAttack());
        cards.add(new QuickDefend());
        cards.add(new QuickScreen());
        cards.add(new DimensionSplitting());
        cards.add(new AnastasiaCore());
        cards.add(new Goodbye());
        cards.add(new AttackInitiation());
        cards.add(new EnduranceInitiation());
        cards.add(new AttackPreparation());
        cards.add(new SpeedShot());
//        cards.add(new Strafe());
        cards.add(new Strafe2());
        cards.add(new EnhancedWeapon());
        cards.add(new Boot());
        cards.add(new FirstStrike());
        cards.add(new SpeedSlash());
        cards.add(new Rob());
        cards.add(new Gravitonium());
        cards.add(new QuickTrigger());
        cards.add(new GravityImpact());
        cards.add(new DefensiveOrder());


//        紫音-克洛伊斯
        cards.add(new TimeBacktracking());
        cards.add(new TimeSlack());
        cards.add(new TimeStop());
        cards.add(new TimeBomb());
        cards.add(new HourHand());
        cards.add(new MinuteHand());
        cards.add(new SecondHand());
        cards.add(new CrackOfTime());
        cards.add(new ReleaseFormKuroisu());
        cards.add(new TimeWarp());
        cards.add(new BlackHand());
        cards.add(new TimeOverload());
        cards.add(new Limit());
        cards.add(new OverspeedField());
        cards.add(new DelayAvatar());


//        紫音-南小棉
        cards.add(new FinFunnelActive());
        cards.add(new AttackWithDefense());
        cards.add(new LockIndication());
        cards.add(new TacticalLayout());
        cards.add(new TacticalLink());
        cards.add(new FinFunnelSupport());
        cards.add(new SuperCharge());
//        cards.add(new Anticoagulation());
        cards.add(new Lure());
        cards.add(new ReleaseFormMinami());
        cards.add(new AnestheticReagent());
        cards.add(new EnhancedSupport());
        cards.add(new BattlefieldHeritage());
        cards.add(new GravityLoading());
        cards.add(new FirePower());
        cards.add(new ArmedToTheTeeth());
        cards.add(new GravityCharging());


//      紫音-黎夜竹
        cards.add(new IntroductionSilence());
        cards.add(new SacredAdvice());
        cards.add(new DivineRedemption());
        cards.add(new BlueBlade());
        cards.add(new HolyCharge());
        cards.add(new StrengthPray());
        cards.add(new Pray());
        cards.add(new SantaCroce());
        cards.add(new ReleaseFormLiyezhu());
        cards.add(new Awaken());
        cards.add(new HolyCoffinSinkingSpirit());
        cards.add(new HolySlashDown());
        cards.add(new PainfulConfession());
        cards.add(new HolyCoffinRelease());
        cards.add(new BlueRose());

//        紫音-anastasia
        cards.add(new FinFunnelUpgrade());
        cards.add(new AnastasiaPlan());
//        cards.add(new AttackOrderAlpha());
//        cards.add(new AttackOrderBeta());
//        cards.add(new AttackOrderDelta());
//        cards.add(new AttackOrderGamma());
        cards.add(new EnergyReserve());
        cards.add(new LockOn());
        cards.add(new Reboot());
        cards.add(new TeamWork());
        cards.add(new Read());
        cards.add(new OverloadFortress());


//        cards.add(new InvestigationFinFunnelUpgrade());
        cards.add(new GravityFinFunnelUpgrade());
        cards.add(new PursuitFinFunnelUpgrade());
        cards.add(new DissectingFinFunnelUpgrade());
        cards.add(new MatrixUpgrade());

        cards.add(new FunnelMatrix());

//        cards.add(new StrikeIntegrated());
        cards.add(new ChainPursuit());
        cards.add(new AoeAnalysis());
        cards.add(new GravityVortex());
        cards.add(new GravityRepression());
        cards.add(new TrackingAnalysis());
        cards.add(new MatrixAmplify());
//        cards.add(new CollaborativeInvestigation());
        cards.add(new HyperDimensionalMatrix());

        cards.add(new WideAreaLocking());
        cards.add(new ReleaseFormEisluRen());

//        忘川
        cards.add(new HiltBash());
        cards.add(new Slide());
        cards.add(new PreExecution());
        cards.add(new Sheathe());
        cards.add(new InTheBlink());
        cards.add(new Warp());

//        忘川-拔刀系
        cards.add(new GlandesMagicae());
        cards.add(new StrideSlash());
        cards.add(new OnrushingTip());
        cards.add(new VertexGladii());
        cards.add(new SeverWrist());
        cards.add(new SeverAnkle());
        cards.add(new SeverPetal());
        cards.add(new AethereScindo());
        cards.add(new GladiiInfiniti());
        cards.add(new Guard());
        cards.add(new LunaMergo());
        cards.add(new Reflect());
        cards.add(new Sharpen());
        cards.add(new FlawlessParry());
        cards.add(new Moonstrider());
        cards.add(new SubLuna());
        cards.add(new SeverCurrent());
        cards.add(new WeatherEye());
        cards.add(new Focus());
        cards.add(new OffensiveAdvance());
        cards.add(new EbbAndFlow());
        cards.add(new PetalsFall());
        cards.add(new Poise());
        cards.add(new FullBloom());

//        忘川-魔法系
        cards.add(new BombardaMagica());
        cards.add(new AfflictioBellumque());
        cards.add(new RosaSpinaque());
        cards.add(new MorsLibraque());
        cards.add(new HeliumLuxque());
        cards.add(new Skip());
        cards.add(new DistantiaAbsensque());
        cards.add(new VitaNaturaque());
        cards.add(new CorLapisque());
        cards.add(new AnimaSpiritusque());
        cards.add(new MensVirtusque());
        cards.add(new ElysiumSpesque());

//        忘川-支援
        cards.add(new SupportShion());
        cards.add(new BlockThis());
        cards.add(new Replenish());
        cards.add(new VampireForm());

//        忘川-魔能过载
        cards.add(new Alleviator());
        cards.add(new Recharger());
        cards.add(new Accelerator());
        cards.add(new Recoiler());
        cards.add(new Gravitater());
        cards.add(new Superloader());
        cards.add(new Antisequencer());
        cards.add(new GensBombardae());
        cards.add(new MagicProjection());
        cards.add(new NihilImmensum());
        cards.add(new ArtificiumMundi());
        cards.add(new OculusMortis());

        //        忘川-攻击牌补充
        cards.add(new SpaceSlice());
        cards.add(new Exile());
        cards.add(new PhantomChop());
        cards.add(new MentalMotivationChop());
        cards.add(new MeltDowner());
        cards.add(new CirrocumulusChop());
        cards.add(new CoronaChop());
        cards.add(new MotherRosario());
        cards.add(new BreakChop());
        cards.add(new OppressiveSword());

//        通典
        cards.add(new ChaosNimius());
        cards.add(new ChaosRapidus());
        cards.add(new LuxRapida());
        cards.add(new LuxConstans());
        cards.add(new CaligoConstans());
        cards.add(new CaligoRapida());
        cards.add(new CaligoRapida());
        cards.add(new CaelumNimium());
        cards.add(new CaelumRapidum());
        cards.add(new TerraNimia());
        cards.add(new TerraRapida());
        cards.add(new HomoRapidus());
        cards.add(new HomoNimius());
        cards.add(new AquaConstans());
        cards.add(new AquaRapida());
        cards.add(new IgnisRapidus());
        cards.add(new IgnisNimius());
        cards.add(new VentusRapidus());
        cards.add(new VentusNimius());
        cards.add(new AurumNimium());
        cards.add(new AurumFidum());
        cards.add(new LignumNimium());
        cards.add(new LignumConstans());
        cards.add(new TonitrusConstans());
        cards.add(new TonitrusRapidus());

//        黎夜竹
        cards.add(new HolyLight());
        cards.add(new Barrier());
        cards.add(new SoothingScripture());
        cards.add(new EmanationOfIre());
        cards.add(new TranquilPrayer());
        cards.add(new EdgeOfSquall());
        cards.add(new RipsoulShrilling());
        cards.add(new JudgementOfSins());
        cards.add(new RealizingCanticle());
        cards.add(new BeinglessMoment());
        cards.add(new SoleAnthem());
        cards.add(new FlayTheEvil());
        cards.add(new PiousPhrase());
        cards.add(new RavingExcoriation());
        cards.add(new BlindDevotion());
        cards.add(new LimpidHeart());
        cards.add(new SanguinaryPrecept());
        cards.add(new FlickeringTip());
        cards.add(new InordinateAdmonition());
        cards.add(new ChasteReflection());
        cards.add(new AvariciousMotto());
        cards.add(new BurnishedRazor());

        cards.add(new PrincipledThievery());
        cards.add(new HeavenDecree());
        cards.add(new CelestialIncarnation());
        cards.add(new Calamity());
        cards.add(new HallowedCasket());
        cards.add(new EnsanguinedFigure());
        cards.add(new CrimsonDeluge());
        cards.add(new Asceticism());
        cards.add(new TranscendSoul());
        cards.add(new Arbitration());
        cards.add(new ProphecyOfDestruction());
        cards.add(new ProphecyOfSalvation());

        cards.add(new Sentence());
        cards.add(new SacredChop());
        cards.add(new SavageSeries());
        cards.add(new CruciformPenance());
        cards.add(new ReapTheSinful());
        cards.add(new Whisk());
        cards.add(new AnnihilatingChoir());
        cards.add(new SpiritImpact());
        cards.add(new CruelSword());
        cards.add(new Execution());
        cards.add(new Enchant());

        cards.add(new March());
        cards.add(new CompileSoul());
        cards.add(new RedEyes());
        cards.add(new Consult());
        cards.add(new Identify());
        cards.add(new MiseriamVoco());
        cards.add(new CorCrucis());
        cards.add(new VoluntasDoloris());
        cards.add(new ViaAfflictionis());
        cards.add(new ExitiumMaeroris());
        cards.add(new WoeIntoCasket());
        cards.add(new EvilOnMe());
        cards.add(new VerdictUponHeart());
        cards.add(new BrokenSanctuary());
        cards.add(new Precasting());
        cards.add(new LiReboot());
        cards.add(new ShionEmbodiment());


        cards.add(new LiXiaoNan());
        cards.add(new LiYueSheng());
        cards.add(new LiXiaoYa());
        cards.add(new LiyezhuUpgradeCard());


        for (CustomCard card : cards) {
            BaseMod.addCard(card);
            UnlockTracker.unlockCard(card.cardID);

            if (card instanceof AbstractShionAnastasiaCard)
                an_Cards.add(card);

            if (card instanceof AbstractShionKuroisuCard)
                ku_Cards.add(card);

            if (card instanceof AbstractShionLiyezhuCard)
                li_Cards.add(card);

            if (card instanceof AbstractShionMinamiCard)
                mi_Cards.add(card);

            if (card instanceof AbstractShionCard)
                shi_Cards.add(card);

            if (card instanceof AbstractCodexCard && !(card instanceof ChaosNimius) && !(card instanceof ChaosRapidus))
                codex_Cards.add(card);

        }
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new DimensionSplitterAria(), CardColorEnum.VUP_Shion_LIME);
        BaseMod.addRelicToCustomPool(new KuroisuDetermination(), CardColorEnum.VUP_Shion_LIME);
        BaseMod.addRelicToCustomPool(new Croissant(), CardColorEnum.VUP_Shion_LIME);
        BaseMod.addRelicToCustomPool(new OpticalCamouflage(), CardColorEnum.VUP_Shion_LIME);
        BaseMod.addRelicToCustomPool(new Sniperscope(), CardColorEnum.VUP_Shion_LIME);
        BaseMod.addRelicToCustomPool(new AnastasiaNecklace(), CardColorEnum.VUP_Shion_LIME);
        BaseMod.addRelicToCustomPool(new BlueGiant(), CardColorEnum.VUP_Shion_LIME);
        BaseMod.addRelicToCustomPool(new BlueSupergiant(), CardColorEnum.VUP_Shion_LIME);
        BaseMod.addRelicToCustomPool(new Drapery(), CardColorEnum.VUP_Shion_LIME);
        BaseMod.addRelicToCustomPool(new Parocheth(), CardColorEnum.VUP_Shion_LIME);
        BaseMod.addRelicToCustomPool(new ConcordSnipe(), CardColorEnum.VUP_Shion_LIME);
        BaseMod.addRelicToCustomPool(new ConcordArray(), CardColorEnum.VUP_Shion_LIME);
        BaseMod.addRelicToCustomPool(new ConcordCharge(), CardColorEnum.VUP_Shion_LIME);

        BaseMod.addRelicToCustomPool(new Nebula(), CardColorEnum.WangChuan_LIME);
        BaseMod.addRelicToCustomPool(new Protostar(), CardColorEnum.WangChuan_LIME);
        BaseMod.addRelicToCustomPool(new TrackingBeacon(), CardColorEnum.WangChuan_LIME);
        BaseMod.addRelicToCustomPool(new StarQuakes(), CardColorEnum.WangChuan_LIME);
        BaseMod.addRelicToCustomPool(new StarBreaker(), CardColorEnum.WangChuan_LIME);
        BaseMod.addRelicToCustomPool(new PureHeart(), CardColorEnum.WangChuan_LIME);
        BaseMod.addRelicToCustomPool(new WhitePurity(), CardColorEnum.WangChuan_LIME);
        BaseMod.addRelicToCustomPool(new SapphireRoseNecklace(), CardColorEnum.WangChuan_LIME);
        BaseMod.addRelicToCustomPool(new TheRipple(), CardColorEnum.WangChuan_LIME);
        BaseMod.addRelicToCustomPool(new PlacidAqua(), CardColorEnum.WangChuan_LIME);
        BaseMod.addRelicToCustomPool(new WhiteRose(), CardColorEnum.WangChuan_LIME);
        BaseMod.addRelicToCustomPool(new PurityWhiteRose(), CardColorEnum.WangChuan_LIME);
        BaseMod.addRelicToCustomPool(new WaveSlasher(), CardColorEnum.WangChuan_LIME);
        BaseMod.addRelicToCustomPool(new WaveBreaker(), CardColorEnum.WangChuan_LIME);
        BaseMod.addRelicToCustomPool(new PrototypeCup(), CardColorEnum.WangChuan_LIME);
        BaseMod.addRelicToCustomPool(new MagiaCup(), CardColorEnum.WangChuan_LIME);
        BaseMod.addRelicToCustomPool(new MagiaSwordRed(), CardColorEnum.WangChuan_LIME);
        BaseMod.addRelicToCustomPool(new MagiaSwordRuby(), CardColorEnum.WangChuan_LIME);


        BaseMod.addRelicToCustomPool(new MartyrVessel(), CardColorEnum.Liyezhu_LIME);
        BaseMod.addRelicToCustomPool(new HallowedCase(), CardColorEnum.Liyezhu_LIME);
        BaseMod.addRelicToCustomPool(new AbyssalCrux(), CardColorEnum.Liyezhu_LIME);
        BaseMod.addRelicToCustomPool(new Inhibitor(), CardColorEnum.Liyezhu_LIME);
        BaseMod.addRelicToCustomPool(new UnknownDust(), CardColorEnum.Liyezhu_LIME);
        BaseMod.addRelicToCustomPool(new TimeReversalBullet(), CardColorEnum.Liyezhu_LIME);
        BaseMod.addRelicToCustomPool(new DemonSword(), CardColorEnum.Liyezhu_LIME);
        BaseMod.addRelicToCustomPool(new QueenShield(), CardColorEnum.Liyezhu_LIME);

        BaseMod.addRelicToCustomPool(new ShieldHRzy1(), CardColorEnum.EisluRen_LIME);

//共享遗物
        BaseMod.addRelic(new TrainingScabbard(), RelicType.SHARED);
        BaseMod.addRelic(new TrainingLightsaber(), RelicType.SHARED);
        BaseMod.addRelic(new TrainingPropeller(), RelicType.SHARED);
        BaseMod.addRelic(new FishingRod(), RelicType.SHARED);
        BaseMod.addRelic(new PodBot(), RelicType.SHARED);
        BaseMod.addRelic(new OldFinFunnel(), RelicType.SHARED);
        BaseMod.addRelic(new DragonScales(), RelicType.SHARED);
        BaseMod.addRelic(new WingsClergy(), RelicType.SHARED);
        BaseMod.addRelic(new OldOmamori(), RelicType.SHARED);
        BaseMod.addRelic(new BlankMap(), RelicType.SHARED);
        BaseMod.addRelic(new CombatNotes(), RelicType.SHARED);
        BaseMod.addRelic(new AttackCircuit(), RelicType.SHARED);
        BaseMod.addRelic(new TotipotentCircuit(), RelicType.SHARED);
        BaseMod.addRelic(new MedicalCollar(), RelicType.SHARED);
        BaseMod.addRelic(new CardRecorder(), RelicType.SHARED);
        BaseMod.addRelic(new Memento(), RelicType.SHARED);
        BaseMod.addRelic(new TimeConfetti(), RelicType.SHARED);
        BaseMod.addRelic(new BladeFragment(), RelicType.SHARED);

//       事件遗物
        BaseMod.addRelic(new FragmentsOfFaith(), RelicType.SHARED);
        BaseMod.addRelic(new FruitCake(), RelicType.SHARED);


    }


    private Settings.GameLanguage languageSupport() {
        switch (Settings.language) {
            case ZHS:
                return Settings.language;
            case DEU:
                return Settings.language;
//            case JPN:
//                return Settings.language;
            case ZHT:
                return Settings.language;
            default:
                return Settings.GameLanguage.ENG;
        }
    }

    public void receiveEditStrings() {
        Settings.GameLanguage language = languageSupport();
        loadLocStrings(Settings.GameLanguage.ENG);
        if (!language.equals(Settings.GameLanguage.ENG)) {
            loadLocStrings(language);
        }

    }

    private void loadLocStrings(Settings.GameLanguage language) {
        String path = "localization/" + language.toString().toLowerCase() + "/";

        BaseMod.loadCustomStringsFile(AchievementStrings.class, assetPath(path + "AchievementStrings.json"));
        BaseMod.loadCustomStringsFile(EventStrings.class, assetPath(path + "EventStrings.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class, assetPath(path + "UIStrings.json"));
        BaseMod.loadCustomStringsFile(PotionStrings.class, assetPath(path + "PotionStrings.json"));
        BaseMod.loadCustomStringsFile(CardStrings.class, assetPath(path + "CardStrings.json"));
        BaseMod.loadCustomStringsFile(MonsterStrings.class, assetPath(path + "MonsterStrings.json"));
        BaseMod.loadCustomStringsFile(PowerStrings.class, assetPath(path + "PowerStrings.json"));
        BaseMod.loadCustomStringsFile(RelicStrings.class, assetPath(path + "RelicStrings.json"));
        BaseMod.loadCustomStringsFile(CharacterStrings.class, assetPath(path + "CharacterStrings.json"));
        BaseMod.loadCustomStringsFile(OrbStrings.class, assetPath(path + "OrbStrings.json"));
        BaseMod.loadCustomStringsFile(StanceStrings.class, assetPath(path + "StanceStrings.json"));
//        BaseMod.loadCustomStringsFile(TutorialStrings.class,assetPath(path + "TutorialStrings.json"));
    }


    private void loadLocKeywords(Settings.GameLanguage language) {
        String path = "localization/" + language.toString().toLowerCase() + "/";
        Gson gson = new Gson();
        String json = Gdx.files.internal(assetPath(path + "KeywordStrings.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword("vup_shion_mod", keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receiveEditKeywords() {

        Settings.GameLanguage language = languageSupport();

        // Load english first to avoid crashing if translation doesn't exist for something
        loadLocKeywords(Settings.GameLanguage.ENG);
        if (!language.equals(Settings.GameLanguage.ENG)) {
            loadLocKeywords(language);
        }
    }


}
