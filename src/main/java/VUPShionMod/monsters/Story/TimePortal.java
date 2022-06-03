package VUPShionMod.monsters.Story;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Unique.SummonMinionAction;
import VUPShionMod.character.WangChuan;
import VUPShionMod.effects.ShionBossBackgroundEffect;
import VUPShionMod.powers.Monster.PlagaAMundo.ChaoticPower;
import VUPShionMod.powers.Monster.PlagaAMundo.SiegePower;
import VUPShionMod.powers.Monster.TimePortal.ContortTimePower;
import VUPShionMod.powers.Monster.TimePortal.HulkingPower;
import VUPShionMod.powers.Monster.TimePortal.LoomingPower;
import VUPShionMod.powers.Monster.TimePortal.MalignBenevolencePower;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.actions.ClearCardQueueAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.CannotLoseAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class TimePortal extends CustomMonster {
    public static final String ID = VUPShionMod.makeID("TimePortal");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    private int moveCount = 1;
    private boolean summoned = false;
    private boolean npc = false;

    private boolean playDeath = false;
    private float dieAnimationTimer = 2.0f;


    public TimePortal() {
        super(NAME, ID, 88, 90.0F, 210.0F, 420.0F, 480.0F, null, 60.0F, -180.0f);

        if (AbstractDungeon.ascensionLevel >= 7) {
            setHp(8000);
        } else {
            setHp(8000);
        }


        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 3));
        } else {
            this.damage.add(new DamageInfo(this, 3));
        }


        this.type = EnemyType.BOSS;
        this.dialogX = -50.0F * Settings.scale;
        this.dialogY = 50.0F * Settings.scale;


        loadAnimation("VUPShionMod/img/monsters/TimePortal/Stance_Wormhole_only.atlas",
                "VUPShionMod/img/monsters/TimePortal/Stance_Wormhole_only.json", 1.0f);


        AnimationState.TrackEntry e = this.state.setAnimation(0, "Wormhole_open", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Wormhole_idle", true, 0.0f);
        this.flipHorizontal = false;
    }


    public void usePreBattleAction() {
        addToBot(new CannotLoseAction());
        addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, this, SiegePower.POWER_ID));
        addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, this, ChaoticPower.POWER_ID));

        addToBot(new ApplyPowerAction(this, this, new ContortTimePower(this, 20)));
        addToBot(new ApplyPowerAction(this, this, new MalignBenevolencePower(this, 1)));
        addToBot(new ApplyPowerAction(this, this, new LoomingPower(this, 6)));
        addToBot(new ApplyPowerAction(this, this, new HulkingPower(this, 2000)));

        ShionBossBackgroundEffect.instance.loadAnimation(
                "VUPShionMod/img/monsters/TimePortal/Timebug_battle_background.atlas",
                "VUPShionMod/img/monsters/TimePortal/Timebug_battle_background.json", 1.0f);
        ShionBossBackgroundEffect.instance.setAnimation(0, "idle_background", true);
        ShionBossBackgroundEffect.instance.setAnimation(1, "idle_flower", true);
        ShionBossBackgroundEffect.instance.setAnimation(2, "idle_flower2", true);
        ShionBossBackgroundEffect.instance.setAnimation(3, "idle_rock", true);
    }


    public void takeTurn() {
        if (!this.npc && !summoned) {
            switch (this.nextMove) {
                case 0:
                    addToBot(new ApplyPowerAction(this, this, new ArtifactPower(this, 5)));
                    break;
                case 1:

                    addToBot(new SummonMinionAction(this, new Ouroboros(false), 5));
                    addToBot(new ChangeStateAction(this, "NPC"));
                    this.summoned = true;
                    break;
                case 2:
                    addToBot(new SummonMinionAction(this, new Ouroboros(true), 5));
                    addToBot(new ChangeStateAction(this, "NPC"));
                    this.summoned = true;
                    break;
            }

            addToBot(new RollMoveAction(this));
        }
    }


    protected void getMove(int num) {
        if (this.halfDead) {
            setMove((byte) 3, Intent.NONE);
            return;
        }


        if (this.moveCount >= 6) {
            setMove((byte) 1, Intent.UNKNOWN);
        } else {
            setMove((byte) 0, Intent.BUFF);
        }
        this.moveCount++;
    }


    @Override
    public void die() {
        if (!(AbstractDungeon.getCurrRoom()).cannotLose)
            super.die();
    }

    public void damage(DamageInfo info) {
        super.damage(info);

        if (this.currentHealth <= 0 && !this.halfDead) {
            this.halfDead = true;

            for (AbstractPower p : this.powers) {
                p.onDeath();
            }

            for (AbstractRelic r : AbstractDungeon.player.relics) {
                r.onMonsterDeath(this);
            }

            addToTop(new ClearCardQueueAction());

            this.powers.clear();

            hideHealthBar();

            setMove((byte) 2, Intent.UNKNOWN);
            createIntent();
            addToBot(new SetMoveAction(this, (byte) 2, Intent.UNKNOWN));
            applyPowers();

        }
    }

    public void changeState(String stateName) {
        switch (stateName) {
            case "DieAnimation":
                this.playDeath = true;

                break;
            case "NPC":
                this.currentHealth = 0;
                this.halfDead = true;
                this.npc = true;
                this.summoned = true;
                hideHealthBar();
                this.powers.clear();
                break;
        }
    }

    @Override
    public void renderTip(SpriteBatch sb) {
        this.tips.clear();

        if (this.intentAlphaTarget == 1.0F && !AbstractDungeon.player.hasRelic("Runic Dome") && this.intent != Intent.NONE) {
            this.tips.add(ReflectionHacks.getPrivate(this, AbstractMonster.class, "intentTip"));
        }


        ArrayList<AbstractPower> tipPowers = new ArrayList<>();

        for (AbstractPower p : this.powers) {
            if (p instanceof ContortTimePower || p instanceof MalignBenevolencePower || p instanceof LoomingPower || p instanceof HulkingPower) {
                if (p.region48 != null) {
                    this.tips.add(new PowerTip(p.name, p.description, p.region48));
                    continue;
                }
                this.tips.add(new PowerTip(p.name, p.description, p.img));
            } else {
                tipPowers.add(p);
            }

        }


        for (AbstractPower p : tipPowers) {
            if (p.region48 != null) {
                this.tips.add(new PowerTip(p.name, p.description, p.region48));
                continue;
            }
            this.tips.add(new PowerTip(p.name, p.description, p.img));
        }


        if (!this.tips.isEmpty()) {
            if (this.hb.cX + this.hb.width / 2.0F < TIP_X_THRESHOLD) {
                TipHelper.queuePowerTips(this.hb.cX + this.hb.width / 2.0F + TIP_OFFSET_R_X, this.hb.cY +

                        TipHelper.calculateAdditionalOffset(this.tips, this.hb.cY), this.tips);
            } else {

                TipHelper.queuePowerTips(this.hb.cX - this.hb.width / 2.0F + TIP_OFFSET_L_X, this.hb.cY +

                        TipHelper.calculateAdditionalOffset(this.tips, this.hb.cY), this.tips);
            }
        }


    }

    @SpireOverride
    protected void renderIntentVfxBehind(SpriteBatch sb) {
        if (!this.npc)
            SpireSuper.call(sb);
    }

    @SpireOverride
    protected void renderIntent(SpriteBatch sb) {
        if (!this.npc)
            SpireSuper.call(sb);
    }

    @SpireOverride
    protected void renderIntentVfxAfter(SpriteBatch sb) {
        if (!this.npc)
            SpireSuper.call(sb);
    }

    @Override
    public void update() {
        super.update();

        if (this.playDeath) {
            this.dieAnimationTimer -= Gdx.graphics.getDeltaTime();

            if (this.dieAnimationTimer <= 0) {
                this.playDeath = false;
                this.state.setAnimation(0, "Wormhole_close", false);
            }


        }

    }
}
