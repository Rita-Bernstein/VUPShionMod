package VUPShionMod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;


public abstract class AbstractVUPShionCard extends CustomCard {

    public int secondaryM;
    public int baseSecondaryM;
    public boolean upgradeSecondaryM;
    public boolean isSecondaryMModified;

    public AbstractVUPShionCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }


    public void updateDescription() {
        //Overwritten in cards
    }

    protected void upgradeSecondM(int amount) {
        this.baseSecondaryM += amount;
        this.secondaryM = this.baseSecondaryM;
        this.upgradeSecondaryM = true;
    }

    public void safeChangeSecondM(int amount) {
        this.baseSecondaryM += amount;
        if (this.baseSecondaryM < 0) this.baseSecondaryM = 0;
        this.secondaryM = this.baseSecondaryM;
        this.upgradeSecondaryM = true;
    }

    @Override
    public void displayUpgrades() {
        super.displayUpgrades();

        if (this.upgradeSecondaryM) {
            this.secondaryM = this.baseSecondaryM;
            this.isSecondaryMModified = true;
        }
    }

    public int getModifyAlterDamage() {
        return this.secondaryM;
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractVUPShionCard card = (AbstractVUPShionCard) super.makeStatEquivalentCopy();
        card.secondaryM = this.secondaryM;
        card.baseSecondaryM = this.baseSecondaryM;
        card.upgradeSecondaryM = this.upgradeSecondaryM;
        card.isSecondaryMModified = this.isSecondaryMModified;
        return card;
    }
}