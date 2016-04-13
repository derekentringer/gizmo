package com.derekentringer.gizmo.manager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.derekentringer.gizmo.component.actor.player.PlayerActor;
import com.derekentringer.gizmo.component.stage.GameStage;
import com.derekentringer.gizmo.component.stage.interfaces.IGameStage;
import com.derekentringer.gizmo.manager.interfaces.IAchievement;
import com.derekentringer.gizmo.model.BaseModelType;
import com.derekentringer.gizmo.model.body.DeleteBody;
import com.derekentringer.gizmo.model.enemy.BaseEnemyModel;
import com.derekentringer.gizmo.model.item.BasePlayerItemModel;
import com.derekentringer.gizmo.model.object.DropCrystalBlueModel;
import com.derekentringer.gizmo.model.object.DropHeartModel;
import com.derekentringer.gizmo.model.object.HeartModel;
import com.derekentringer.gizmo.model.object.KeyModel;
import com.derekentringer.gizmo.model.object.LifeModel;
import com.derekentringer.gizmo.model.room.RoomModel;
import com.derekentringer.gizmo.model.structure.destroyable.BaseDestroyableModel;
import com.derekentringer.gizmo.model.structure.door.DoorModel;
import com.derekentringer.gizmo.util.BlockUtils;
import com.derekentringer.gizmo.util.BodyUtils;
import com.derekentringer.gizmo.util.EnemyUtils;
import com.derekentringer.gizmo.util.FixtureUtils;
import com.derekentringer.gizmo.util.ItemUtils;
import com.derekentringer.gizmo.util.log.GLog;
import com.derekentringer.gizmo.util.map.MapParser;

import java.util.ArrayList;

public class ContactManager {

    private static final String TAG = ContactManager.class.getSimpleName();

    private ArrayList<IAchievement> mIAchievementListeners = new ArrayList<IAchievement>();

    public void addListener(IAchievement listener) {
        mIAchievementListeners.add(listener);
    }

    public ContactManager() {
    }

    public void setPlayerOnGround(PlayerActor playerActor, Fixture fixtureA, Fixture fixtureB) {
        // detect when player is on the ground
        if (FixtureUtils.fixtureIsPlayerHitAreaBottom(fixtureA) && FixtureUtils.fixtureIsGround(fixtureB)) {
            playerActor.setIsOnGround(true);
        }
        else if (FixtureUtils.fixtureIsPlayerHitAreaBottom(fixtureB) && FixtureUtils.fixtureIsGround(fixtureA)) {
            playerActor.setIsOnGround(true);
        }
    }

    public void setPlayerAtDoor(PlayerActor playerActor, Body bodyA, Body bodyB) {
        // set player at door types
        if (BodyUtils.bodyTypeCheck(bodyA, BaseModelType.PLAYER) && BodyUtils.bodyTypeCheck(bodyB, BaseModelType.DOOR)) {
            playerActor.setIsAtDoor(true);
            playerActor.setIsAtDoorUserData((DoorModel) bodyB.getUserData());
        }
        else if (BodyUtils.bodyTypeCheck(bodyB, BaseModelType.PLAYER) && BodyUtils.bodyTypeCheck(bodyA, BaseModelType.DOOR)) {
            playerActor.setIsAtDoor(true);
            playerActor.setIsAtDoorUserData((DoorModel) bodyA.getUserData());
        }

        if (BodyUtils.bodyTypeCheck(bodyA, BaseModelType.PLAYER) && BodyUtils.bodyTypeCheck(bodyB, BaseModelType.DOOR_OFF)) {
            playerActor.setIsAtDoor(false);
        }
        else if (BodyUtils.bodyTypeCheck(bodyB, BaseModelType.PLAYER) && BodyUtils.bodyTypeCheck(bodyA, BaseModelType.DOOR_OFF)) {
            playerActor.setIsAtDoor(false);
        }
    }

    public void setPlayerTouchingDestroyable(PlayerActor playerActor, Fixture fixtureA, Fixture fixtureB) {
        // player fixture and destroyable detection
        // right fixture sensor detection
        if (FixtureUtils.fixtureIsPlayerHitAreaRight(fixtureA) && FixtureUtils.fixtureIsDestroyable(fixtureB)) {
            playerActor.setTouchingBodyDestroyableRight(fixtureB.getBody());
        }
        else if (FixtureUtils.fixtureIsPlayerHitAreaRight(fixtureB) && FixtureUtils.fixtureIsDestroyable(fixtureA)) {
            playerActor.setTouchingBodyDestroyableRight(fixtureA.getBody());
        }

        // bottom fixture sensor detection
        if (FixtureUtils.fixtureIsPlayerHitAreaBottom(fixtureA) && FixtureUtils.fixtureIsDestroyable(fixtureB)) {
            playerActor.setIsOnGround(true);
            playerActor.setTouchingBodyDestroyableBottom(fixtureB.getBody());
        }
        else if (FixtureUtils.fixtureIsPlayerHitAreaBottom(fixtureB) && FixtureUtils.fixtureIsDestroyable(fixtureA)) {
            playerActor.setIsOnGround(true);
            playerActor.setTouchingBodyDestroyableBottom(fixtureA.getBody());
        }

        // left fixture sensor detection
        if (FixtureUtils.fixtureIsPlayerHitAreaLeft(fixtureA) && FixtureUtils.fixtureIsDestroyable(fixtureB)) {
            playerActor.setTouchingBodyDestroyableLeft(fixtureB.getBody());
        }
        else if (FixtureUtils.fixtureIsPlayerHitAreaLeft(fixtureB) && FixtureUtils.fixtureIsDestroyable(fixtureA)) {
            playerActor.setTouchingBodyDestroyableLeft(fixtureA.getBody());
        }
    }

    public void setPlayerAttacking(MapParser mapParser, GameStage gameStage, World world, RoomModel loadedRoomModel, ArrayList<DeleteBody> deleteBodies, Body bodyA, Body bodyB) {
        //player attack with items collisions
        //primary and secondary bodyA & bodyB checks

        //primary
        if (BodyUtils.bodyTypeCheck(bodyA, BaseModelType.PLAYER_ITEM_PRIMARY) && BodyUtils.bodyTypeCheck(bodyB, BaseModelType.ENEMY)) {
            EnemyUtils.setEnemyHealth(bodyB, ItemUtils.getItemHealthDamage(bodyA));
            if (EnemyUtils.getEnemyHealth(bodyB) <= 0) {
                if (EnemyUtils.isEnemyBoss(bodyB)) {
                    loadedRoomModel.addDestroyedBoss((BaseEnemyModel) bodyB.getUserData());
                    if (EnemyUtils.getEnemyDropsLoot(bodyB)) {
                        mapParser.addToBossDroppedItemPositionArray(bodyB.getPosition());
                    }
                }
                else if (EnemyUtils.getEnemyDropsLoot(bodyB)) {
                    mapParser.addToDroppedItemPositionArray(bodyB.getPosition());
                }
                deleteBodies.add(new DeleteBody((BaseEnemyModel) bodyB.getUserData(), bodyB));
                mapParser.addToDestroyedEnemyPositionArray(bodyB.getPosition());

                for (IAchievement listener : mIAchievementListeners) {
                    listener.achievementPlayerKilledEnemy((BaseEnemyModel) bodyB.getUserData());
                }
            }
        }
        else if (BodyUtils.bodyTypeCheck(bodyB, BaseModelType.PLAYER_ITEM_PRIMARY) && BodyUtils.bodyTypeCheck(bodyA, BaseModelType.ENEMY)) {
            EnemyUtils.setEnemyHealth(bodyA, ItemUtils.getItemHealthDamage(bodyB));
            if (EnemyUtils.getEnemyHealth(bodyA) <= 0) {
                if (EnemyUtils.isEnemyBoss(bodyA)) {
                    loadedRoomModel.addDestroyedBoss((BaseEnemyModel) bodyA.getUserData());
                    if (EnemyUtils.getEnemyDropsLoot(bodyA)) {
                        mapParser.addToBossDroppedItemPositionArray(bodyA.getPosition());
                    }
                }
                else if (EnemyUtils.getEnemyDropsLoot(bodyA)) {
                    mapParser.addToDroppedItemPositionArray(bodyA.getPosition());
                }
                deleteBodies.add(new DeleteBody((BaseEnemyModel) bodyA.getUserData(), bodyA));
                mapParser.addToDestroyedEnemyPositionArray(bodyA.getPosition());

                for (IAchievement listener : mIAchievementListeners) {
                    listener.achievementPlayerKilledEnemy((BaseEnemyModel) bodyA.getUserData());
                }
            }
        }

        //secondary
        if (BodyUtils.bodyTypeCheck(bodyA, BaseModelType.PLAYER_ITEM_SECONDARY) && BodyUtils.bodyTypeCheck(bodyB, BaseModelType.ENEMY)) {
            EnemyUtils.setEnemyHealth(bodyB, ItemUtils.getItemHealthDamage(bodyA));
            if (EnemyUtils.getEnemyHealth(bodyB) <= 0) {
                if (EnemyUtils.isEnemyBoss(bodyB)) {
                    loadedRoomModel.addDestroyedBoss((BaseEnemyModel) bodyB.getUserData());
                    if (EnemyUtils.getEnemyDropsLoot(bodyB)) {
                        mapParser.addToBossDroppedItemPositionArray(bodyB.getPosition());
                    }
                }
                else if (EnemyUtils.getEnemyDropsLoot(bodyB)) {
                    mapParser.addToDroppedItemPositionArray(bodyB.getPosition());
                }
                deleteBodies.add(new DeleteBody((BaseEnemyModel) bodyB.getUserData(), bodyB));
                mapParser.addToDestroyedEnemyPositionArray(bodyB.getPosition());

                for (IAchievement listener : mIAchievementListeners) {
                    listener.achievementPlayerKilledEnemy((BaseEnemyModel) bodyB.getUserData());
                }
            }
        }
        else if (BodyUtils.bodyTypeCheck(bodyB, BaseModelType.PLAYER_ITEM_SECONDARY) && BodyUtils.bodyTypeCheck(bodyA, BaseModelType.ENEMY)) {
            EnemyUtils.setEnemyHealth(bodyA, ItemUtils.getItemHealthDamage(bodyB));
            if (EnemyUtils.getEnemyHealth(bodyA) <= 0) {
                if (EnemyUtils.isEnemyBoss(bodyA)) {
                    loadedRoomModel.addDestroyedBoss((BaseEnemyModel) bodyA.getUserData());
                    if (EnemyUtils.getEnemyDropsLoot(bodyA)) {
                        mapParser.addToBossDroppedItemPositionArray(bodyA.getPosition());
                    }
                }
                else if (EnemyUtils.getEnemyDropsLoot(bodyA)) {
                    mapParser.addToDroppedItemPositionArray(bodyA.getPosition());
                }
                deleteBodies.add(new DeleteBody((BaseEnemyModel) bodyA.getUserData(), bodyA));
                mapParser.addToDestroyedEnemyPositionArray(bodyA.getPosition());

                for (IAchievement listener : mIAchievementListeners) {
                    listener.achievementPlayerKilledEnemy((BaseEnemyModel) bodyA.getUserData());
                }
            }
        }
        //bombs destroy blocks
        else if (BodyUtils.bodyTypeCheck(bodyA, BaseModelType.PLAYER_ITEM_SECONDARY) && BodyUtils.bodyTypeCheck(bodyB, BaseModelType.BLOCK_DESTROYABLE)) {
            BlockUtils.setBlockHealth(bodyB, ItemUtils.getItemHealthDamage(bodyA));
            if (BlockUtils.getBlockHealth(bodyB) <= 0) {
                if (BlockUtils.getBlockDropsLoot(bodyB)) {
                    mapParser.addToDroppedItemPositionArray(bodyB.getPosition());
                }
                deleteBodies.add(new DeleteBody((BaseDestroyableModel) bodyB.getUserData(), bodyB));
                loadedRoomModel.addDestroyedBlock((BaseDestroyableModel) bodyB.getUserData());
                mapParser.addToDestroyedBlockPositionArray(bodyB.getPosition());
            }
        }
        else if (BodyUtils.bodyTypeCheck(bodyB, BaseModelType.PLAYER_ITEM_SECONDARY) && BodyUtils.bodyTypeCheck(bodyA, BaseModelType.BLOCK_DESTROYABLE)) {
            BlockUtils.setBlockHealth(bodyA, ItemUtils.getItemHealthDamage(bodyB));
            if (BlockUtils.getBlockHealth(bodyA) <= 0) {
                if (BlockUtils.getBlockDropsLoot(bodyA)) {
                    mapParser.addToDroppedItemPositionArray(bodyA.getPosition());
                }
                deleteBodies.add(new DeleteBody((BaseDestroyableModel) bodyA.getUserData(), bodyA));
                loadedRoomModel.addDestroyedBlock((BaseDestroyableModel) bodyA.getUserData());
                mapParser.addToDestroyedBlockPositionArray(bodyA.getPosition());
            }
        }
    }

    public void setPlayerEnemyCollision(PlayerActor playerActor, Body bodyA, Body bodyB) {
        // player/enemy collisions
        if (BodyUtils.bodyTypeCheck(bodyA, BaseModelType.ENEMY) && BodyUtils.bodyTypeCheck(bodyB, BaseModelType.PLAYER)) {
            playerActor.setHitEnemy(EnemyUtils.getEnemyBodyDamageAmount(bodyA));
            playerActor.setIsFlinching(true);
            playerActor.startFlinchingTimer(playerActor);
        }
        else if (BodyUtils.bodyTypeCheck(bodyB, BaseModelType.ENEMY) && BodyUtils.bodyTypeCheck(bodyA, BaseModelType.PLAYER)) {
            playerActor.setHitEnemy(EnemyUtils.getEnemyBodyDamageAmount(bodyB));
            playerActor.setIsFlinching(true);
            playerActor.startFlinchingTimer(playerActor);
        }
    }

    public void setPlayerPickupKey(PlayerActor playerActor, RoomModel loadedRoomModel, ArrayList<DeleteBody> deleteBodies, MapParser mapParser, Body bodyA, Body bodyB) {
        // pickup a key
        if (BodyUtils.bodyTypeCheck(bodyA, BaseModelType.KEY) && BodyUtils.bodyTypeCheck(bodyB, BaseModelType.PLAYER)) {
            mapParser.addToPickedUpKeyPositionArray(bodyA.getPosition());
            playerActor.addKey((KeyModel) bodyA.getUserData());
            loadedRoomModel.addPickedUpKey((KeyModel) bodyA.getUserData());
            deleteBodies.add(new DeleteBody((KeyModel) bodyA.getUserData(), bodyA));
        }
        else if (BodyUtils.bodyTypeCheck(bodyB, BaseModelType.KEY) && BodyUtils.bodyTypeCheck(bodyA, BaseModelType.PLAYER)) {
            mapParser.addToPickedUpKeyPositionArray(bodyB.getPosition());
            playerActor.addKey((KeyModel) bodyB.getUserData());
            loadedRoomModel.addPickedUpKey((KeyModel) bodyB.getUserData());
            deleteBodies.add(new DeleteBody((KeyModel) bodyB.getUserData(), bodyB));
        }
    }

    public void setPlayerPickupHeart(PlayerActor playerActor, RoomModel loadedRoomModel, ArrayList<DeleteBody> deleteBodies, MapParser mapParser, ArrayList<IGameStage> listeners, Body bodyA, Body bodyB) {
        // pickup a heart
        if (BodyUtils.bodyTypeCheck(bodyA, BaseModelType.HEART) && BodyUtils.bodyTypeCheck(bodyB, BaseModelType.PLAYER)) {
            mapParser.addToPickedUpHeartPositionArray(bodyA.getPosition());
            playerActor.addHealthHeart((HeartModel) bodyA.getUserData());
            loadedRoomModel.addPickedUpHeart((HeartModel) bodyA.getUserData());

            for (IGameStage listener : listeners) {
                listener.setHudHealthHearts(playerActor.getHealthHearts());
            }

            deleteBodies.add(new DeleteBody((HeartModel) bodyA.getUserData(), bodyA));
        }
        else if (BodyUtils.bodyTypeCheck(bodyB, BaseModelType.HEART) && BodyUtils.bodyTypeCheck(bodyA, BaseModelType.PLAYER)) {
            mapParser.addToPickedUpHeartPositionArray(bodyB.getPosition());
            playerActor.addHealthHeart((HeartModel) bodyB.getUserData());
            loadedRoomModel.addPickedUpHeart((HeartModel) bodyB.getUserData());

            for (IGameStage listener : listeners) {
                listener.setHudHealthHearts(playerActor.getHealthHearts());
            }

            deleteBodies.add(new DeleteBody((HeartModel) bodyB.getUserData(), bodyB));
        }
    }

    public void setPlayerPickupLife(PlayerActor playerActor, RoomModel loadedRoomModel, ArrayList<DeleteBody> deleteBodies, MapParser mapParser, ArrayList<IGameStage> listeners, Body bodyA, Body bodyB) {
        // pickup a life
        if (BodyUtils.bodyTypeCheck(bodyA, BaseModelType.LIFE) && BodyUtils.bodyTypeCheck(bodyB, BaseModelType.PLAYER)) {
            mapParser.addToPickedUpLifePositionArray(bodyA.getPosition());
            playerActor.incrementLives();
            loadedRoomModel.addPickedUpLife((LifeModel) bodyA.getUserData());

            for (IGameStage listener : listeners) {
                listener.setHudLives(playerActor.getPlayerLives());
            }

            deleteBodies.add(new DeleteBody((LifeModel) bodyA.getUserData(), bodyA));
        }
        else if (BodyUtils.bodyTypeCheck(bodyB, BaseModelType.LIFE) && BodyUtils.bodyTypeCheck(bodyA, BaseModelType.PLAYER)) {
            mapParser.addToPickedUpLifePositionArray(bodyB.getPosition());
            playerActor.incrementLives();
            loadedRoomModel.addPickedUpLife((LifeModel) bodyB.getUserData());

            for (IGameStage listener : listeners) {
                listener.setHudLives(playerActor.getPlayerLives());
            }

            deleteBodies.add(new DeleteBody((LifeModel) bodyB.getUserData(), bodyB));
        }
    }

    public void setPlayerPickupItem(PlayerActor playerActor, RoomModel loadedRoomModel, ArrayList<DeleteBody> deleteBodies, Body bodyA, Body bodyB) {
        //pick up player items
        if (BodyUtils.bodyTypeCheck(bodyA, BaseModelType.PLAYER_ITEM_PRIMARY) && BodyUtils.bodyTypeCheck(bodyB, BaseModelType.PLAYER)) {
            GLog.d(TAG, "setPlayerPickupItem: primary" + bodyB.getUserData().toString());
            playerActor.addPrimaryItem((BasePlayerItemModel) bodyA.getUserData());
            loadedRoomModel.addPickedUpItem((BasePlayerItemModel) bodyA.getUserData());
            deleteBodies.add(new DeleteBody((BasePlayerItemModel) bodyA.getUserData(), bodyA));

            for (IAchievement listener : mIAchievementListeners) {
                listener.achievementPlayerPickedUpItem((BasePlayerItemModel) bodyA.getUserData());
            }
        }
        else if (BodyUtils.bodyTypeCheck(bodyB, BaseModelType.PLAYER_ITEM_PRIMARY) && BodyUtils.bodyTypeCheck(bodyA, BaseModelType.PLAYER)) {
            GLog.d(TAG, "setPlayerPickupItem: primary" + bodyB.getUserData().toString());
            playerActor.addPrimaryItem((BasePlayerItemModel) bodyB.getUserData());
            loadedRoomModel.addPickedUpItem((BasePlayerItemModel) bodyB.getUserData());
            deleteBodies.add(new DeleteBody((BasePlayerItemModel) bodyB.getUserData(), bodyB));

            for (IAchievement listener : mIAchievementListeners) {
                listener.achievementPlayerPickedUpItem((BasePlayerItemModel) bodyB.getUserData());
            }
        }

        if (BodyUtils.bodyTypeCheck(bodyA, BaseModelType.PLAYER_ITEM_SECONDARY) && BodyUtils.bodyTypeCheck(bodyB, BaseModelType.PLAYER)) {
            GLog.d(TAG, "setPlayerPickupItem: secondary" + bodyA.getUserData().toString());
            playerActor.addSecondaryItem((BasePlayerItemModel) bodyA.getUserData());
            loadedRoomModel.addPickedUpItem((BasePlayerItemModel) bodyA.getUserData());
            deleteBodies.add(new DeleteBody((BasePlayerItemModel) bodyA.getUserData(), bodyA));

            for (IAchievement listener : mIAchievementListeners) {
                listener.achievementPlayerPickedUpItem((BasePlayerItemModel) bodyA.getUserData());
            }
        }
        else if (BodyUtils.bodyTypeCheck(bodyB, BaseModelType.PLAYER_ITEM_SECONDARY) && BodyUtils.bodyTypeCheck(bodyA, BaseModelType.PLAYER)) {
            GLog.d(TAG, "setPlayerPickupItem: secondary" + bodyB.getUserData().toString());
            playerActor.addSecondaryItem((BasePlayerItemModel) bodyB.getUserData());
            loadedRoomModel.addPickedUpItem((BasePlayerItemModel) bodyB.getUserData());
            deleteBodies.add(new DeleteBody((BasePlayerItemModel) bodyB.getUserData(), bodyB));

            for (IAchievement listener : mIAchievementListeners) {
                listener.achievementPlayerPickedUpItem((BasePlayerItemModel) bodyB.getUserData());
            }
        }
    }

    public void setPlayerPickupSmallHeart(GameStage gameStage, PlayerActor playerActor, ArrayList<DeleteBody> deleteBodies, Body bodyA, Body bodyB) {
        //pick up HEART_SMALL
        if (BodyUtils.bodyTypeCheck(bodyA, BaseModelType.PLAYER) && BodyUtils.bodyTypeCheck(bodyB, BaseModelType.HEART_SMALL)) {
            playerActor.addHealth((DropHeartModel) bodyB.getUserData());
            deleteBodies.add(new DeleteBody((DropHeartModel) bodyB.getUserData(), bodyB));
            gameStage.updateHud();
        }
        else if (BodyUtils.bodyTypeCheck(bodyB, BaseModelType.PLAYER) && BodyUtils.bodyTypeCheck(bodyA, BaseModelType.HEART_SMALL)) {
            playerActor.addHealth((DropHeartModel) bodyA.getUserData());
            deleteBodies.add(new DeleteBody((DropHeartModel) bodyA.getUserData(), bodyA));
            gameStage.updateHud();
        }
    }

    public void setPlayerPickupSmallCrystalBlue(GameStage gameStage, PlayerActor playerActor, ArrayList<DeleteBody> deleteBodies, Body bodyA, Body bodyB) {
        //pick up CRYSTAL_BLUE with PLAYER
        if (BodyUtils.bodyTypeCheck(bodyA, BaseModelType.PLAYER) && BodyUtils.bodyTypeCheck(bodyB, BaseModelType.CRYSTAL_BLUE)) {
            playerActor.incrementCrystalBlueAmount();
            deleteBodies.add(new DeleteBody((DropCrystalBlueModel) bodyB.getUserData(), bodyB));
            gameStage.updateHud();
        }
        else if (BodyUtils.bodyTypeCheck(bodyB, BaseModelType.PLAYER) && BodyUtils.bodyTypeCheck(bodyA, BaseModelType.CRYSTAL_BLUE)) {
            playerActor.incrementCrystalBlueAmount();
            deleteBodies.add(new DeleteBody((DropCrystalBlueModel) bodyA.getUserData(), bodyA));
            gameStage.updateHud();
        }
    }

}