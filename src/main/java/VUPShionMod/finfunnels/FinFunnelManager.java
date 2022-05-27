package VUPShionMod.finfunnels;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.TurnTriggerAllFinFunnelAction;
import VUPShionMod.cards.ShionCard.anastasia.AttackOrderGamma;
import VUPShionMod.patches.AbstractPlayerEnum;
import VUPShionMod.patches.EnergyPanelPatches;
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

    public void initializeFinFunnel() {

    }

    public void atStartOfTurn(){
        AbstractDungeon.actionManager.addToBottom(new TurnTriggerAllFinFunnelAction(true));
        if (AbstractDungeon.player.hasPower(AttackOrderGamma.ID))
            AbstractDungeon.actionManager.addToBottom(new TurnTriggerAllFinFunnelAction(true));

        EnergyPanelPatches.energyUsedThisTurn = 1;

        if(!finFunnelList.isEmpty())
            for(AbstractFinFunnel finFunnel: finFunnelList){
                finFunnel.atTurnStart();
            }
    }

    public void preBattlePrep() {
        VUPShionMod.loadFinFunnels();
        selectedFinFunnel = getFinFunnel(VUPShionMod.activeFinFunnel);
        if (selectedFinFunnel != null) {
            this.cX = selectedFinFunnel.cX;
            this.cY = selectedFinFunnel.cY;
        }

        if(!finFunnelList.isEmpty())
            for(AbstractFinFunnel finFunnel: finFunnelList){
                finFunnel.preBattlePrep();
            }
    }

    public void onVictory() {
        VUPShionMod.saveFinFunnels();
    }

    public AbstractFinFunnel getFinFunnel(String id) {
        if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion)
            initializeFinFunnelShion();

        if (!finFunnelList.isEmpty())
            for (AbstractFinFunnel finFunnel : finFunnelList) {
                System.out.println("查询浮游炮---" + finFunnel.id);
                System.out.println("目标浮游炮---" + id);

                if (finFunnel.id.equals(id))
                    return finFunnel;
            }

        return null;
    }

    public void initializeFinFunnelShion() {
        if (finFunnelList.isEmpty()) {
            finFunnelList.add(new InvestigationFinFunnel(VUPShionMod.investigationFinFunnelLevel));
            finFunnelList.add(new PursuitFinFunnel(VUPShionMod.pursuitFinFunnelLevel));
            finFunnelList.add(new GravityFinFunnel(VUPShionMod.gravityFinFunnelLevel));
            finFunnelList.add(new DissectingFinFunnel(VUPShionMod.dissectingFinFunnelLevel));
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
            if (!finFunnelList.isEmpty())
                for (AbstractFinFunnel funnel : finFunnelList) {
                    funnel.render(sb);
                }

            sb.draw(this.box, this.cX - 48.0F + 24.0f * Settings.scale, this.cY - 48.0F + 48.0f * Settings.scale,
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