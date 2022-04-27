package VUPShionMod.cards.ShionCard.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.AbstractShionLiyezhuCard;
import VUPShionMod.powers.Shion.HolyCoffinSinkingSpiritPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HolyCoffinSinkingSpirit extends AbstractShionLiyezhuCard {
    public static final String ID = VUPShionMod.makeID("HolyCoffinSinkingSpirit");
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/liyezhu/lyz11.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public HolyCoffinSinkingSpirit() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new HolyCoffinSinkingSpiritPower(p, this.magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(2);
        }
    }


}
