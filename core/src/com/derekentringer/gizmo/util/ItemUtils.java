package com.derekentringer.gizmo.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.derekentringer.gizmo.component.actor.item.boomerang.BoomerangAmethystActor;
import com.derekentringer.gizmo.component.actor.item.boomerang.BoomerangBloodStoneActor;
import com.derekentringer.gizmo.component.actor.item.boomerang.BoomerangEmeraldActor;
import com.derekentringer.gizmo.component.actor.item.boomerang.BoomerangWoodActor;
import com.derekentringer.gizmo.component.actor.player.PlayerActor;
import com.derekentringer.gizmo.component.stage.GameStage;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.player_item.BasePlayerItemModel;
import com.derekentringer.gizmo.model.player_item.boomerang.BoomerangAmethystModel;
import com.derekentringer.gizmo.model.player_item.boomerang.BoomerangBloodStoneModel;
import com.derekentringer.gizmo.model.player_item.boomerang.BoomerangEmeraldModel;
import com.derekentringer.gizmo.model.player_item.boomerang.BoomerangWoodModel;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.map.MapParser;

public class ItemUtils {

    private static final String TAG = ItemUtils.class.getSimpleName();

    public static void createWoodBoomerang(World world, PlayerActor playerActor, MapParser mapParser, GameStage gameStage) {
        BoomerangWoodActor boomerangWoodActor = new BoomerangWoodActor(ItemUtils.createBoomerang(new BoomerangWoodModel(), world, playerActor.getPosition()), playerActor.getFacingDirection());
        boomerangWoodActor.setName(BoomerangWoodModel.BOOMERANG_WOOD);
        mapParser.addToTempActorsArray(boomerangWoodActor);
        boomerangWoodActor.addListener(gameStage);
    }

    public static void createEmeraldBoomerang(World world, PlayerActor playerActor, MapParser mapParser, GameStage gameStage) {
        BoomerangEmeraldActor boomerangEmeraldActor = new BoomerangEmeraldActor(ItemUtils.createBoomerang(new BoomerangEmeraldModel(), world, playerActor.getPosition()), playerActor.getFacingDirection());
        boomerangEmeraldActor.setName(BoomerangEmeraldModel.BOOMERANG_EMERALD);
        mapParser.addToTempActorsArray(boomerangEmeraldActor);
        boomerangEmeraldActor.addListener(gameStage);
    }

    public static void createAmethystBoomerang(World world, PlayerActor playerActor, MapParser mapParser, GameStage gameStage) {
        BoomerangAmethystActor boomerangAmethystActor = new BoomerangAmethystActor(ItemUtils.createBoomerang(new BoomerangAmethystModel(), world, playerActor.getPosition()), playerActor.getFacingDirection());
        boomerangAmethystActor.setName(BoomerangAmethystModel.BOOMERANG_AMETHYST);
        mapParser.addToTempActorsArray(boomerangAmethystActor);
        boomerangAmethystActor.addListener(gameStage);
    }

    public static void createBloodStoneBoomerang(World world, PlayerActor playerActor, MapParser mapParser, GameStage gameStage) {
        BoomerangBloodStoneActor boomerangBloodStoneActor = new BoomerangBloodStoneActor(ItemUtils.createBoomerang(new BoomerangBloodStoneModel(), world, playerActor.getPosition()), playerActor.getFacingDirection());
        boomerangBloodStoneActor.setName(BoomerangBloodStoneModel.BOOMERANG_BLOODSTONE);
        mapParser.addToTempActorsArray(boomerangBloodStoneActor);
        boomerangBloodStoneActor.addListener(gameStage);
    }

    public static int getItemHealthDamage(Body body) {
        BasePlayerItemModel itemModel = (BasePlayerItemModel) body.getUserData();
        return itemModel.getHealthDamage();
    }

    public static Body createBoomerang(BaseModel userData, World world, Vector2 coordinates) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.position.set(coordinates.x, coordinates.y);
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        Body body = world.createBody(bodyDef);

        shape.setAsBox(WorldUtils.ppmCalc(14), WorldUtils.ppmCalc(14));
        fixtureDef.shape = shape;
        fixtureDef.isSensor = false;

        fixtureDef.filter.categoryBits = Constants.PLAYER_ATTACK_ENTITY;
        fixtureDef.filter.maskBits = Constants.ENEMY_ENTITY;

        body.createFixture(fixtureDef).setUserData(userData);

        body.setUserData(userData);

        shape.dispose();

        return body;
    }

}