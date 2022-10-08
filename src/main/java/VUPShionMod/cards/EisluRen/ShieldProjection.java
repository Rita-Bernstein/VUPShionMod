package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.GainShieldAction;
import VUPShionMod.actions.Common.RemoveAllShieldAction;
import VUPShionMod.minions.AbstractPlayerMinion;
import VUPShionMod.minions.MinionGroup;
import VUPShionMod.msic.Shield;
import VUPShionMod.patches.ShieldPatches;
import VUPShionMod.powers.EisluRen.SupportGravitaterPower;
import VUPShionMod.powers.Monster.PlagaAMundo.FlyPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class ShieldProjection extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(ShieldProjection.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/ShieldProjection.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public ShieldProjection() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.selfRetain = true;
        this.returnToHand = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPlayerMinion elf = MinionGroup.getElfMinion();
        if(elf != null){
            addToBot(new RemoveAllShieldAction(p));
            addToBot(new GainShieldAction(elf, Shield.getShield(p).getCurrentShield()));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(0);
        }
    }
}
