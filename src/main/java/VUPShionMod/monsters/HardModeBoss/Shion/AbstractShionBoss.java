package VUPShionMod.monsters.HardModeBoss.Shion;

import VUPShionMod.actions.Unique.TurnTriggerAllBossFinFunnelAction;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.MatrixFinFunnel;
import VUPShionMod.monsters.AbstractVUPShionBoss;
import VUPShionMod.monsters.HardModeBoss.Shion.bossfinfunnels.AbstractBossFinFunnel;
import VUPShionMod.powers.Monster.BossShion.BossAvatarPower;
import VUPShionMod.powers.Shion.DelayAvatarPower;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public abstract class AbstractShionBoss extends AbstractVUPShionBoss {
    public ArrayList<AbstractBossFinFunnel> bossFinFunnels = new ArrayList<>();
    private Texture avatar = ImageMaster.loadImage("VUPShionMod/characters/Shion/Avatar.png");


    public AbstractShionBoss(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, offsetX, offsetY);
    }

    @Override
    public void update() {
        super.update();

        if (!bossFinFunnels.isEmpty()) {
            for (AbstractBossFinFunnel finFunnel : this.bossFinFunnels) {
                finFunnel.updatePosition(this.skeleton);
                finFunnel.update();
            }
        }
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();


        if (!bossFinFunnels.isEmpty()) {
            for (AbstractBossFinFunnel finFunnel : this.bossFinFunnels) {
                finFunnel.preBattlePrep();
            }
        }
    }


    @Override
    public void applyStartOfTurnPowers() {
        addToBot(new TurnTriggerAllBossFinFunnelAction(this));
        super.applyStartOfTurnPowers();
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);

        if (!this.isDeadOrEscaped() && !bossFinFunnels.isEmpty()) {
            for (AbstractBossFinFunnel finFunnel : this.bossFinFunnels) {
                finFunnel.render(sb);
            }
        }

        boolean hasPower = false;
        for (AbstractPower power : this.powers) {
            if (power instanceof BossAvatarPower)
                hasPower = true;
        }

        if (hasPower && !this.isDeadOrEscaped()) {
            sb.setColor(Color.WHITE);
            sb.setBlendFunction(770, 771);
            sb.draw(this.avatar, this.hb.x - this.avatar.getWidth() * 0.5f - 40.0F * Settings.scale,
                    this.hb.y - this.avatar.getHeight() * 0.5f + 120.0F * Settings.scale,
                    this.avatar.getWidth() * 0.5f, this.avatar.getHeight() * 0.5f, this.avatar.getWidth(), this.avatar.getHeight(),
                    0.6f * Settings.scale, 0.6f * Settings.scale, 0.0F, 0, 0, this.avatar.getWidth(), this.avatar.getHeight(), this.flipHorizontal, false);

        }
    }


    public static AbstractBossFinFunnel getFinFunnelMini(AbstractShionBoss boss) {
        if (boss.bossFinFunnels.isEmpty()) {
            System.out.println("浮游炮列表为空");
            return null;
        }


        ArrayList<AbstractBossFinFunnel> tmp = new ArrayList<>();

        for (AbstractBossFinFunnel finFunnel : boss.bossFinFunnels) {
            if (!finFunnel.id.equals(MatrixFinFunnel.ID))
                tmp.add(finFunnel);
        }


        if (!tmp.isEmpty())
            return tmp.get(AbstractDungeon.miscRng.random(tmp.size() - 1));
        else {
            System.out.println("浮游炮列表为空");
            return null;
        }
    }
}
