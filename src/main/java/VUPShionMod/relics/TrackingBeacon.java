package VUPShionMod.relics;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.WangChuan.MorsLibraque;
import VUPShionMod.character.Shion;
import VUPShionMod.character.WangChuan;
import VUPShionMod.cutscenes.CGlayout;
import VUPShionMod.effects.ShionBossBackgroundEffect;
import VUPShionMod.monsters.PlagaAMundoMinion;
import VUPShionMod.patches.AbstractPlayerEnum;
import VUPShionMod.powers.Unique.GravitoniumOverPower;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.relics.OnPlayerDeathRelic;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.FairyPotion;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.LizardTail;

public class TrackingBeacon extends AbstractShionRelic implements OnPlayerDeathRelic {
    public static final String ID = VUPShionMod.makeID("TrackingBeacon");
    public static final String IMG_PATH = "img/relics/TrackingBeacon.png";
    private static final String OUTLINE_PATH = "img/relics/outline/TrackingBeacon.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public boolean triggered = false;
    public boolean effectApplied = false;
    private boolean lockHealth = false;

    private CGlayout cg = new CGlayout("WangChuan");

    public TrackingBeacon() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
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
//        setDescriptionAfterLoading();

        CardCrawlGame.music.playTempBGM("VUPShionMod:Boss_Phase2");
        AbstractDungeon.player.increaseMaxHp(10, false);


        AbstractDungeon.player.movePosition(Settings.WIDTH * 0.1F, 340.0F * Settings.yScale);


        if (AbstractDungeon.player instanceof WangChuan) {
            ((WangChuan) AbstractDungeon.player).shionHelper = new Shion(Shion.charStrings.NAMES[1], AbstractPlayerEnum.VUP_Shion);
            ((WangChuan) AbstractDungeon.player).shionHelper.healthBarUpdatedEvent();
            ((WangChuan) AbstractDungeon.player).shionHelper.showHealthBar();
            ((WangChuan) AbstractDungeon.player).shionHelper.increaseMaxHp(600 - 88, false);

            addToBot(new ApplyPowerAction(((WangChuan) AbstractDungeon.player).shionHelper,
                    AbstractDungeon.player, new BarricadePower(((WangChuan) AbstractDungeon.player).shionHelper)));
            addToBot(new ApplyPowerAction(((WangChuan) AbstractDungeon.player).shionHelper,
                    AbstractDungeon.player, new GravitoniumOverPower(((WangChuan) AbstractDungeon.player).shionHelper)));
//            addToBot(new GainBlockAction(((WangChuan) AbstractDungeon.player).shionHelper, AbstractDungeon.player, 700));
//            ((WangChuan) AbstractDungeon.player).shionHelper.addBlock(700);

            addToBot(new GainBlockAction(((WangChuan) AbstractDungeon.player).shionHelper, AbstractDungeon.player, 700));

        }

        AbstractCard card = new MorsLibraque();
        card.upgrade();
        addToBot(new MakeTempCardInHandAction(card));

    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        if (this.lockHealth) {
            triggerRelic();
        }

        if (this.triggered)
            if (((WangChuan) AbstractDungeon.player).shionHelper != null)
                if (!((WangChuan) AbstractDungeon.player).shionHelper.halfDead)
                    addToBot(new GainBlockAction(((WangChuan) AbstractDungeon.player).shionHelper, AbstractDungeon.player, 700));

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
