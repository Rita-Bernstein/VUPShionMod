package VUPShionMod.cards.ShionCard.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.AbstractMinamiCard;
import VUPShionMod.powers.AnticoagulationPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Anticoagulation extends AbstractMinamiCard  {
    public static final String ID = VUPShionMod.makeID("Anticoagulation");
    public static final String IMG = VUPShionMod.assetPath("img/cards/minami/minami06.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Anticoagulation() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new AnticoagulationPower(m, this.magicNumber),this.magicNumber));
    }


    public AbstractCard makeCopy() {
        return new Anticoagulation();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.exhaust = false;
        }
    }
}