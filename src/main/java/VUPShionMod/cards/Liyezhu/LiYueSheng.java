package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Wangchuan.MarchDrawCardAction;
import VUPShionMod.powers.Liyezhu.SwearPower;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;

public class LiYueSheng extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(LiYueSheng.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/LiYueSheng.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public LiYueSheng() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 10;
        this.secondaryM = this.baseSecondaryM = 1;
        this.exhaust =true;
        this.selfRetain =true;
        GraveField.grave.set(this,true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("STANCE_ENTER_WRATH"));
        addToBot(new VFXAction(new BorderFlashEffect(Color.SCARLET, true)));
        addToBot(new VFXAction(new StanceChangeParticleGenerator(p.hb.cX, p.hb.cY, "Wrath")));
        addToBot(new ApplyPowerAction(p,p,new SwearPower(p,1)));

        int count = 0;
        for(AbstractPower power : AbstractDungeon.player.powers){
            if(power.type == AbstractPower.PowerType.DEBUFF)
                count++;
        }

        addToBot(new RemoveDebuffsAction(p));
        addToBot(new LoseHPAction(p,p,count*this.magicNumber));
        addToBot(new ApplyPowerAction(p,p,new StrengthPower(p,count*this.secondaryM)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(0);
        }
    }
}