package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.EisluRen.AddWingShieldDamageReduceAction;
import VUPShionMod.powers.EisluRen.ReduceDamagePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Station extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(Station.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/Station.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public Station() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber =this.baseMagicNumber = 2;
        this.tags.add(CardTags.STARTER_DEFEND);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new ReduceDamagePower(p,10)));
        addToBot(new AddWingShieldDamageReduceAction(this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(0);
        }
    }
}