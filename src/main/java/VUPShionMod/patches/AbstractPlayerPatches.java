package VUPShionMod.patches;

import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.finfunnels.FinFunnelManager;
import VUPShionMod.minions.AbstractPlayerMinion;
import VUPShionMod.minions.MinionGroup;
import VUPShionMod.monsters.AbstractVUPShionBoss;
import VUPShionMod.powers.AbstractShionPower;
import VUPShionMod.helpers.ChargeHelper;
import VUPShionMod.powers.EisluRen.CoverMinionPower;
import VUPShionMod.powers.Liyezhu.DecreePower;
import VUPShionMod.powers.Monster.BossShion.SavePowerPower;
import VUPShionMod.ui.SansMeter;
import VUPShionMod.vfx.Common.AbstractSpineEffect;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.Skeleton;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.lang.reflect.Field;

@SuppressWarnings("unused")
public class AbstractPlayerPatches {
    @SpirePatch(
            clz = AbstractPlayer.class,
            method = SpirePatch.CLASS
    )
    public static class AddFields {
        public static SpireField<ChargeHelper> chargeHelper = new SpireField<>(() -> new ChargeHelper());
        public static SpireField<FinFunnelManager> finFunnelManager = new SpireField<>(() -> new FinFunnelManager());
        public static SpireField<MinionGroup> playerMinions = new SpireField<>(() -> new MinionGroup());

    }


    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "applyStartOfTurnRelics"
    )
    public static class PatchApplyStartOfTurnRelics {
        public static void Postfix(AbstractPlayer player) {
            AddFields.finFunnelManager.get(player).atStartOfTurn();
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "update"
    )
    public static class PatchUpdate {
        public static void Postfix(AbstractPlayer player) {
            try {
                Field stateDataField = AbstractCreature.class.getDeclaredField("skeleton");
                stateDataField.setAccessible(true);
                Skeleton sk = ((Skeleton) stateDataField.get(player));
                if (sk != null) {
                    AddFields.finFunnelManager.get(player).update(sk);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (AddFields.chargeHelper.get(player).active) {
                AddFields.chargeHelper.get(player).update();
            }
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "render"
    )
    public static class PatchRender {
        public static void Postfix(AbstractPlayer player, SpriteBatch sb) {
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                AddFields.finFunnelManager.get(player).render(sb);

                if (AddFields.chargeHelper.get(player).active) {
                    AddFields.chargeHelper.get(player).render(sb);
                }
            }
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "preBattlePrep"
    )
    public static class PatchPreBattlePrep {
        public static void Postfix(AbstractPlayer player) {
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                AddFields.finFunnelManager.get(player).preBattlePrep();
            }
        }
    }


    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "onVictory"
    )
    public static class PatchOnVictory {
        public static void Postfix(AbstractPlayer player) {
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                AddFields.finFunnelManager.get(player).onVictory();
                if (EnergyPanelPatches.PatchEnergyPanelField.canUseSans.get(AbstractDungeon.overlayMenu.energyPanel))
                    SansMeter.getSans().onVictory();
            }

            for (AbstractGameEffect effect : AbstractDungeon.effectList) {
                if (effect instanceof AbstractSpineEffect) {
                    effect.isDone = true;
                }
            }

            for (AbstractGameEffect effect : AbstractDungeon.topLevelEffects) {
                if (effect instanceof AbstractSpineEffect) {
                    effect.isDone = true;
                }
            }


        }

    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "draw",
            paramtypez = {
                    int.class
            }
    )
    public static class PatchDraw {
        @SpireInsertPatch(rloc = 13, localvars = {"c"})
        public static void Insert(AbstractPlayer p, int numCards, @ByRef(type = "cards.AbstractCard") Object[] _c) {
            AbstractCard c = (AbstractCard) _c[0];
            if (c.hasTag(CardTagsEnum.LOADED)) {
                AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                    @Override
                    public void update() {
                        c.isInAutoplay = true;
                        AbstractMonster m = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.miscRng);
                        c.applyPowers();
                        p.useCard(c, m, 0);

                        AbstractDungeon.actionManager.cardsPlayedThisTurn.add(c);

                        GameStatsPatch.loadingCardTriggerCombat++;

                        for (AbstractPower power : AbstractDungeon.player.powers)
                            if (power instanceof AbstractShionPower)
                                ((AbstractShionPower) power).onTriggerLoaded();

                        for (AbstractCard card : p.hand.group) {
                            if (card instanceof AbstractVUPShionCard)
                                ((AbstractVUPShionCard) card).onTriggerLoaded();
                        }

                        for (AbstractCard card : p.discardPile.group) {
                            if (card instanceof AbstractVUPShionCard)
                                ((AbstractVUPShionCard) card).onTriggerLoaded();
                        }

                        for (AbstractCard card : p.drawPile.group) {
                            if (card instanceof AbstractVUPShionCard)
                                ((AbstractVUPShionCard) card).onTriggerLoaded();
                        }
                        isDone = true;
                    }
                });

            }
        }
    }


    @SpirePatch(
            clz = AbstractMonster.class,
            method = "damage"
    )
    public static class OnUnblockDamagePatch {
        @SpireInsertPatch(rloc = 61, localvars = {"damageAmount"})
        public static SpireReturn<Void> Insert(AbstractMonster _instance, DamageInfo info, int damageAmount) {
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p instanceof AbstractShionPower) {
                    ((AbstractShionPower) p).monsterAfterOnAttack(info, _instance, damageAmount);
                }
            }

            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = AbstractMonster.class,
            method = "damage"
    )
    public static class MonsterEnemyAttackedBeforeBlockPatch {
        @SpireInsertPatch(rloc = 17, localvars = {"damageAmount"})
        public static SpireReturn<Void> Insert(AbstractMonster _instance, DamageInfo info, @ByRef int[] damageAmount) {
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p instanceof AbstractShionPower) {
                    damageAmount[0] = ((AbstractShionPower) p).onEnemyAttackedPreBlock(info, _instance, damageAmount[0]);
                }
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "damage"
    )
    public static class PlayerAttackedBeforeBlockPatch {
        @SpireInsertPatch(rloc = 6, localvars = {"damageAmount"})
        public static SpireReturn<Void> Insert(AbstractPlayer _instance, DamageInfo info, @ByRef int[] damageAmount) {
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p instanceof AbstractShionPower) {
                    damageAmount[0] = ((AbstractShionPower) p).onAttackedPreBlock(info, _instance, damageAmount[0]);
                }
            }

            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = AbstractMonster.class,
            method = "damage"
    )
    public static class MonsterOnAttackedBeforeBlockPatch {
        @SpireInsertPatch(rloc = 9, localvars = {"damageAmount"})
        public static SpireReturn<Void> Insert(AbstractMonster _instance, DamageInfo info, @ByRef int[] damageAmount) {
            for (AbstractPower p : _instance.powers) {
                if (p instanceof AbstractShionPower) {
                    damageAmount[0] = ((AbstractShionPower) p).onAttackedPreBlock(info, _instance, damageAmount[0]);
                }
            }

            return SpireReturn.Continue();
        }
    }


    //    爪牙

    @SpirePatch(
            clz = MonsterGroup.class,
            method = "showIntent"
    )
    public static class showIntentPatch {
        @SpirePostfixPatch
        public static void Insert(MonsterGroup _instance) {
            AddFields.playerMinions.get(AbstractDungeon.player).showIntent();
        }
    }

    @SpirePatch(
            clz = MonsterGroup.class,
            method = "init"
    )
    public static class InitPatch {
        @SpirePostfixPatch
        public static void Insert(MonsterGroup _instance) {
            AddFields.playerMinions.get(AbstractDungeon.player).init();
        }
    }

    @SpirePatch(
            clz = MonsterGroup.class,
            method = "usePreBattleAction"
    )
    public static class UsePreBattleActionPatch {
        @SpirePostfixPatch
        public static void Insert(MonsterGroup _instance) {
            AddFields.playerMinions.get(AbstractDungeon.player).usePreBattleAction();
        }
    }

    @SpirePatch(
            clz = AbstractRoom.class,
            method = "update"
    )
    public static class ApplyPreTurnLogicPatch {
        @SpireInsertPatch(rloc = 64)
        public static void Insert(AbstractRoom _instance) {
            AddFields.playerMinions.get(AbstractDungeon.player).applyPreTurnLogic();
        }
    }


    @SpirePatch(
            clz = MonsterGroup.class,
            method = "update"
    )
    public static class MonsterGroupUpdatePatch {
        @SpirePostfixPatch
        public static void Insert(MonsterGroup _instance) {
            AddFields.playerMinions.get(AbstractDungeon.player).update();
        }
    }

    @SpirePatch(
            clz = MonsterGroup.class,
            method = "updateAnimations"
    )
    public static class UpdateAnimationsPatch {
        @SpirePostfixPatch
        public static void Insert(MonsterGroup _instance) {
            AddFields.playerMinions.get(AbstractDungeon.player).updateAnimations();
        }
    }


    @SpirePatch(
            clz = MonsterGroup.class,
            method = "render"
    )
    public static class MonsterGroupRenderPatch {
        @SpirePostfixPatch
        public static void Insert(MonsterGroup _instance, SpriteBatch sb) {
            AddFields.playerMinions.get(AbstractDungeon.player).render(sb);
        }
    }


    @SpirePatch(
            clz = MonsterGroup.class,
            method = "applyEndOfTurnPowers"
    )
    public static class ApplyEndOfTurnPowersPatch {
        @SpirePostfixPatch
        public static void Postfix(MonsterGroup _instance) {
            AddFields.playerMinions.get(AbstractDungeon.player).applyEndOfTurnPowers();
        }
    }

    @SpirePatch(
            clz = MonsterGroup.class,
            method = "renderReticle"
    )
    public static class RenderReticlePatch {
        @SpirePostfixPatch
        public static void Insert(MonsterGroup _instance, SpriteBatch sb) {
            AddFields.playerMinions.get(AbstractDungeon.player).renderReticle(sb);
        }
    }

    @SpirePatch(
            clz = AbstractRoom.class,
            method = "endTurn"
    )
    public static class EndTurnPatch {
        @SpireInsertPatch(rloc = 1)
        public static SpireReturn<Void> Insert(AbstractRoom _instance) {
            AddFields.playerMinions.get(AbstractDungeon.player).applyEndOfTurnTriggers();
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "onVictory"
    )
    public static class PlayerOnVictoryPatch {
        @SpirePostfixPatch
        public static void Insert(AbstractPlayer _instance) {
            AddFields.playerMinions.get(AbstractDungeon.player).onVictory();
        }
    }


    @SpirePatch(
            clz = ApplyPowerAction.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {AbstractCreature.class, AbstractCreature.class, AbstractPower.class, int.class, boolean.class, AbstractGameAction.AttackEffect.class}

    )
    public static class SavePowerChangeApplyPowerPatch {
        @SpireInsertPatch(rloc = 13, localvars = {"amount", "duration"})
        public static SpireReturn<Void> Insert(ApplyPowerAction _instance,
                                               AbstractCreature target,
                                               AbstractCreature source,
                                               AbstractPower powerToApply,
                                               int stackAmount,
                                               boolean isFast,
                                               AbstractGameAction.AttackEffect effect, @ByRef int[] amount, @ByRef float[] duration) {

            if (_instance.target != null && _instance.target.isPlayer) {
                for (AbstractPower power : AbstractDungeon.player.powers) {
                    if (power instanceof SavePowerPower) {
                        if (!((SavePowerPower) power).endingEffect) {
                            ((SavePowerPower) power).playerPowers.add(powerToApply);
                            _instance.isDone = true;
                            return SpireReturn.Return();
                        }
                    }
                }
            }

            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = DamageAction.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {AbstractCreature.class, DamageInfo.class, AbstractGameAction.AttackEffect.class}
    )
    public static class ChangeDamageActionTargetPatch {
        @SpirePostfixPatch
        public static SpireReturn<Void> Postfix(DamageAction _instance, AbstractCreature target, DamageInfo info, AbstractGameAction.AttackEffect effect) {
            if (target != null && target.isPlayer) {
                if (info.owner != null && info.owner.hasPower(DecreePower.POWER_ID)) {
                    _instance.target = info.owner;
                } else if (!AbstractDungeon.player.hasPower(CoverMinionPower.POWER_ID))
                    if (!MinionGroup.areMinionsBasicallyDead()) {
                        AbstractPlayerMinion minion = MinionGroup.getCurrentMinion();
                        if (minion != null) {
                            _instance.target = minion;
                        }
                    }
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = VampireDamageAction.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {AbstractCreature.class, DamageInfo.class, AbstractGameAction.AttackEffect.class}
    )
    public static class ChangeVampireDamageActionTargetPatch {
        @SpirePostfixPatch
        public static SpireReturn<Void> Postfix(VampireDamageAction _instance, AbstractCreature target, DamageInfo info, AbstractGameAction.AttackEffect effect) {
            if (target != null && target.isPlayer) {
                if (info.owner != null && info.owner.hasPower(DecreePower.POWER_ID)) {
                    _instance.target = info.owner;
                } else if (!AbstractDungeon.player.hasPower(CoverMinionPower.POWER_ID))
                    if (!MinionGroup.areMinionsBasicallyDead()) {
                        AbstractPlayerMinion minion = MinionGroup.getCurrentMinion();
                        if (minion != null)
                            _instance.target = minion;
                    }
            }

            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "damage"
    )
    public static class ChangePlayerDamageTargetPatch {
        @SpirePostfixPatch
        public static SpireReturn<Void> Postfix(AbstractPlayer _instance, DamageInfo info) {
            if (info.owner != null && info.owner.hasPower(DecreePower.POWER_ID)) {
                info.owner.damage(info);
            } else if (!AbstractDungeon.player.hasPower(CoverMinionPower.POWER_ID))
                if (!MinionGroup.areMinionsBasicallyDead()) {
                    AbstractPlayerMinion minion = MinionGroup.getCurrentMinion();
                    if (minion != null)
                        minion.damage(info);
                }

            return SpireReturn.Return();
        }
    }

    @SpirePatch(
            clz = AbstractMonster.class,
            method = "calculateDamage"
    )
    public static class ChangeMonsterCalculateTargetPatch {
        @SpireInsertPatch(rloc = 20, localvars = {"tmp"})
        public static SpireReturn<Void> Insert(AbstractMonster _instance, int damage, @ByRef float[] tmp) {
            if (!_instance.hasPower(DecreePower.POWER_ID) && !AbstractDungeon.player.hasPower(CoverMinionPower.POWER_ID))
                if (!MinionGroup.areMinionsBasicallyDead()) {
                    AbstractPlayerMinion minion = MinionGroup.getCurrentMinion();
                    if (minion != null)
                        for (AbstractPower p : minion.powers) {
                            tmp[0] = p.atDamageReceive(tmp[0], DamageInfo.DamageType.NORMAL);
                        }
                }
            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = AbstractMonster.class,
            method = "calculateDamage"
    )
    public static class ChangeMonsterCalculateTargetPatch2 {
        @SpireInsertPatch(rloc = 38, localvars = {"tmp"})
        public static SpireReturn<Void> Insert(AbstractMonster _instance, int damage, @ByRef float[] tmp) {
            if (!_instance.hasPower(DecreePower.POWER_ID) && !AbstractDungeon.player.hasPower(CoverMinionPower.POWER_ID))
                if (!MinionGroup.areMinionsBasicallyDead()) {
                    AbstractPlayerMinion minion = MinionGroup.getCurrentMinion();
                    if (minion != null)
                        for (AbstractPower p : minion.powers) {
                            tmp[0] = p.atDamageFinalReceive(tmp[0], DamageInfo.DamageType.NORMAL);
                        }
                }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = AbstractMonster.class,
            method = "calculateDamage"
    )
    public static class ChangeMonsterCalculateTargetPatch3 {
        @SpirePostfixPatch()
        public static SpireReturn<Void> Insert(AbstractMonster _instance, int damage) {
            if (_instance.hasPower(DecreePower.POWER_ID)) {
                AbstractCreature target = _instance;
                float tmp = damage;


                for (AbstractPower p : _instance.powers) {
                    tmp = p.atDamageGive(tmp, DamageInfo.DamageType.NORMAL);
                }


                for (AbstractPower p : target.powers) {
                    tmp = p.atDamageReceive(tmp, DamageInfo.DamageType.NORMAL);
                }

                if (target instanceof AbstractVUPShionBoss)
                    tmp = ((AbstractVUPShionBoss) target).stance.atDamageReceive(tmp, DamageInfo.DamageType.NORMAL);


                for (AbstractPower p : _instance.powers) {
                    tmp = p.atDamageFinalGive(tmp, DamageInfo.DamageType.NORMAL);
                }


                for (AbstractPower p : target.powers) {
                    tmp = p.atDamageFinalReceive(tmp, DamageInfo.DamageType.NORMAL);
                }


                damage = MathUtils.floor(tmp);
                if (damage < 0) {
                    damage = 0;
                }

                ReflectionHacks.setPrivate(_instance, AbstractMonster.class, "intentDmg", damage);
            }


            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = DamageInfo.class,
            method = "applyPowers"
    )
    public static class ChangeMonsterCalculateTargetPatch4 {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn<Void> Insert(DamageInfo info, AbstractCreature owner, @ByRef AbstractCreature[] _target) {
            if (info.owner != null && info.owner.hasPower(DecreePower.POWER_ID)) {
                _target[0] = info.owner;
            } else if (!AbstractDungeon.player.hasPower(CoverMinionPower.POWER_ID))
                if (!MinionGroup.areMinionsBasicallyDead() && !(owner.isPlayer || owner instanceof AbstractPlayerMinion)) {
                    AbstractPlayerMinion minion = MinionGroup.getCurrentMinion();
                    if (minion != null)
                        _target[0] = minion;
                }

            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "onModifyPower"
    )
    public static class OnApplyPowerPatch {
        @SpirePostfixPatch
        public static void Postfix() {
            if (!MinionGroup.areMinionsBasicallyDead())
                for (AbstractPlayerMinion m : MinionGroup.getMinions()) {
                    m.applyPowers();
                }
        }
    }

    @SpirePatch(
            clz = AbstractMonster.class,
            method = "die",
            paramtypez = {boolean.class}
    )
    public static class OnMonsterDeathPatch {
        @SpireInsertPatch(rloc = 16)
        public static SpireReturn<Void> Insert(AbstractMonster _instance, boolean triggerRelic) {
            for (AbstractPlayerMinion minion : MinionGroup.getMinions()) {
                if (minion.getTargetMonster() != null && minion.getTargetMonster().isDeadOrEscaped()) {
                    minion.onMonsterDeath();
                }
            }

            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = SpawnMonsterAction.class,
            method = "update"
    )
    public static class SpawnMonsterActionPatch {
        @SpirePostfixPatch
        public static SpireReturn<Void> Postfix(SpawnMonsterAction _instance) {
            if (_instance.isDone)
                for (AbstractPlayerMinion minion : MinionGroup.getMinions()) {
                    if (minion.getTargetMonster() != null && minion.getTargetMonster().isDeadOrEscaped()) {
                        minion.onSpawnMonster();
                    }
                }

            return SpireReturn.Continue();
        }
    }
}


