package VUPShionMod.actions.Shion;

import VUPShionMod.vfx.Atlas.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SecondHandAction extends AbstractGameAction {
    private final AbstractCard card;

    public SecondHandAction(AbstractCard card) {
        this.card = card;
    }


    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (this.target != null) {
            this.card.calculateCardDamage((AbstractMonster) this.target);
            addToTop(new SFXAction("BLUNT_FAST"));
            addToTop(new VFXAction(new AbstractAtlasGameEffect("Sparks 076 Impact Explosion Radial", this.target.hb.cX, this.target.hb.cY + 20.0f * Settings.scale,
                    128.0f, 133.0f, 3.0f * Settings.scale, 2, false)));
            addToTop(new DamageAction(this.target, new DamageInfo(AbstractDungeon.player,
                    this.card.damage, this.card.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        }

        this.isDone = true;
    }

}
