package VUPShionMod.monsters.Rita;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class AbstractMonsterIntent {
    public AbstractMonster m;
    public AbstractPlayer p;
    public String[] MOVES;
    public String[] DIALOG;

    protected int moveCount = 1;


    public AbstractMonsterIntent(AbstractMonster m) {
        this.m = m;
        this.p = AbstractDungeon.player;
        this.MOVES = CardCrawlGame.languagePack.getMonsterStrings(m.id).MOVES;
        this.DIALOG = CardCrawlGame.languagePack.getMonsterStrings(m.id).DIALOG;
    }

    public void initDamage(){}

    public void usePreBattleAction() {

    }

    public void takeTurn() {
    }


    public void getMove(int num) {

    }

    public void changeState(String stateName) {

    }


    protected void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    protected void addToTop(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }


}
