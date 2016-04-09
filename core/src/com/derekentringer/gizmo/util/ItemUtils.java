package com.derekentringer.gizmo.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.derekentringer.gizmo.component.actor.item.BombActor;
import com.derekentringer.gizmo.component.actor.item.BombExplodeActor;
import com.derekentringer.gizmo.component.actor.item.boomerang.BoomerangAmethystActor;
import com.derekentringer.gizmo.component.actor.item.boomerang.BoomerangBloodStoneActor;
import com.derekentringer.gizmo.component.actor.item.boomerang.BoomerangEmeraldActor;
import com.derekentringer.gizmo.component.actor.item.boomerang.BoomerangWoodActor;
import com.derekentringer.gizmo.component.actor.player.PlayerActor;
import com.derekentringer.gizmo.component.stage.GameStage;
import com.derekentringer.gizmo.model.item.BasePlayerItemModel;
import com.derekentringer.gizmo.model.item.BombExplodeModel;
import com.derekentringer.gizmo.model.item.BombModel;
import com.derekentringer.gizmo.model.item.boomerang.BoomerangAmethystModel;
import com.derekentringer.gizmo.model.item.boomerang.BoomerangBloodStoneModel;
import com.derekentringer.gizmo.model.item.boomerang.BoomerangEmeraldModel;
import com.derekentringer.gizmo.model.item.boomerang.BoomerangWoodModel;
import com.derekentringer.gizmo.util.map.MapParser;

public class ItemUtils {

    public static void createWoodBoomerang(World world, PlayerActor playerActor, MapParser mapParser, GameStage gameStage) {
        BoomerangWoodActor boomerangWoodActor = new BoomerangWoodActor(BodyUtils.createBoomerang(new BoomerangWoodModel(), world, playerActor.getPosition()), playerActor.getFacingDirection());
        boomerangWoodActor.setName(BoomerangWoodModel.BOOMERANG_WOOD);
        mapParser.addToTempActorsArray(boomerangWoodActor);
        boomerangWoodActor.addListener(gameStage);
    }

    public static void createEmeraldBoomerang(World world, PlayerActor playerActor, MapParser mapParser, GameStage gameStage) {
        BoomerangEmeraldActor boomerangEmeraldActor = new BoomerangEmeraldActor(BodyUtils.createBoomerang(new BoomerangEmeraldModel(), world, playerActor.getPosition()), playerActor.getFacingDirection());
        boomerangEmeraldActor.setName(BoomerangEmeraldModel.BOOMERANG_EMERALD);
        mapParser.addToTempActorsArray(boomerangEmeraldActor);
        boomerangEmeraldActor.addListener(gameStage);
    }

    public static void createAmethystBoomerang(World world, PlayerActor playerActor, MapParser mapParser, GameStage gameStage) {
        BoomerangAmethystActor boomerangAmethystActor = new BoomerangAmethystActor(BodyUtils.createBoomerang(new BoomerangAmethystModel(), world, playerActor.getPosition()), playerActor.getFacingDirection());
        boomerangAmethystActor.setName(BoomerangAmethystModel.BOOMERANG_AMETHYST);
        mapParser.addToTempActorsArray(boomerangAmethystActor);
        boomerangAmethystActor.addListener(gameStage);
    }

    public static void createBloodStoneBoomerang(World world, PlayerActor playerActor, MapParser mapParser, GameStage gameStage) {
        BoomerangBloodStoneActor boomerangBloodStoneActor = new BoomerangBloodStoneActor(BodyUtils.createBoomerang(new BoomerangBloodStoneModel(), world, playerActor.getPosition()), playerActor.getFacingDirection());
        boomerangBloodStoneActor.setName(BoomerangBloodStoneModel.BOOMERANG_BLOODSTONE);
        mapParser.addToTempActorsArray(boomerangBloodStoneActor);
        boomerangBloodStoneActor.addListener(gameStage);
    }

    public static int getItemHealthDamage(Body body) {
        BasePlayerItemModel itemModel = (BasePlayerItemModel) body.getUserData();
        return itemModel.getHealthDamage();
    }

    public static void useBomb(World world, PlayerActor playerActor, MapParser mapParser, GameStage gameStage) {
        BombActor bombActor = new BombActor(BodyUtils.createBomb(new BombModel(), world, playerActor.getPosition()), world, mapParser, gameStage, playerActor.getFacingDirection());
        bombActor.setName(BombModel.BOMB);
        mapParser.addToTempActorsArray(bombActor);
        bombActor.addListener(gameStage);
        removeSecondaryItem(playerActor);
    }

    public static void explodeBomb(World world, Vector2 bombPosition, MapParser mapParser, GameStage gameStage) {
        BombExplodeActor bombExplodeActor = new BombExplodeActor(BodyUtils.createBombExplosion(new BombExplodeModel(), world, bombPosition));
        bombExplodeActor.setName(BombExplodeModel.BOMB_EXPOLODE);
        mapParser.addToTempActorsArray(bombExplodeActor);
        bombExplodeActor.addListener(gameStage);
    }

    public static void usePotionLife(PlayerActor playerActor) {
        playerActor.resetHealth();
        removeSecondaryItem(playerActor);
    }

    private static void removeSecondaryItem(PlayerActor playerActor) {
        playerActor.removeSecondaryItem(playerActor.getCurrentSecondaryItem());
        playerActor.incrementSelectedSecondaryItem();
    }

}