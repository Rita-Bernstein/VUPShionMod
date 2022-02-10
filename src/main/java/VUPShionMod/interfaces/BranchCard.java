package VUPShionMod.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public interface BranchCard extends BranchableUpgradeCard {
    default void upgrade() {
        if (this instanceof AbstractCard) {
            possibleBranches().get(chosenBranch()).upgrade();
        } else {
            throw new NotImplementedException();
        }
    }
}