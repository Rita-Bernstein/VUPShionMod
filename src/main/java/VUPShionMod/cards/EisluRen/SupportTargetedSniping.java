package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.JudgementAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SupportTargetedSniping extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(SupportTargetedSniping.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/ReleaseFormEisluRen.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public SupportTargetedSniping() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 33;
        this.baseDamage = 20;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new JudgementAction(m, this.magicNumber));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(0);
        }
    }
}
