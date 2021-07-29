package VUPShionMod.finfunnels;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.PursuitPower;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

public class PursuitFinFunnel extends AbstractFinFunnel {
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(VUPShionMod.makeID("PursuitFinFunnel"));
    private static final Texture IMG = new Texture(VUPShionMod.assetPath("img/finFunnels/pursuitFinFunnel.png"));

    public PursuitFinFunnel() {
        super();
        this.name = orbStrings.NAME;
        this.ID = VUPShionMod.makeID("PursuitFinFunnel");
        this.img = IMG;
    }

    @Override
    public void updateDescription() {
        this.description = orbStrings.DESCRIPTION[0] + this.level + orbStrings.DESCRIPTION[1];
    }

    @Override
    public void atTurnStart() {
        if (this.level <= 0) return;
        AbstractMonster m = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
        if (m != null) {
            fire(m, this.level, DamageInfo.DamageType.THORNS);
        }
    }

    @Override
    public void onPursuitEnemy(AbstractCreature target) {
        if (this.level <= 0) return;
        fire(target, this.level, DamageInfo.DamageType.THORNS);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(1, 1, 1, 1);
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y);
        this.renderText(sb);
        this.hb.render(sb);
    }

    @Override
    public void fire(AbstractCreature target, int damage, DamageInfo.DamageType type) {
        addToBot(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
        addToBot(new VFXAction(new BorderFlashEffect(Color.SKY)));
        addToBot(new VFXAction(new SmallLaserEffect(target.hb.cX, target.hb.cY, this.hb.cX, this.hb.cY), 0.3F));
        addToBot(new DamageAction(target, new DamageInfo(AbstractDungeon.player, damage, type)));
        if (this.level > 0) {
            addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new PursuitPower(target, this.level)));
        }
    }
}