package VUPShionMod.vfx;

import VUPShionMod.actions.GainShieldAction;
import VUPShionMod.character.Shion;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.GravityFinFunnel;
import VUPShionMod.finfunnels.InvestigationFinFunnel;
import VUPShionMod.finfunnels.PursuitFinFunnel;
import VUPShionMod.powers.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;

public class AllFinFunnelSmallLaserEffect extends AbstractGameEffect {
    private ArrayList<AbstractFinFunnel> finFunnels;
    private ArrayList<FinFunnelSmallLaserData> dataList = new ArrayList<>();
    private static TextureAtlas.AtlasRegion img;
    private boolean posUpdated = false;


    private boolean isDoubleDamage = false;
    private boolean isMultiDamage = false;
    private boolean isApplyBleeding = false;
    private boolean isGainBlock = false;


    private static TextureAtlas.AtlasRegion atkImg;
    private Color atkColor = Color.WHITE.cpy();
    private static int blockSound = 0;

    private AbstractPlayer p = AbstractDungeon.player;

    public static class FinFunnelSmallLaserData {
        public float sX = 0.0f;
        public float sY = 0.0f;
        public float dX = 0.0f;
        public float dY = 0.0f;
        public float dst = 0.0f;
        public float rotation = 0.0f;
    }

    private void getPower() {
        if (p.hasPower(AttackOrderAlphaPower.POWER_ID))
            isDoubleDamage = true;

        if (p.hasPower(AttackOrderBetaPower.POWER_ID))
            isMultiDamage = true;

        if (p.hasPower(AttackOrderGammaPower.POWER_ID))
            isApplyBleeding = true;

        if (p.hasPower(AttackOrderDeltaPower.POWER_ID))
            isGainBlock = true;
    }

    public AllFinFunnelSmallLaserEffect(ArrayList<AbstractFinFunnel> finFunnels) {
        super();
        this.finFunnels = finFunnels;
        if (img == null) {
            img = ImageMaster.vfxAtlas.findRegion("combat/laserThin");
        }

        if (atkImg == null) {
            atkImg = ImageMaster.ATK_FIRE;
        }

        this.color = Color.CYAN.cpy();
        this.duration = 0.8F;
        this.startingDuration = 0.5F;

        this.scale = Settings.scale;

        getPower();
    }

    @Override
    public void update() {
        if (duration == 0.8F)
            for (AbstractFinFunnel f : finFunnels)
                ((Shion) p).playFinFunnelAnimation(f.id);


        if (this.duration < this.startingDuration) {
            if (!posUpdated) {
                posUpdated = true;

                for (AbstractFinFunnel finFunnel : this.finFunnels) {
                    AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.miscRng);
                    if (m == null)
                        return;

                    FinFunnelSmallLaserData data = new FinFunnelSmallLaserData();
                    data.dX = finFunnel.muzzle_X;
                    data.dY = finFunnel.muzzle_Y;
                    data.sX = m.hb.cX;
                    data.sY = m.hb.cY;
                    data.dst = Vector2.dst(data.sX, data.sY, data.dX, data.dY) / Settings.scale;
                    data.rotation = MathUtils.atan2(data.dX - data.sX, data.dY - data.sY);
                    data.rotation *= 57.295776F;
                    data.rotation = -data.rotation + 90.0F;
                    dataList.add(data);


                    if (isDoubleDamage)
                        m.damage(new DamageInfo(p, finFunnel.getFinalDamage() * 3, DamageInfo.DamageType.THORNS));
                    else
                        m.damage(new DamageInfo(p, finFunnel.getFinalDamage(), DamageInfo.DamageType.THORNS));

                    if (m.lastDamageTaken > 0 && isGainBlock) {
                        addToBot(new GainBlockAction(p, (int) Math.floor(m.lastDamageTaken)));
                    }

                    if (finFunnel instanceof PursuitFinFunnel && !m.isDeadOrEscaped()) {
                        addToBot(new ApplyPowerAction(m, p, new PursuitPower(m, finFunnel.getFinalEffect())));
                    }

                    if (finFunnel instanceof GravityFinFunnel) {
                        if (p.hasPower(GravitoniumPower.POWER_ID))
                            addToBot(new GainShieldAction(p, finFunnel.getFinalEffect(), true));
                        else
                            addToBot(new GainBlockAction(p, finFunnel.getFinalEffect(), true));
                    }

                    if (finFunnel instanceof InvestigationFinFunnel && !m.isDeadOrEscaped()) {
                        if (isApplyBleeding)
                            addToBot(new ApplyPowerAction(m, p, new BleedingPower(m, p, 2)));
                        addToBot(new ApplyPowerAction(m, p, new BleedingPower(m, p, finFunnel.getFinalEffect())));
                    }

                    CardCrawlGame.sound.play("ATTACK_FIRE");
                    CardCrawlGame.sound.play("ATTACK_MAGIC_BEAM_SHORT", 0.5f);
                }
            }


            if (this.duration > this.startingDuration / 2.0F) {
                this.color.a = Interpolation.pow2In.apply(1.0F, 0.0F, (this.duration - 0.25F) * 4.0F);
            } else {
                this.color.a = Interpolation.bounceIn.apply(0.0F, 1.0F, this.duration * 4.0F);
            }
        }


        this.duration -= Gdx.graphics.getDeltaTime();


        if (this.duration < this.startingDuration / 2.0F) {
            this.atkColor.a = this.duration / (this.startingDuration / 2.0F);
        }


        if (this.duration < 0.0F) {
            this.isDone = true;
            this.atkColor.a = 0.0F;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (this.duration < this.startingDuration) {
            for (FinFunnelSmallLaserData data : dataList) {

                sb.setBlendFunction(770, 1);
                sb.setColor(this.color);
                sb.draw(img, data.sX, data.sY - img.packedHeight / 2.0F + 10.0F * Settings.scale,
                        0.0F, img.packedHeight / 2.0F, data.dst, 50.0F, this.scale + MathUtils.random(-0.01F, 0.01F), this.scale, data.rotation);
                sb.setColor(new Color(0.3F, 0.3F, 1.0F, this.color.a));
                sb.draw(img, data.sX, data.sY - img.packedHeight / 2.0F,
                        0.0F, img.packedHeight / 2.0F, data.dst, MathUtils.random(50.0F, 90.0F), this.scale + MathUtils.random(-0.02F, 0.02F), this.scale, data.rotation);
                sb.setBlendFunction(770, 771);


                if (this.atkImg != null) {
                    sb.setColor(this.atkColor);
                    sb.draw(atkImg, data.sX - atkImg.packedWidth / 2.0f, data.sY - atkImg.packedHeight / 2.0f,
                            atkImg.packedWidth / 2.0F, atkImg.packedHeight / 2.0F,
                            atkImg.packedWidth, atkImg.packedHeight,
                            this.scale, this.scale, this.rotation);
                }

            }
        }
    }

    @Override
    public void dispose() {

    }

    private void addToBot(AbstractGameAction a) {
        AbstractDungeon.actionManager.addToBottom(a);
    }

    private void addToTop(AbstractGameAction a) {
        AbstractDungeon.actionManager.addToTop(a);
    }
}
