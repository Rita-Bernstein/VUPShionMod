package VUPShionMod.actions.Shion;

import VUPShionMod.vfx.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class MotherRosarioAction extends AbstractGameAction {
    private AbstractCard card;

    public MotherRosarioAction(AbstractCard card) {
        this.card = card;
    }

    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (this.target != null) {
            this.card.calculateCardDamage((AbstractMonster) this.target);

            addToTop(new DamageAction(this.target, new DamageInfo(AbstractDungeon.player, this.card.damage, this.card.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));

            addToTop(new VFXAction(new AbstractAtlasGameEffect("Energy 105 Ray Left Loop", this.target.hb.cX, this.target.hb.cY,
                    50.0f, 50.0f, 10.0f * Settings.scale, 2, false)));

        }


        this.isDone = true;
    }
}