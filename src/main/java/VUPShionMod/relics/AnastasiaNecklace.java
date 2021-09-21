package VUPShionMod.relics;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cutscenes.CGlayout;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.monsters.PlagaAMundoMinion;
import VUPShionMod.patches.AbstractPlayerEnum;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.powers.AttackOrderSpecialPower;
import VUPShionMod.powers.BleedingPower;
import VUPShionMod.powers.PursuitPower;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.relics.OnPlayerDeathRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerToRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.powers.BerserkPower;


public class AnastasiaNecklace extends CustomRelic implements OnPlayerDeathRelic {
    public static final String ID = VUPShionMod.makeID("AnastasiaNecklace");
    public static final String IMG_PATH = "img/relics/AnastasiaNecklace.png";
    private static final String OUTLINE_PATH = "img/relics/outline/AnastasiaNecklace.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));
    private static final Texture UPGRADE_IMG = new Texture(VUPShionMod.assetPath("img/relics/AnastasiaNecklaceUpgrade.png"));

    private boolean triggered = false;
    private boolean effectApplied = false;

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
        this.tips.add(new PowerTip(this.name, this.description));
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
        AbstractDungeon.player.energy.energy++;
        AbstractDungeon.player.energy.energyMaster++;
        if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion) {
            for (AbstractFinFunnel f : AbstractPlayerPatches.AddFields.finFunnelList.get(AbstractDungeon.player)) {
                f.upgradeLevel(9);
            }
        }
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new AttackOrderSpecialPower(AbstractDungeon.player)));
//        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BerserkPower(AbstractDungeon.player,1)));

        this.img = UPGRADE_IMG;
    }

    @Override
    public void atBattleStartPreDraw() {
        super.atBattleStartPreDraw();
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BarricadePower(AbstractDungeon.player)));

    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
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
            if (m.id.equals(PlagaAMundoMinion.ID)){
                canTrigger = true;
                break;
            }
        }

        if (!triggered && canTrigger) {
            triggerRelic();
            AbstractDungeon.player.halfDead = true;
            (AbstractDungeon.getCurrRoom()).cannotLose = true;
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        if (this.triggered)
            cg.render(sb);
    }

    @Override
    public void update() {
        super.update();
        if (this.triggered)
            cg.update();

        if (cg.isDone && !effectApplied) {
            effectApplied = true;
            AbstractDungeon.isScreenUp = false;
            GameCursor.hidden = false;
            AbstractDungeon.screen = AbstractDungeon.CurrentScreen.NONE;
            AbstractDungeon.player.halfDead = false;
            applyEffect();
        }
    }

    @Override
    public void renderInTopPanel(SpriteBatch sb) {
        super.renderInTopPanel(sb);

    }

    public void renderAbove(SpriteBatch sb) {
        if (this.triggered)
            cg.renderAbove(sb);
    }
}
