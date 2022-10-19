package VUPShionMod.actions.EisluRen;

import VUPShionMod.minions.AbstractPlayerMinion;
import VUPShionMod.minions.MinionGroup;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class PlayerMinionTakeTurnAction extends AbstractGameAction {
    public PlayerMinionTakeTurnAction() {
    }

    @Override
    public void update() {
        if (!MinionGroup.areMinionsBasicallyDead()) {
            AbstractPlayerMinion minion = MinionGroup.getCurrentMinion();
            if (minion != null)
                minion.takeTurn();
        }

        isDone = true;
    }
}
