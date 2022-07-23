package VUPShionMod.relics.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.TriggerDimensionSplitterAction;
import VUPShionMod.relics.AbstractShionRelic;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

public class DimensionSplitterAria extends AbstractShionRelic implements ClickableRelic {
    public static final String ID = VUPShionMod.makeID(DimensionSplitterAria.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/DimensionSplitterAria.png";
    private static final String OUTLINE_PATH = "img/relics/outline/DimensionSplitterAria.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public DimensionSplitterAria() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.STARTER, LandingSound.CLINK);
        this.counter = 0;
        setDescriptionAfterLoading();
    }

    @Override
    public void onRightClick() {
        if (!AbstractDungeon.actionManager.turnHasEnded && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT)
            if (this.counter >= 3) {
                this.pulse = true;
                addToBot(new TriggerDimensionSplitterAction());
                this.counter = 0;
                setDescriptionAfterLoading();
            }
    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
        this.pulse = false;
        setDescriptionAfterLoading();
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], this.counter);
    }

    public void setDescriptionAfterLoading() {
        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(DESCRIPTIONS[1], DESCRIPTIONS[2]));
        this.initializeTips();
    }

    @Override
    public void atTurnStart() {
        if (this.counter < 3)
            this.counter++;

        this.pulse = this.counter >= 3;
        setDescriptionAfterLoading();
    }

//    public void doDamage(AbstractMonster m, int extraDamage, boolean isLoseHP) {
//        if (m != null) {
//            this.flash();
//            addToBot(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
//            addToBot(new VFXAction(new BorderFlashEffect(Color.RED)));
//            addToBot(new VFXAction(new SmallLaserEffect(m.hb.cX, m.hb.cY, this.hb.cX, this.hb.cY), 0.3F));
//
//            if (isLoseHP)
//                addToBot(new LoseHPAction(m, AbstractDungeon.player, this.counter * 3 + 2 + extraDamage));
//            else
//                addToBot(new DamageAction(m, new DamageInfo(AbstractDungeon.player, this.counter * 3 + 2 + extraDamage,
//                        DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
//
//            addToBot(new AbstractGameAction() {
//                @Override
//                public void update() {
//                    if (m.isDying && !m.hasPower(MinionPower.POWER_ID)) {
//                        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, DimensionSplitterAria.this));
//                        addToBot(new GainEnergyAction(1));
//                        doDamage(extraDamage, isLoseHP);
//                    }
//                    isDone = true;
//                }
//            });
//        }
//    }
//
//    public void doDamage() {
//        doDamage(0, false);
//    }
//
//    public void doDamage(int extraDamage, boolean isLoseHP) {
//        AbstractMonster abstractMonster = AbstractDungeon.getRandomMonster();
//        if (abstractMonster != null)
//            doDamage(abstractMonster, extraDamage, isLoseHP);
//    }
}