package com.derekentringer.gizmo.manager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.derekentringer.gizmo.component.actor.player.PlayerActor;
import com.derekentringer.gizmo.component.stage.GameStage;
import com.derekentringer.gizmo.component.stage.interfaces.IGameStage;
import com.derekentringer.gizmo.model.BaseModelType;
import com.derekentringer.gizmo.model.body.DeleteBody;
import com.derekentringer.gizmo.model.enemy.BaseEnemyModel;
import com.derekentringer.gizmo.model.item.BaseItemModel;
import com.derekentringer.gizmo.model.level.LevelModel;
import com.derekentringer.gizmo.model.object.DropCrystalBlueModel;
import com.derekentringer.gizmo.model.object.DropHeartModel;
import com.derekentringer.gizmo.model.object.HeartModel;
import com.derekentringer.gizmo.model.object.KeyModel;
import com.derekentringer.gizmo.model.object.LifeModel;
import com.derekentringer.gizmo.model.structure.door.DoorModel;
import com.derekentringer.gizmo.util.BodyUtils;
import com.derekentringer.gizmo.util.EnemyUtils;
import com.derekentringer.gizmo.util.FixtureUtils;
import com.derekentringer.gizmo.util.ItemUtils;
import com.derekentringer.gizmo.util.map.MapParser;

import java.util.ArrayList;

public class ContactManager {

    private static final String TAG = ContactManager.class.getSimpleName();

    public ContactManager() {
    }

    public static void setPlayerOnGround(PlayerActor playerActor, Fixture fixtureA, Fixture fixtureB) {
        // detect when player is on the ground
        if (FixtureUtils.fixtureIsPlayerHitAreaBottom(fixtureA) && FixtureUtils.fixtureIsGround(fixtureB)) {
            playerActor.setIsOnGround(true);
        }
        else if (FixtureUtils.fixtureIsPlayerHitAreaBottom(fixtureB) && FixtureUtils.fixtureIsGround(fixtureA)) {
            playerActor.setIsOnGround(true);
        }
    }

    public static void setPlayerAtDoor(PlayerActor playerActor, Body bodyA, Body bodyB) {
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

    public static void setPlayerTouchingDestroyable(PlayerActor playerActor, Fixture fixtureA, Fixture fixtureB) {
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

    public static void setPlayerAttacking(MapParser mapParser, ArrayList<DeleteBody> deleteBodies, Body bodyA, Body bodyB) {
        // player attack with items collisions
        if (BodyUtils.bodyTypeCheck(bodyA, BaseModelType.PLAYER_ITEM) && BodyUtils.bodyTypeCheck(bodyB, BaseModelType.ENEMY)) {
            EnemyUtils.setEnemyHealth(bodyB, ItemUtils.getItemHealthDamage(bodyA));
            if (EnemyUtils.getEnemyHealth(bodyB) <= 0) {
                if (EnemyUtils.isEnemyBoss(bodyB)) {
                    if (EnemyUtils.getEnemyDropsLoot(bodyB)) {
                        mapParser.addToBossDroppedItemPositionArray(bodyB.getPosition());
                    }
                }
                else if (EnemyUtils.getEnemyDropsLoot(bodyB)) {
                    mapParser.addToDroppedItemPositionArray(bodyB.getPosition());
                }
                deleteBodies.add(new DeleteBody((BaseEnemyModel) bodyB.getUserData(), bodyB));
            }
        }
        else if (BodyUtils.bodyTypeCheck(bodyB, BaseModelType.PLAYER_ITEM) && BodyUtils.bodyTypeCheck(bodyA, BaseModelType.ENEMY)) {
            EnemyUtils.setEnemyHealth(bodyA, ItemUtils.getItemHealthDamage(bodyB));
            if (EnemyUtils.getEnemyHealth(bodyA) <= 0) {
                if (EnemyUtils.isEnemyBoss(bodyA)) {
                    if (EnemyUtils.getEnemyDropsLoot(bodyA)) {
                        mapParser.addToBossDroppedItemPositionArray(bodyA.getPosition());
                    }
                }
                else if (EnemyUtils.getEnemyDropsLoot(bodyA)) {
                    mapParser.addToDroppedItemPositionArray(bodyA.getPosition());
                }
                deleteBodies.add(new DeleteBody((BaseEnemyModel) bodyA.getUserData(), bodyA));
            }
        }
    }

    public static void setPlayerEnemyCollision(PlayerActor playerActor, Body bodyA, Body bodyB) {
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

    public static void setPlayerPickupKey(PlayerActor playerActor, LevelModel loadedLevelModel, ArrayList<DeleteBody> deleteBodies, Body bodyA, Body bodyB) {
        // pickup a key
        if (BodyUtils.bodyTypeCheck(bodyA, BaseModelType.KEY) && BodyUtils.bodyTypeCheck(bodyB, BaseModelType.PLAYER)) {
            playerActor.addKey((KeyModel) bodyA.getUserData());
            loadedLevelModel.addPickedUpKey((KeyModel) bodyA.getUserData());
            deleteBodies.add(new DeleteBody((KeyModel) bodyA.getUserData(), bodyA));
        }
        else if (BodyUtils.bodyTypeCheck(bodyB, BaseModelType.KEY) && BodyUtils.bodyTypeCheck(bodyA, BaseModelType.PLAYER)) {
            playerActor.addKey((KeyModel) bodyB.getUserData());
            loadedLevelModel.addPickedUpKey((KeyModel) bodyB.getUserData());
            deleteBodies.add(new DeleteBody((KeyModel) bodyB.getUserData(), bodyB));
        }
    }

    public static void setPlayerPickupHeart(PlayerActor playerActor, LevelModel loadedLevelModel, ArrayList<DeleteBody> deleteBodies, ArrayList<IGameStage> listeners, Body bodyA, Body bodyB) {
        // pickup a heart
        if (BodyUtils.bodyTypeCheck(bodyA, BaseModelType.HEART) && BodyUtils.bodyTypeCheck(bodyB, BaseModelType.PLAYER)) {
            playerActor.addHealthHeart((HeartModel) bodyA.getUserData());
            loadedLevelModel.addPickedUpHeart((HeartModel) bodyA.getUserData());

            for (IGameStage listener : listeners) {
                listener.setHudHealthHearts(playerActor.getHealthHearts());
            }

            deleteBodies.add(new DeleteBody((HeartModel) bodyA.getUserData(), bodyA));
        }
        else if (BodyUtils.bodyTypeCheck(bodyB, BaseModelType.HEART) && BodyUtils.bodyTypeCheck(bodyA, BaseModelType.PLAYER)) {
            playerActor.addHealthHeart((HeartModel) bodyB.getUserData());
            loadedLevelModel.addPickedUpHeart((HeartModel) bodyB.getUserData());

            for (IGameStage listener : listeners) {
                listener.setHudHealthHearts(playerActor.getHealthHearts());
            }

            deleteBodies.add(new DeleteBody((HeartModel) bodyB.getUserData(), bodyB));
        }
    }

    public static void setPlayerPickupLife(PlayerActor playerActor, LevelModel loadedLevelModel, ArrayList<DeleteBody> deleteBodies, ArrayList<IGameStage> listeners, Body bodyA, Body bodyB) {
        // pickup a life
        if (BodyUtils.bodyTypeCheck(bodyA, BaseModelType.LIFE) && BodyUtils.bodyTypeCheck(bodyB, BaseModelType.PLAYER)) {
            playerActor.incrementLives();
            loadedLevelModel.addPickedUpLife((LifeModel) bodyA.getUserData());

            for (IGameStage listener : listeners) {
                listener.setHudLives(playerActor.getPlayerLives());
            }

            deleteBodies.add(new DeleteBody((LifeModel) bodyA.getUserData(), bodyA));
        }
        else if (BodyUtils.bodyTypeCheck(bodyB, BaseModelType.LIFE) && BodyUtils.bodyTypeCheck(bodyA, BaseModelType.PLAYER)) {
            playerActor.incrementLives();
            loadedLevelModel.addPickedUpLife((LifeModel) bodyB.getUserData());

            for (IGameStage listener : listeners) {
                listener.setHudLives(playerActor.getPlayerLives());
            }

            deleteBodies.add(new DeleteBody((LifeModel) bodyB.getUserData(), bodyB));
        }
    }

    public static void setPlayerPickupItem(PlayerActor playerActor, LevelModel loadedLevelModel, ArrayList<DeleteBody> deleteBodies, Body bodyA, Body bodyB) {
        //pick up any type of PLAYER_ITEM
        if (BodyUtils.bodyTypeCheck(bodyA, BaseModelType.PLAYER_ITEM) && BodyUtils.bodyTypeCheck(bodyB, BaseModelType.PLAYER)) {
            playerActor.addItem((BaseItemModel) bodyA.getUserData());
            loadedLevelModel.addPickedUpItem((BaseItemModel) bodyA.getUserData());
            deleteBodies.add(new DeleteBody((BaseItemModel) bodyA.getUserData(), bodyA));
        }
        else if (BodyUtils.bodyTypeCheck(bodyB, BaseModelType.PLAYER_ITEM) && BodyUtils.bodyTypeCheck(bodyA, BaseModelType.PLAYER)) {
            playerActor.addItem((BaseItemModel) bodyB.getUserData());
            loadedLevelModel.addPickedUpItem((BaseItemModel) bodyB.getUserData());
            deleteBodies.add(new DeleteBody((BaseItemModel) bodyB.getUserData(), bodyB));
        }
    }

    public static void setPlayerPickupSmallHeart(GameStage gameStage, PlayerActor playerActor, ArrayList<DeleteBody> deleteBodies, Body bodyA, Body bodyB) {
        //pick up HEART_SMALL with PLAYER
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

    public static void setPlayerPickupSmallCrystalBlue(GameStage gameStage, PlayerActor playerActor, ArrayList<DeleteBody> deleteBodies, Body bodyA, Body bodyB) {
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