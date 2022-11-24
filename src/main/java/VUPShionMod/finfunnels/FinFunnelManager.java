package VUPShionMod.finfunnels;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.TurnTriggerAllFinFunnelAction;
import VUPShionMod.cards.ShionCard.anastasia.AttackOrderGamma;
import VUPShionMod.patches.AbstractPlayerEnum;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.skins.SkinManager;
import VUPShionMod.util.SaveHelper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.spine.Skeleton;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MathHelper;

import java.util.ArrayList;

public class FinFunnelManager {
    public ArrayList<AbstractFinFunnel> finFunnelList = new ArrayList<>();
    public Texture box;
    public float cX = 0.0F;
    public float cY = 0.0F;

    public AbstractFinFunnel selectedFinFunnel;

    public FinFunnelManager() {
        this.box = new Texture(VUPShionMod.assetPath("img/effects/finFunnelSelectedEffect.png"));
    }

    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new TurnTriggerAllFinFunnelAction(true));
        if (AbstractDungeon.player.hasPower(AttackOrderGamma.ID))
            AbstractDungeon.actionManager.addToBottom(new TurnTriggerAllFinFunnelAction(true));

        EnergyPanelPatches.energyUsedThisTurn = 1;

        if (!finFunnelList.isEmpty())
            for (AbstractFinFunnel finFunnel : finFunnelList) {
                finFunnel.atTurnStart();
            }
    }

    public void preBattlePrep() {
        if (finFunnelList != null)
            finFunnelList.clear();

        if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion)
            initializeFinFunnelShion();

        SaveHelper.loadFinFunnels();
        selectedFinFunnel = getFinFunnel(SaveHelper.activeFinFunnel);
        if (selectedFinFunnel != null) {
            this.cX = selectedFinFunnel.cX;
            this.cY = selectedFinFunnel.cY;
        }

        if (!finFunnelList.isEmpty())
            for (AbstractFinFunnel finFunnel : finFunnelList) {
                finFunnel.preBattlePrep();
            }
    }

    public void onVictory() {
        SaveHelper.saveFinFunnels();
    }

    public AbstractFinFunnel getFinFunnel(String id) {
        if (!finFunnelList.isEmpty())
            for (AbstractFinFunnel finFunnel : finFunnelList) {
                if (finFunnel.id.equals(id))
                    return finFunnel;
            }

        return null;
    }

    public static ArrayList<AbstractFinFunnel> getFinFunnelList() {
        return AbstractPlayerPatches.AddFields.finFunnelManager.get(AbstractDungeon.player).finFunnelList;
    }

    public static AbstractFinFunnel getSelectedFinFunnel() {
        return AbstractPlayerPatches.AddFields.finFunnelManager.get(AbstractDungeon.player).selectedFinFunnel;
    }

    public static AbstractFinFunnel getFinFunnelMini() {
        if (getFinFunnelList().isEmpty()) {
            System.out.println("浮游炮列表为空");
            return null;
        }


        ArrayList<AbstractFinFunnel> tmp = new ArrayList<>();
        for (AbstractFinFunnel finFunnel : getFinFunnelList()) {
            if (!finFunnel.id.equals(MatrixFinFunnel.ID))
                tmp.add(finFunnel);
        }

        if(tmp.isEmpty()){
            for (AbstractFinFunnel finFunnel : getFinFunnelList()) {
                if (finFunnel.id.equals(MatrixFinFunnel.ID))
                    tmp.add(finFunnel);
            }
        }


        if (!tmp.isEmpty())
            return tmp.get(AbstractDungeon.miscRng.random(tmp.size() - 1));
        else {
            System.out.println("浮游炮列表为空");
            return null;
        }
    }

    public void initializeFinFunnelShion() {
        if (finFunnelList.isEmpty() && SkinManager.getSkinCharacter(0).reskinCount != 3) {
            finFunnelList.add(new MatrixFinFunnel(SaveHelper.matrixFinFunnelLevel));
//            finFunnelList.add(new InvestigationFinFunnel(SaveHelper.investigationFinFunnelLevel));
            finFunnelList.add(new PursuitFinFunnel(SaveHelper.pursuitFinFunnelLevel));
            finFunnelList.add(new GravityFinFunnel(SaveHelper.gravityFinFunnelLevel));
            finFunnelList.add(new DissectingFinFunnel(SaveHelper.dissectingFinFunnelLevel));

        }
    }

    public void update(Skeleton sk) {
        if (!finFunnelList.isEmpty())
            for (AbstractFinFunnel funnel : finFunnelList) {
                if (sk != null)
                    funnel.updatePosition(sk);
                funnel.update();
            }

        if (selectedFinFunnel != null) {
            this.cX = MathHelper.uiLerpSnap(this.cX, selectedFinFunnel.cX);
            this.cY = MathHelper.uiLerpSnap(this.cY, selectedFinFunnel.cY);
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        if (AbstractDungeon.rs != AbstractDungeon.RenderScene.EVENT && !AbstractDungeon.isScreenUp) {
            if (!finFunnelList.isEmpty()) {
                for (AbstractFinFunnel funnel : finFunnelList) {
                    funnel.render(sb);
                }
                
                sb.draw(this.box, this.cX - 48.0F - 24.0f * Settings.scale, this.cY - 48.0F + 48.0f * Settings.scale,
                        48.0f, 48.0f,
                        48.0f, 48.0f,
                        2.0f * Settings.scale, 2.0f * Settings.scale,
                        0.0f, 0, 0,
                        96, 96,
                        false, false
                );
            }
        }
    }

}
