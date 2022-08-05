package VUPShionMod.minions;

import VUPShionMod.actions.Common.MinionIntentFlashAction;
import VUPShionMod.patches.AbstractPlayerPatches;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.red.Barricade;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.buttons.PeekButton;

import java.util.ArrayList;

public class MinionGroup {
    private ArrayList<AbstractPlayerMinion> minions = new ArrayList<>();

    public MinionGroup() {
    }

    public void addMinion(AbstractPlayerMinion minion) {
        this.minions.add(minion);
    }

    public void showIntent() {
        if (!this.minions.isEmpty())
            for (AbstractPlayerMinion minion : this.minions) {
                minion.createIntent();
            }
    }

    public void init() {
        if (!this.minions.isEmpty())
            for (AbstractPlayerMinion minion : this.minions) {
                minion.init();
            }
    }

    public void usePreBattleAction() {
    }

    public void updateAnimations() {
    }

    public void applyPreTurnLogic() {
        if (!this.minions.isEmpty())
            for (AbstractPlayerMinion minion : this.minions) {
                if (!minion.isDeadOrEscaped()) {
                    if (!minion.hasPower(Barricade.ID)) {
                        minion.loseBlock();
                    }

                    minion.applyStartOfTurnPostDrawPowers();
                }
            }
    }

    public void update() {
        if (!this.minions.isEmpty())
            for (AbstractPlayerMinion minion : this.minions) {
                minion.update();
            }
    }

    public void render(SpriteBatch sb) {
        if (!this.minions.isEmpty())
            for (AbstractPlayerMinion minion : this.minions) {
                if (minion.hb.hovered && !AbstractDungeon.player.isDead) {
                    if (!AbstractDungeon.isScreenUp || PeekButton.isPeeking) {
                        minion.renderTip(sb);
                    }
                }

                minion.render(sb);
            }
    }

    public void applyEndOfTurnPowers() {
        if (!this.minions.isEmpty())
            for (AbstractPlayerMinion minion : this.minions) {
                if (!minion.isDying && !minion.isEscaping) {
                    for (AbstractPower power : minion.powers)
                        power.atEndOfRound();
                }
            }

    }

    public void applyEndOfTurnTriggers() {
        if (!this.minions.isEmpty())
            for (AbstractPlayerMinion minion : this.minions) {
                if (!minion.isDying && !minion.isEscaping) {
                    AbstractDungeon.actionManager.addToBottom(new MinionIntentFlashAction(minion));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(1.5F));
                    AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                        @Override
                        public void update() {
                            minion.takeTurn();
                            minion.applyEndOfTurnTriggers();
                            isDone = true;
                        }
                    });


                }
            }
    }


    public void renderReticle(SpriteBatch sb) {
        if (!this.minions.isEmpty())
            for (AbstractPlayerMinion minion : this.minions) {
                if (!minion.isDeadOrEscaped()) {
                    minion.renderReticle(sb);
                }
            }
    }

    public static ArrayList<AbstractPlayerMinion> getMinions() {
        return AbstractPlayerPatches.AddFields.playerMinions.get(AbstractDungeon.player).minions;
    }

    public void onVictory() {
        for (AbstractPlayerMinion minion : this.minions) {
            for (AbstractPower power : minion.powers) {
                power.onVictory();
            }

            minion.dispose();
        }

        this.minions.clear();

    }

    public static boolean areMinionsBasicallyDead() {
        if (AbstractPlayerPatches.AddFields.playerMinions.get(AbstractDungeon.player).minions.isEmpty()) return true;
        for (AbstractPlayerMinion m : AbstractPlayerPatches.AddFields.playerMinions.get(AbstractDungeon.player).minions) {
            if (!m.isDying && !m.isEscaping) {
                if (m instanceof ElfMinion) {
                    if (((ElfMinion) m).cannotSelected) {
                        continue;
                    }
                }

                return false;
            }
        }
        return true;
    }

    public static AbstractPlayerMinion getCurrentMinion() {
        for (AbstractPlayerMinion m : AbstractPlayerPatches.AddFields.playerMinions.get(AbstractDungeon.player).minions) {
            if (!m.isDying && !m.isEscaping) {
                if (m instanceof ElfMinion) {
                    if (((ElfMinion) m).cannotSelected) {
                        continue;
                    }
                }
                return m;
            }
        }

        return null;
    }



}
