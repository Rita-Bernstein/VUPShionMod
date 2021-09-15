package VUPShionMod.relics;

import VUPShionMod.VUPShionMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

public class DimensionSplitterAria extends CustomRelic {
    public static final String ID = VUPShionMod.makeID("DimensionSplitterAria");
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
    public String getUpdatedDescription() {
        if (this.counter <= 1)
            return String.format(DESCRIPTIONS[1] + DESCRIPTIONS[0], this.counter * 3 + 2, this.counter);
        else
            return String.format(this.DESCRIPTIONS[0], this.counter * 3 + 2, this.counter);
    }

    public void setDescriptionAfterLoading() {
        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public void atTurnStart() {
        setDescriptionAfterLoading();
        if (this.counter > 1)
            doDamage();
    }

    public void doDamage(AbstractMonster m, int extraDamage, boolean isLoseHP) {
        if (m != null) {
            this.flash();
            addToBot(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
            addToBot(new VFXAction(new BorderFlashEffect(Color.RED)));
            addToBot(new VFXAction(new SmallLaserEffect(m.hb.cX, m.hb.cY, this.hb.cX, this.hb.cY), 0.3F));

            if (isLoseHP)
                addToBot(new LoseHPAction(m, AbstractDungeon.player, this.counter * 3 + 2 + extraDamage));
            else
                addToBot(new DamageAction(m, new DamageInfo(AbstractDungeon.player, this.counter * 3 + 2 + extraDamage,
                        DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));

            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    if (m.isDying && !m.hasPower(MinionPower.POWER_ID)) {
                        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, DimensionSplitterAria.this));
                        addToBot(new GainEnergyAction(1));
                        doDamage(extraDamage, isLoseHP);
                    }
                    isDone = true;
                }
            });
        }
    }

    public void doDamage() {
        doDamage(0, false);
    }

    public void doDamage(int extraDamage, boolean isLoseHP) {
        AbstractMonster abstractMonster = AbstractDungeon.getRandomMonster();
        if (abstractMonster != null)
            doDamage(abstractMonster, extraDamage, isLoseHP);
    }
}
