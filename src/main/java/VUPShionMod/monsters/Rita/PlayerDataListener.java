package VUPShionMod.monsters.Rita;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PlayerDataListener {
    private boolean playerHasHugeBlockUsed = false;
    private int playerHugeBlockTurn = 0;


    public PlayerDataListener() {
    }


    public void recordPlayerData() {
        if (AbstractDungeon.player.currentBlock >= 90)
            this.playerHugeBlockTurn++;
    }

    public boolean playerHasHugeBlock() {
        return playerHugeBlockTurn >= 3 && !this.playerHasHugeBlockUsed;
    }

    public void playerHasHugeBlockUsed() {
        this.playerHasHugeBlockUsed = true;
    }

}
