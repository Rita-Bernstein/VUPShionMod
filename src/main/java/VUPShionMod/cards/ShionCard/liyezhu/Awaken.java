package VUPShionMod.cards.ShionCard.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.LoseHyperdimensionalLinksAction;
import VUPShionMod.cards.ShionCard.AbstractShionLiyezhuCard;
import VUPShionMod.powers.Shion.HyperdimensionalLinksPower;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Awaken extends AbstractShionLiyezhuCard {
    public static final String ID = VUPShionMod.makeID("Awaken");
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/liyezhu/lyz10.png");
    private static final int COST = 2;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Awaken() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 3;
        this.exhaust = true;
        this.tags.add(CardTags.HEALING);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(HyperdimensionalLinksPower.POWER_ID)) {
            int amount = p.getPower(HyperdimensionalLinksPower.POWER_ID).amount;
            addToBot(new HealAction(p, p, amount * this.baseMagicNumber));
            addToBot(new LoseHyperdimensionalLinksAction(true));
        }
    }
}
