package VUPShionMod.relics;

import VUPShionMod.VUPShionMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
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
    public static final String IMG_PATH = "img/relics/dimensionSplitterAria.png";
    private static final String OUTLINE_PATH = "img/relics/dimensionSplitterAriaOutline.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public DimensionSplitterAria() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.STARTER, LandingSound.CLINK);
        this.counter = 0;
        setDescriptionAfterLoading();
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(this.DESCRIPTIONS[0], this.counter + 5, this.counter);
    }

    public void setDescriptionAfterLoading() {
        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public void atTurnStart() {
        AbstractMonster m = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.miscRng);
        if (m != null) {
            this.flash();
            addToBot(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
            addToBot(new VFXAction(new BorderFlashEffect(Color.RED)));
            addToBot(new VFXAction(new SmallLaserEffect(m.hb.cX, m.hb.cY, this.hb.cX, this.hb.cY), 0.3F));
            addToBot(new DamageAction(m, new DamageInfo(AbstractDungeon.player, this.counter + 5, DamageInfo.DamageType.THORNS)));
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    if (m.isDying && !m.hasPower(MinionPower.POWER_ID)) {
                        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, DimensionSplitterAria.this));
                        addToBot(new GainEnergyAction(1));
                        atTurnStart();
                    }
                    isDone = true;
                }
            });
        }
    }
}
